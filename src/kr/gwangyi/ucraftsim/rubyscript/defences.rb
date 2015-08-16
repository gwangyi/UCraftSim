module UCraftSim
  class LaserTurret < Frigate
    defence
    weapon class: LaserCannon, damage: 180
    armor  1000
    shield  100
    threat    1
  end

  class PhotonTurret < Cruiser
    defence
    weapon class: PhotonCannon, damage: 180, range: 5
    armor  1000
    shield  100
    threat    2
  end

  class IonTurret < BattleCruiser
    defence
    weapon class: IonCannon, damage: 4000
    armor  18000
    shield  1800
    threat     3
  end

  class PlasmaTurret < Battleship
    defence
    weapon class: PlasmaCannon, damage: 12000
    armor  50000
    shield  5000
    threat     4
  end

  class NeutronTurret < Carrier
    defence
    weapon class: PlasmaCannon, damage: 2800, range: 5
    armor  70000
    shield  7000
    threat     5
  end

  class AntimatterTurret < Titan
    defence
    weapon class: AntimatterCannon, damage: 5000, range: 5
    armor  150000
    shield  15000
    threat      6
  end
end
