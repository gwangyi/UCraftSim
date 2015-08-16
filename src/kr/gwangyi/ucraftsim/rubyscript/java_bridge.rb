class LoggerBridge
  def initialize(logger)
    @logger = logger
  end

  def round_start
    @logger.roundStart
  end

  def attack(ship, weapon, target, damage)
    @logger.attack ship.side == :a, ship.shiptype.gsub(/[A-Z]/){|c| "_" + c.downcase }[1..-1], target.shiptype.gsub(/[A-Z]/){|c| "_" + c.downcase }[1..-1], damage
  end

  def destroyed(ship)
    @logger.destroyed ship.side == :a, ship.shiptype.gsub(/[A-Z]/){|c| "_" + c.downcase }[1..-1]
  end

  def round_end
    @logger.roundEnd
  end
end

class BattleSimBridge
  include Java::kr.gwangyi.ucraftsim.BattleSim

  def init(attacker, defender)
    a = { :fleet => {}, :research => {}, :factor => {} }
    b = { :fleet => {}, :research => {}, :factor => {} }
    attacker.getFleet.entrySet.each do |entry|
      a[:fleet][entry.getKey.to_sym] = entry.getValue.to_i
    end
    attacker.getResearch.entrySet.each do |entry|
      a[:research][entry.getKey.to_sym] = entry.getValue.to_i
    end
    attacker.getFactor.entrySet.each do |entry|
      a[:factor][entry.getKey.to_sym] = entry.getValue.to_i
    end
    defender.getFleet.entrySet.each do |entry|
      b[:fleet][entry.getKey.to_sym] = entry.getValue.to_i
    end
    defender.getResearch.entrySet.each do |entry|
      b[:research][entry.getKey.to_sym] = entry.getValue.to_i
    end
    defender.getFactor.entrySet.each do |entry|
      b[:factor][entry.getKey.to_sym] = entry.getValue.to_i
    end
    @battlesim = UCraftSim::BattleSim.new(a, b)
  end

  def setLogger(logger)
    @battlesim.logger = LoggerBridge.new(logger)
  end

  def round
    @battlesim.round
  end

  def compare
    @battlesim.compare
  end
end

BattleSimBridge.new