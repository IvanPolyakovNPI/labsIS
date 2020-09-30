(define (domain minecraft) 
  (:requirements
    :strips                 
    :negative-preconditions 
    :equality
    :typing
  )
  (:types
    player
    place
    oreVein
    wood
    stone
    coal
    ironOre
    diamond
    location
    workbench
    foundry
  )
  (:predicates
    (in ?location - location ?place - place)
    (at ?x ?location - location)
    (contain ?x ?oreVein - oreVein)
    (haveWood ?Steve - player ?wood - wood)
    (haveStone ?Steve - player ?stone - stone)
    (haveCoal ?Steve - player ?coal - coal)
    (haveIronOre ?Steve - player ?ironOre - ironOre)
    (haveIron ?Steve - player)
    (haveDiomand ?Steve - player ?diamond - diamond)
    (haveWoodPickaxe ?Steve - player)
    (haveStonePickaxe ?Steve - player)
    (haveIronPickaxe ?Steve - player)
  )
  (:action move
        :parameters (?Steve - player ?from - location ?to - location ?place - place)
        :precondition (and 
        (at ?Steve ?from)
        (not(at ?Steve ?to))
        (not (= ?from ?to))
        (in ?from ?place)
        (in ?to ?place)
        )
        :effect (and (not(at ?Steve ?from))(at ?Steve ?to))
  )
  (:action cutTree
        :parameters (?Steve - player ?location - location ?wood - wood ?oreVein - oreVein)
        :precondition (and 
        (at ?Steve ?location)
        (at ?oreVein ?location)
        (contain ?wood ?oreVein)
        (not(haveWood ?Steve ?wood))
        )
        :effect (at ?wood ?location)
  )
  (:action takeWood
        :parameters (?Steve - player ?location - location ?wood - wood)
        :precondition (and 
        (at ?Steve ?location)
        (at ?wood ?location)
        (not(haveWood ?Steve ?wood))
        )
        :effect (and(haveWood ?Steve ?wood)(not(at ?wood ?location)))
  )
  (:action craftWoodPickaxe
       :parameters (?Steve - player ?location - location ?wood - wood ?workbench - workbench)
        :precondition (and 
        (at ?Steve ?location)
        (at ?workbench ?location)
        (haveWood ?Steve ?wood)
        (not(haveWoodPickaxe ?Steve))
        )
        :effect (and(not(haveWood ?Steve ?wood))(haveWoodPickaxe ?Steve))
  )
  (:action mineStone
        :parameters (?Steve - player ?location - location ?stone - stone ?oreVein - oreVein)
        :precondition (and 
        (at ?Steve ?location)
        (at ?oreVein ?location)
        (contain ?stone ?oreVein)
        (not(haveStone ?Steve ?stone))
        (haveWoodPickaxe ?Steve)
        )
        :effect (at ?stone ?location)
  )
  (:action takeStone
        :parameters (?Steve - player ?location - location ?stone - stone)
        :precondition (and 
        (at ?Steve ?location)
        (at ?stone ?location)
        (not(haveStone ?Steve ?stone))
        )
        :effect (and(haveStone ?Steve ?stone)(not(at ?stone ?location)))
  )
  (:action craftStonePickaxe
       :parameters (?Steve - player ?location - location ?stone - stone ?wood - wood ?workbench - workbench)
        :precondition (and 
        (at ?Steve ?location)
        (at ?workbench ?location)
        (haveWood ?Steve ?wood)
        (haveStone ?Steve ?stone)
        (not(haveStonePickaxe ?Steve))
        )
        :effect (and
        (not(haveWood ?Steve ?wood))
        (not(haveStone ?Steve ?stone))
        (haveStonePickaxe ?Steve)
        )
  )
  (:action mineIronOre
        :parameters (?Steve - player ?location - location ?ironOre - ironOre ?oreVein - oreVein)
        :precondition (and 
        (at ?Steve ?location)
        (at ?oreVein ?location)
       (contain ?ironOre ?oreVein)
        (not(haveIronOre ?Steve ?ironOre))
       (haveStonePickaxe ?Steve)
        )
        :effect (at ?ironOre ?location)
  )
  (:action takeIronOre
       :parameters (?Steve - player ?location - location ?ironOre - ironOre)
        :precondition (and 
        (at ?Steve ?location)
        (at ?ironOre ?location)
        (not(haveIronOre ?Steve ?ironOre))
        )
        :effect (and(haveIronOre ?Steve ?ironOre)(not(at ?ironOre ?location)))
  )
  (:action mineCoal
        :parameters (?Steve - player ?location - location ?coal - coal ?oreVein - oreVein)
        :precondition (and 
        (at ?Steve ?location)
        (at ?oreVein ?location)
        (contain ?coal ?oreVein)
        (not(haveCoal ?Steve ?coal))
        (haveStonePickaxe ?Steve)
        )
        :effect (at ?coal ?location)
  )
   (:action takeCoal
       :parameters (?Steve - player ?location - location ?coal - coal)
        :precondition (and 
        (at ?Steve ?location)
        (at ?coal ?location)
        (not(haveCoal ?Steve ?coal))
        )
        :effect (and(haveCoal ?Steve ?coal)(not(at ?coal ?location)))
  )
  (:action meltIron
       :parameters (?Steve - player ?location - location ?ironOre - ironOre ?coal - coal ?foundry - foundry)
        :precondition (and 
        (at ?Steve ?location)
        (at ?foundry ?location)
        (haveCoal ?Steve ?coal)
        (haveIronOre ?Steve ?ironOre)
        (not(haveIron ?Steve))
        )
        :effect (and(not(haveCoal ?Steve ?coal))(not(haveIronOre ?Steve ?ironOre))(haveIron ?Steve))
  )
  (:action craftIronPickaxe
       :parameters (?Steve - player ?location - location ?wood - wood ?workbench - workbench)
        :precondition (and 
        (at ?Steve ?location)
        (at ?workbench ?location)
        (haveIron ?Steve)
        (haveWood ?Steve ?wood)
        (not(haveIronPickaxe ?Steve))
        )
        :effect (and
        (not(haveWood ?Steve ?wood))
        (not(haveIron ?Steve))
        (haveIronPickaxe ?Steve)
        )
  )
  (:action mineDiamond
        :parameters (?Steve - player ?location - location ?diamond - diamond ?oreVein - oreVein)
        :precondition (and 
        (at ?Steve ?location)
        (at ?oreVein ?location)
        (contain ?diamond ?oreVein)
        (not(haveDiamond ?Steve ?diamond))
        (haveIronPickaxe ?Steve)
        )
        :effect (at ?diamond ?location)
  )
  (:action takeDiamond
       :parameters (?Steve - player ?location - location ?diamond - diamond)
        :precondition (and 
        (at ?Steve ?location)
        (at ?diamond ?location)
        (not(haveDiamond ?Steve ?diamond))
        )
        :effect (and(haveDiamond ?Steve ?diamond)(not(at ?diamond ?location)))
  )
  (:action giveDiamond
       :parameters (?Steve1 - player ?Steve2 - player ?location - location ?diamond - diamond)
        :precondition (and 
        (at ?Steve1 ?location)
        (at ?Steve2 ?location)
        (haveDiamond ?Steve1 ?diamond)
        (not(haveDiamond ?Steve2 ?diamond))
        )
        :effect (and(haveDiamond ?Steve2 ?diamond)(not(haveDiamond ?Steve1 ?diamond)))
  )
  (:action giveCoal
       :parameters (?Steve1 - player ?Steve2 - player ?location - location ?coal - coal)
        :precondition (and 
        (at ?Steve1 ?location)
        (at ?Steve2 ?location)
        (haveCoal ?Steve1 ?coal)
        (not(haveCoal ?Steve2 ?coal))
        )
        :effect (and(haveCoal ?Steve2 ?coal)(not(haveCoal ?Steve1 ?coal)))
  )
  (:action giveWood
       :parameters (?Steve1 - player ?Steve2 - player ?location - location ?wood - wood)
        :precondition (and 
        (at ?Steve1 ?location)
        (at ?Steve2 ?location)
        (haveWood ?Steve1 ?wood)
        (not(haveWood ?Steve2 ?wood))
        )
        :effect (and(haveWood ?Steve2 ?wood)(not(haveWood ?Steve1 ?wood)))
  )
  (:action giveStone
       :parameters (?Steve1 - player ?Steve2 - player ?location - location ?stone - stone)
        :precondition (and 
        (at ?Steve1 ?location)
        (at ?Steve2 ?location)
        (haveStone ?Steve1 ?stone)
        (not(haveStone ?Steve2 ?stone))
        )
        :effect (and(haveStone ?Steve2 ?stone)(not(haveStone ?Steve1 ?stone)))
  )
  (:action giveIronOre
       :parameters (?Steve1 - player ?Steve2 - player ?location - location ?ironOre - ironOre)
        :precondition (and 
        (at ?Steve1 ?location)
        (at ?Steve2 ?location)
        (haveIronOre ?Steve1 ?ironOre)
        (not(haveIronOre ?Steve2 ?ironOre))
        )
        :effect (and(haveIronOre ?Steve2 ?ironOre)(not(haveIronOre ?Steve1 ?ironOre)))
  )
)