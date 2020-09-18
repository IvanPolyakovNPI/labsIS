import java.util.*;

public class Graph {// класс для хранения информации о графе
    private Vector<String> knots;// вектор названий узлов
    private HashMap<String, Vector<Connection>> connections;// хэшмэп название узла --> вектор его соседей (объекты Connection)

    public Graph(Vector<String> knots, HashMap<String, Vector<Connection>> connections){//конструктор класса
        this.knots = knots;
        this.connections = connections;
    }
    // далее геттеры и сеттеры для переменных класса
    public void setConnections(HashMap<String, Vector<Connection>> connections) {
        this.connections = connections;
    }

    public void setKnots(Vector<String> knots) {
        this.knots = knots;
    }

    public HashMap<String, Vector<Connection>> getConnections() {
        return connections;
    }

    public Vector<String> getKnots() {
        return knots;
    }

    public void printGraph(){// метод выводящий в консоль всю информацию о графе (я использовал его для проверки парсера)
        System.out.println("Graph:");
        System.out.println("1)Knots:");
        knots.forEach(k -> System.out.print(k + " "));
        System.out.println("\n2)Connections:");
        connections.keySet().forEach(key ->{
            System.out.print(key + " -> ");
            connections.get(key).forEach(connection -> System.out.print(connection.getGoal() + "(weight: " + connection.getWeight() + ") "));
            System.out.println();
        });
    }

    public void dijkstra(String start,String goal){// далее реализация метода поска Дикстры
        // далее создаем OPEN и CLOSE в уддобном формате (используем объекты класса Knot для хранения текущего веса и названии кзла из которого мы пришли в текущий узел)
         TreeMap<String,Knot> open = new TreeMap<>();
        HashMap<String,Knot> close = new HashMap<>();
        Knot knot = new Knot(start, "", 0);
        open.put(start,knot);//кладем в OPEN информацию о стартовом узле
        // далее цикл while, критерии остановки: 1) OPEN оказался пустым в ходе работы => мы рассмотрели все возможные узлы 2) goal узел был рассмотрен и перемещен из OPEN в CLOSE
        // далее будет объяснение почему в уловиях цикла именно это (!(close.containsKey(goal) && !open.containsKey(goal)))
        while (open.size() != 0 && !(close.containsKey(goal) && !open.containsKey(goal))){

            Vector<Knot> neighbors = new Vector<>();// вектор для хранения соседних узлов и информации о них
            //далее запоминаем имя и вес случайного узла в OPEN
            float number = open.get(open.firstKey()).getWeight();
            String nowKnot = open.get(open.firstKey()).getName();

           Vector<String> vector = new Vector<>();// вектор для хранения названий узлов хранящихся в OPEN
           vector.addAll(open.keySet());
            for (int i = 0; i < vector.size(); i++){// далее перебираем все узлы в OPEN и находим узел с минимальным текущим весом пути (рассчитанным начиная от стартового узла)
                if(number > open.get(vector.get(i)).getWeight()){
                    number = open.get(vector.get(i)).getWeight();
                    nowKnot = open.get(vector.get(i)).getName();
            }
            }
                String finalNowKnot = nowKnot;
                connections.get(nowKnot).forEach(connection ->{// далее запоминаем всех соседей текущего рассматриваемого узла в вектор
               if(!connection.getGoal().equals(open.get(finalNowKnot).getFrom())){
                   float weight = open.get(finalNowKnot).getWeight() + connection.getWeight();
                   Knot k = new Knot(connection.getGoal(), finalNowKnot, weight);
                   neighbors.add(k);
               }
           });

           neighbors.forEach(neighbor ->{// далее перебираем вектор соседей
               String name = neighbor.getName();
               if(!close.containsKey(name)){// если CLOSE еще не содержит рассматриваемого соседа добавляем его в оба списка
                   // ПОЯСНЕНИЕ: мы добавляем соседей в оба списка, так как в ходе рассмотрения других узлов мы можем найти более короткий путь до данного соседа и его значение веса может изменяться
                   // это изменение веса ломало метод и вес считался некоректно, поэтому мне пришлось сделать данное допущение с добавлением соседий сразу в оба списка
                   // с ним все работает как нужно и вес узлов в CLOSE может коректно меняться, но теперь окончательным добавлением узла в CLOSE можно считать момент, когда мы удаляем данный узел из списка OPEN
                   open.put(name,neighbor);
                   close.put(name,neighbor);
               }else if (close.get(name).getWeight() > neighbor.getWeight()){// если мы уже рассматривали данного соседа и нашли более короткий путь к нему
                   close.get(name).setFrom(neighbor.getFrom());
                   close.get(name).setWeight(neighbor.getWeight());
               }

           });

           open.remove(nowKnot);// удаляем рассмотренный узел из списка OPEN
        }
        //далее если мы нашли путь выводим его в консоль и его стоимость
        if(!close.containsKey(goal)){
            System.out.println("Path not found!");
        }else {
            System.out.println("Path length: " + close.get(goal).getWeight());
            String path = "";
            while (goal != start){
                path = path + goal + " <-- ";
                goal = close.get(goal).getFrom();
            }
            path += goal;
            System.out.println("Path: " + path);
        }
    }

    public void bfs(String start,String goal){// метод посика в ширину
        // создаем OPEN и CLOSE
        List<Knot> open = new ArrayList<>();// на этот раз используем ArrayList, так как данный список работает по принципу first in first out
        HashMap<String,Knot> close = new HashMap<>();
        Knot knot = new Knot(start, "", 0);
        open.add(knot);
        // далее цикл while, условия остановки:  1) OPEN оказался пустым в ходе работы => мы рассмотрели все возможные узлы 2) мы встретили искомый узел CLOSE в списке соседей
        while (open.size() != 0 && !close.containsKey(goal)){
            // далее метод почти такой же как Дикстра за ислкючением того, что вес 1 перехода всегда равен 1 и мы рассматриваем список OPEN по принципу фифо
            Vector<Knot> neighbors = new Vector<>();
            String nowKnot = open.get(0).getName();
            connections.get(nowKnot).forEach(connection ->{
                if(!connection.getGoal().equals(open.get(0).getFrom())){
                    float weight = open.get(0).getWeight() + 1;
                    Knot k = new Knot(connection.getGoal(), nowKnot, weight);
                    neighbors.add(k);
                }
            });

            neighbors.forEach(neighbor ->{
                String name = neighbor.getName();
                if(!close.containsKey(name)){
                    open.add(neighbor);
                    close.put(name,neighbor);
                }else if (close.get(name).getWeight() > neighbor.getWeight()){
                    close.get(name).setFrom(neighbor.getFrom());
                    close.get(name).setWeight(neighbor.getWeight());
                }

            });

            open.remove(0);
        }

        if(!close.containsKey(goal)){
            System.out.println("Path not found!");
        }else {
            System.out.println("Path length: " + close.get(goal).getWeight());
            String path = "";
            while (goal != start){
                path = path + goal + " <-- ";
                goal = close.get(goal).getFrom();
            }
            path += goal;
            System.out.println("Path: " + path);
        }
     }
}

