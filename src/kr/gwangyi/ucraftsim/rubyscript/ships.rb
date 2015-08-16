module UCraftSim
  class Phantom < Frigate
    weapon class: LaserCannon, damage: 50
    armor  300
    shield  30
    threat   1
  end

  class Phantom2 < Frigate
    2.times { weapon class: LaserCannon, damage: 60 }
    armor  700
    shield  70
    threat   1
  end

  class Observer < Frigate
    armor  200
    shield  20
    threat   1
  end

  class SmallTransporter < Frigate
    weapon class: LaserCannon, damage: 5
    armor  900
    shield  90
    threat   1
  end

  class Miner < Frigate
    weapon class: LaserCannon, damage: 5
    armor  1200
    shield  120
    threat    1
  end

  class Guardian < Cruiser
    weapon class: LaserCannon, damage: 50
    4.times { weapon class: SMMissile, damage: 125 }
    armor  1500
    shield  150
    threat    3
  end

  class Constantine < Cruiser
    weapon class: LaserCannon, damage: 100
    4.times { weapon class: CruiseMissile, damage: 600 }
    armor  1800
    shield  180
    threat    3
  end

  class Nightmare < Cruiser
    4.times { weapon class: LaserCannon,  damage: 150 }
    2.times { weapon class: PlasmaCannon, damage: 500 }
    armor  1500
    shield  300
    threat    3
  end

  class Colonizer < Cruiser
    armor  1000
    shield  100
    threat    1
  end

  class Halpas < BattleCruiser
    weapon class: PhotonCannon, damage: 350, range: 5
    armor  7800
    shield  780
    threat   10
  end

  class Patriot < BattleCruiser
    4.times { weapon class: IonCannon, damage: 600 }
    armor  9000
    shield  900
    threat   10
  end

  class Invader < BattleCruiser
    6.times { weapon class: LaserCannon, damage: 250 }
    weapon class: PhotonCannon, damage: 1200, range: 5
    armor  9000
    shield  900
    threat   10
  end

  class Kraken < BattleCruiser
    weapon class: LaserCannon, damage: 10
    armor  6000
    shield  600
    threat    1
  end

  class LargeTransporter < BattleCruiser
    weapon class: LaserCannon, damage: 10
    armor  8000
    shield  800
    threat    3
  end

  class Valkyrie < Battleship
    2.times { weapon class: IonCannon, damage: 5500 }
    armor  42000
    shield  4200
    threat    30
  end

  class Atlas < Battleship
    2.times { weapon class: PlasmaCannon, damage: 6000 }
    armor  39000
    shield  3900
    threat    30
  end

  class Karma < Battleship
    10.times { weapon class: LaserCannon, damage:  500 }
    10.times { weapon class: IonCannon,   damage: 4000 }
    armor  42000
    shield  8400
    threat    30
  end

  class Galactica < Battleship
    armor  120000
    shield  60000
    threat    500
  end

  class Gigantes < Battleship
    weapon class: EMPCannon, range: 15
    armor  40000
    shield  8000
    threat    30
  end

  class Aegis < Battleship
    weapon class: SpecialShield, damage: 10000, range: 10
    armor  40000
    shield  8000
    threat    30
  end

  class DivineStar < Carrier
    50.times { weapon class: CruiseMissile, damage: 6000 }
    armor  180000
    shield  36000
    threat     60
  end

  class Guillotine < Carrier
    30.times { weapon class: SMMissile, damage: 600 }
    4.times { weapon class: PhotonCannon, damage: 1200, range: 6 }
    4.times { weapon class: NeutronCannon, damage: 2200, range: 10 }
    armor  150000
    shield  30000
    threat     60
  end

  class WraithOfGod < Carrier
    12.times { weapon class: IonCannon, damage: 5000 }
    5.times { weapon class: PlasmaCannon, damage: 20000 }
    armor  150000
    shield  30000
    threat     60
  end

  class DeckPlain < Cruiser
    weapon class: LaserCannon, damage: 350
    armor 1
    shield 1
    threat 1
  end

  class Armageddon < Titan
    armor  600000
    shield 180000
    threat    100

    def initialize(owner, research = {}, factors = {})
      super owner, research, factors
      @research = research
      @factors = factors
    end

    def round_start(fleet)
      super fleet
      1000.times { fleet.push DeckPlain.new(self.owner, @research, @factors) }
    end

    def round_end(fleet)
      super fleet
      fleet.delete_if{|x| x.kind_of? DeckPlain }
    end
  end

  class Harlock < Titan
    100.times { weapon class: SMMissile, damage: 250 }
    2.times { weapon class: AntimatterCannon, damage: 20000, range: 10 }

    armor  550000
    shield 165000
    threat    100
  end

  class Odyssey < Titan
    2.times { weapon class: NeutronCannon, damage: 30000, range: 2 }
    2.times { weapon class: AntimatterCannon, damage: 150000, range: 2 }

    armor  500000
    shield 150000
    threat    100
  end
end
