(define (problem pb1)
  (:domain minecraft)
  (:objects
      Steve - player
      l1 l2 l3 l4 l5 l6 l7 - location
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
      (at Steve l6)
      (contain wood tree)
      (contain stone rock1)
      (contain ironOre rock2)
      (contain coal rock3)
      (contain diamond rock4)
    )
    (:goal (haveDiamond Steve diamond))
)