module UCraftSim

  class Logger
    def round_start
    end

    def attack(ship, weapon, target, damage)
    end

    def destroyed(ship)
    end

    def round_end
    end
  end

  DUMMY_LOGGER = Logger.new

  class Ship
    attr_reader :weapons, :threat, :owner, :max_armor, :max_shield
    attr_accessor :armor, :shield, :side

    @weapons = []
    @armor = 0
    @shield = 0
    @threat = 0
    @armor_type = nil
    @defence = false

    def self.inherited(subclass)
      subclass.instance_eval do
        @weapons, @armor, @shield, @threat, @armor_type, @defence =
          superclass.instance_eval { [@weapons.clone, @armor, @shield, @threat, @armor_type, @defence] }
      end
    end

    # weapon class: LaserCannon, damage: 10
    # weapon class: PhotonCannon, damage: 10, range: 5
    def self.weapon(params = {})
      throw ArgumentError, "weapon class is not specified" if params[:class].nil?
      throw ArgumentError, "weapon class is invalid" unless params[:class].kind_of? Class and params[:class].ancestors.member? Weapon
      @weapons.push params
    end

    # armor 100
    def self.armor(a)
      throw ArgumentError, "armor must be a positive integer" unless a.kind_of? Integer and a > 0
      @armor = a
    end

    # shield 10
    def self.shield(s)
      throw ArgumentError, "shield must be a non-negative integer" unless s.kind_of? Integer and s >= 0
      @shield = s
    end

    # threat 1
    def self.threat(t)
      throw ArgumentError, "threat must be a positive integer" unless t.kind_of? Integer and t > 0
      @threat = t
    end

    # armor_type :light_armor
    def self.armor_type(at)
      throw ArgumentError unless [:light_armor, :heavy_armor].member? at
      @armor_type = at
    end

    def self.defence(d = true)
      @defence = d
    end

    def self.shiptype
      self.to_s.gsub(/^.*::/, '')
    end

    def self.shipclass
      throw NotImplementedError
    end

    def initialize(owner, research = {}, factors = {})
      @owner = owner
      blueprint = self.class.instance_eval do
        {
          weapons: @weapons,
          armor: @armor,
          shield: @shield,
          threat: @threat,
          armor_type: @armor_type
        }
      end

      factors = {armor: 0, shield: 0}.merge(factors)
      factors[:armor] += research[blueprint[:armor_type]] * 0.05 if research[blueprint[:armor_type]]
      factors[:shield] += research[:shield] * 0.05 if research[:shield]

      @weapons = []
      for w in blueprint[:weapons]
        @weapons.push w[:class].new(self, w, research, factors)
      end
      @armor  = ((1 + factors[:armor])  * blueprint[:armor]).to_i
      @shield = ((1 + factors[:shield]) * blueprint[:shield]).to_i
      @threat = blueprint[:threat]
      @max_armor = @armor
      @max_shield = @shield

      @destroyed = false
      @defence = self.class.instance_eval { @defence }

      @damage = 0
      @damaged = false
    end

    def round_start(fleet)
      @damaged = false
    end

    def round_end(fleet)
      return if @destroyed

      @shield -= @damage
      if @shield < 0
        @armor += @shield
        @shield = 0
      elsif @shield > @max_shield
        @shield = @max_shield
      end
      @damage = 0

      @shield += @max_shield / 5
      @shield = @max_shield if @shield > @max_shield
      if @armor.to_f / @max_armor < 0.8 and @damaged
        @destroyed = rand > (@armor.to_f / @max_armor)
        if @destroyed
          @armor = 0 
          @shield = 0
          @owner.logger.destroyed self
        end
      end
    end

    def attack(weighted_fleet, weighted_defences = [], weighted_friends = [])
      for w in @weapons
        w.attack weighted_fleet, weighted_defences, weighted_friends
      end
    end

    def damage(d)
      @damage += d
      @damaged = true if d > 0
    end

    def defence?
      @defence
    end

    def destroyed?
      @destroyed
    end

    def shiptype
      self.class.shiptype
    end

    def shipclass
      self.class.shipclass
    end

    def inspect
      "#<#{self.shiptype}(#{self.object_id}) A[#{"\x1b[34;1m%-10s\x1b[0m" % ("=" * ((@armor < 0 ? 0 : @armor).to_f/@max_armor * 10).to_i)}] S[#{"\x1b[36;1m%-10s\x1b[0m" % ("=" * (@shield.to_f/@max_shield * 10).to_i)}]>"
    end
  end

  class LightArmoredShip < Ship
    armor_type :light_armor
  end

  class HeavyArmoredShip < Ship
    armor_type :heavy_armor
  end

  class Frigate < LightArmoredShip
    def self.shipclass
      Frigate
    end
  end

  class Cruiser < LightArmoredShip
    def self.shipclass
      Cruiser
    end
  end

  class BattleCruiser < LightArmoredShip
    def self.shipclass
      BattleCruiser
    end
  end

  class Battleship < HeavyArmoredShip
    def self.shipclass
      Battleship
    end
  end

  class Carrier < HeavyArmoredShip
    def self.shipclass
      Carrier
    end
  end

  class Titan < HeavyArmoredShip
    def self.shipclass
      Titan
    end
  end

  class Weapon
    attr_reader :damage, :range, :owner

    DAMAGE_DISTRIBUTION = {
      2 => [0.6, 0.2],
      5 => [0.68, 0.56, 0.52, 0.48, 0.36],
      6 => [0.68, 0.6, 0.56, 0.52, 0.48, 0.36],
      10 => [0.92, 0.84, 0.76, 0.68, 0.60, 0.52, 0.44, 0.36, 0.28, 0.20]
    }

    def self.inherited(subclass)
      subclass.instance_eval do
        @efficiency = {}
        @weapon_research = self.to_s.gsub(/^.*::/, '').gsub(/(.)([A-Z])/, '\1_\2').downcase.to_sym
      end
    end

    def self.weapon_research(r)
      @weapon_research = r
    end

    def self.defence_only(d = true)
      @defence_only = d
    end

    def self.efficiency(ship_class, f)
      throw ArgumentError, "ship class is invalid" unless ship_class.kind_of? Class and ship_class.ancestors.member? Ship
      @efficiency[ship_class] = f
    end

    def initialize(owner, info, research = {}, factors = {})
      @owner = owner
      attack = factors[:attack] || 0
      attack += (research[self.class.instance_eval { @weapon_research }] || 0) * 0.1
      @damage = (info[:damage] || 0) * (1 + attack)
      @range = info[:range] || 1
      @only_defence = self.class.instance_eval { @defence_only }
    end

    def attack(weighted_fleet, weighted_defences, weighted_friends)
      ef = self.class.instance_eval { @efficiency }

      s = nil
      if @only_defence
        s = weighted_defences.sample
      else
        s = weighted_fleet.sample
      end
      unless s.nil?
        d = ((ef[s.shipclass] || 1) * @damage).to_i
        s.damage d
        @owner.owner.logger.attack @owner, self, s, d
      end
      if @range > 1
        if DAMAGE_DISTRIBUTION.keys.member? @range
          DAMAGE_DISTRIBUTION[@range].each do |f|
            2.times do
              s = weighted_fleet.sample
              unless s.nil?
                d = ((ef[s.shipclass] || 1) * @damage * f).to_i
                s.damage d
                @owner.owner.logger.attack @owner, self, s, d
              end
            end
          end
        else
          throw ArgumentError, "range #{@range} is not implemented yet"
        end
      end
    end
  end

  require_relative 'weapons'
  require_relative 'ships'
  require_relative 'defences'

  class BattleSim
    attr_accessor :logger

    def initialize(ainfo, binfo)
      ar = ainfo[:research] || {}
      af = ainfo[:factor] || {}
      br = binfo[:research] || {}
      bf = binfo[:factor] || {}
      @afleet = []
      for k, v in ainfo[:fleet]
        ship = UCraftSim.const_get(k.to_s.gsub(/^.|_./){|c| c[-1].upcase })
        v.times { s = ship.new(self, ar, af); s.side = :a; @afleet.push s }
      end
      @bfleet = []
      for k, v in binfo[:fleet]
        ship = UCraftSim.const_get(k.to_s.gsub(/^.|_./){|c| c[-1].upcase })
        v.times { s = ship.new(self, br, bf); s.side = :b; @bfleet.push s }
      end

      @logger = DUMMY_LOGGER
    end

    def round
      aattacks = {}
      battacks = {}
      adamages = {}
      bdamages = {}

      @logger.round_start

      # beginning of round
      @afleet.each {|a| a.round_start @afleet }
      @bfleet.each {|b| b.round_start @bfleet }

      build_weighted_arrays

      # A side attack
      @afleet.each {|a| a.attack @wb, @wbd, @wa }

      # B side attack
      @bfleet.each {|b| b.attack @wa, @wad, @wb }

      # end of round
      @afleet.each {|a| a.round_end @afleet }
      @bfleet.each {|b| b.round_end @bfleet }

      @afleet.delete_if{|a| a.destroyed? }
      @bfleet.delete_if{|b| b.destroyed? }

      @logger.round_end

      return (not (@afleet.empty? or @bfleet.empty?))
    end

    def compare
      (@afleet.empty? ? 0 : 1) <=> (@bfleet.empty? ? 0 : 1)
    end

    private
    def build_weighted_arrays
      @wa = @afleet.map{|x| [x] * x.threat }.flatten
      @wad = @afleet.find_all{|x| x.defence? }.map{|x| [x] * x.threat }.flatten
      @wb = @bfleet.map{|x| [x] * x.threat }.flatten
      @wbd = @bfleet.find_all{|x| x.defence? }.map{|x| [x] * x.threat }.flatten
    end  
  end
end
