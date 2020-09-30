(define (problem pb3)
  (:domain minecraft)
  (:objects
      Steve1 Steve2 Steve3 - player
      l1 l2 l3 l4 l5 l6 l7 l8 - location
      Cave Forest Home - place
      wood - wood
      stone - stone
      coal - coal
      ironOre - ironOre
      diamond - diamond
      tree rock1 rock2 rock3 rock4 - oreVein
      workbench - workbench
      foundry - foundry
  )
  (:init 
      (in l1 Cave)
      (in l2 Cave)
      (in l3 Cave)
      (in l4 Cave)
      (in l4 Forest)
      (in l5 Forest)
      (in l5 Home)
      (in l6 Home)
      (in l7 Home)
      (at tree l4)
      (at rock1 l1)
      (at rock2 l2)
      (at rock3 l3)
      (at rock4 l4)
      (at workbench l6)
      (at foundry l7)
      (at Steve1 l6)
      (at Steve2 l1)
      (at Steve3 l7)
      (contain wood tree)
      (contain stone rock1)
      (contain ironOre rock2)
      (contain coal rock3)
      (contain diamond rock4)
      (haveCoal Steve1 coal)
      (haveIronOre Steve2 IronOre)
      (haveWood Steve3 wood)
    )
    (:goal (haveDiamond Steve2 diamond))
)