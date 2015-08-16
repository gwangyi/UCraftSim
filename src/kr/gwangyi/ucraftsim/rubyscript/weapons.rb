module UCraftSim
  class LaserCannon < Weapon
    efficiency Frigate,       1
    efficiency Cruiser,       1
    efficiency BattleCruiser, 1
    efficiency Battleship,    1
    efficiency Carrier,       0.5
    efficiency Titan,         0.25
  end

  class SMMissile < Weapon
    weapon_research :sm_missile

    efficiency Frigate,       1
    efficiency Cruiser,       0.25
    efficiency BattleCruiser, 0.25
    efficiency Battleship,    0.25
    efficiency Carrier,       0.25
    efficiency Titan,         0.25
  end

  class CruiseMissile < Weapon
    defence_only
  end

  class PhotonCannon < Weapon
    efficiency Frigate,       0.5
    efficiency Cruiser,       1
    efficiency BattleCruiser, 0.5
    efficiency Battleship,    0.5
    efficiency Carrier,       0.5
    efficiency Titan,         0.5
  end

  class IonCannon < Weapon
    efficiency Frigate,       0.5
    efficiency Cruiser,       0.5
    efficiency BattleCruiser, 1
    efficiency Battleship,    0.5
    efficiency Carrier,       0.5
    efficiency Titan,         0.5
  end

  class PlasmaCannon < Weapon
    efficiency Frigate,       0.25
    efficiency Cruiser,       0.25
    efficiency BattleCruiser, 0.25
    efficiency Battleship,    1
    efficiency Carrier,       1
    efficiency Titan,         0.75
  end

  class NeutronCannon < Weapon
    efficiency Frigate,       0.25
    efficiency Cruiser,       0.25
    efficiency BattleCruiser, 0.75
    efficiency Battleship,    1
    efficiency Carrier,       0.75
    efficiency Titan,         0.25
  end

  class AntimatterCannon < Weapon
    efficiency Frigate,       1
    efficiency Cruiser,       1
    efficiency BattleCruiser, 1
    efficiency Battleship,    1
    efficiency Carrier,       1
    efficiency Titan,         1
  end

  class EMPCannon < Weapon
    def attack(weighted_fleet, weighted_defences, weighted_friends)
      cnt = @range * 2 + 1
      cnt.times do
        s = weighted_fleet.sample
        s.damage s.shield
        @owner.owner.logger.attack @owner, self, s, 0
      end
    end
  end

  class SpecialShield < Weapon
    def attack(weighted_fleet, weighted_defences, weighted_friends)
      cnt = @range * 2 + 1
      cnt.times do
        s = weighted_friends.sample
        s.damage(-@damage)
        @owner.owner.logger.attack @owner, self, s, @damage
      end
    end
  end
end
