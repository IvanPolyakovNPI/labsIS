import java.util.HashMap;
import java.util.TreeMap;
import java.util.Vector;

public class Grid extends Graph {// класс Grid относледован от класса Graph, чтобы мы могли применять методы посика в ширину и дикстры к гриду
    // добавим 2 новые переменные для хранения координат узлов и колличества возможных соединений (4 или 8)
    private HashMap<String,Vector<Integer>> coordinates;
    private int amountOfConnections;

    public  Grid(Vector<String> knots, HashMap<String, Vector<Connection>> connections, HashMap<String,Vector<Integer>> coordinates, int amountOfConnections){
        super(knots,connections);
        this.coordinates = coordinates;
        this.amountOfConnections = amountOfConnections;
    }


    public HashMap<String, Vector<Integer>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(HashMap<String, Vector<Integer>> coordinates) {
        this.coordinates = coordinates;
    }

    public int getAmountOfConnections() {
        return amountOfConnections;
    }

    public void setAmountOfConnections(int amountOfConnections) {
        this.amountOfConnections = amountOfConnections;
    }

    public void printCoordinates(){// метод для вывода в консоль списка всех узлов и их координат (для вывода всего остального мы модем воспользоваться printGraph())
        System.out.print("Coordinates:");
        coordinates.keySet().forEach(key ->{
            System.out.print("\n" + key + " -- ");
            coordinates.get(key).forEach(c -> System.out.print(c + " "));
        });
    }

    public void aStar(String start, String goal){// метод почти не отличается от Дисксты, все отличия помечены комментариями
        TreeMap<String,Knot> open = new TreeMap<>();
        HashMap<String,Knot> close = new HashMap<>();
        HashMap<String, Vector<Connection>> connections = getConnections();
        //запомним координаты финальной точки
        int goalX = coordinates.get(goal).get(0);
        int goalY = coordinates.get(goal).get(1);
        Knot knot = new Knot(start, "", 0);
        open.put(start,knot);
        while (open.size() != 0  && !(close.containsKey(goal) && !open.containsKey(goal))){

            Vector<Knot> neighbors = new Vector<>();
            float number = open.get(open.firstKey()).getWeight();
            String nowKnot = open.get(open.firstKey()).getName();

            Vector<String> vector = new Vector<>();
            vector.addAll(open.keySet());
            for (int i = 0; i < vector.size(); i++){
                if(number > open.get(vector.get(i)).getWeight()){
                    number = open.get(vector.get(i)).getWeight();
                    nowKnot = open.get(vector.get(i)).getName();
                }
            }
            String finalNowKnot = nowKnot;
            connections.get(nowKnot).forEach(connection ->{
                if(!connection.getGoal().equals(open.get(finalNowKnot).getFrom())){
                    //запомним координаты текущей точки
                    int x = coordinates.get(connection.getGoal()).get(0);
                    int y = coordinates.get(connection.getGoal()).get(1);
                    float weight;// переменная для храенения значения веса для текущего рассматриваемого соседа
                    if (amountOfConnections == 4){// далее для четерехсвязного грида
                        float manhattanDistance = Math.abs(x - goalX) + Math.abs(y - goalY);// считаем расстояние манхеттана для текущего рассматриваемого соседа
                        weight = open.get(finalNowKnot).getWeight() + connection.getWeight() + manhattanDistance;// общий вес
                    }else {// далее для восьмисвязного грида
                        float minDistance = Math.min(Math.abs(x - goalX), Math.abs(y - goalY));
                        float maxDistance = Math.max(Math.abs(x - goalX), Math.abs(y - goalY));
                        float diagonalDistance = (float) (Math.sqrt(2) * minDistance + (maxDistance - minDistance));// считаем расстояние по диагональной эвристике
                        weight = open.get(finalNowKnot).getWeight() + connection.getWeight() + diagonalDistance;// общий вес
                    }
                    Knot k = new Knot(connection.getGoal(), finalNowKnot, weight);
                    neighbors.add(k);
                }
            });

            neighbors.forEach(neighbor ->{
                String name = neighbor.getName();
                if(!close.containsKey(name)){
                    open.put(name,neighbor);
                    close.put(name,neighbor);
                }else if (close.get(name).getWeight() > neighbor.getWeight()){
                    close.get(name).setFrom(neighbor.getFrom());
                    close.get(name).setWeight(neighbor.getWeight());
                }

            });

            open.remove(nowKnot);
        }

        if(!close.containsKey(goal)){
            System.out.println("Path not found!");
        }else {
            String path = "";
            float distance = 0;// создадим переменную для подсчета расстояния нашего пути
            while (goal != start){
                path = path + goal + " <-- ";
                for(int i = 0; i < connections.get(goal).size(); i++){// считаем расстояние по цене переходов совершенных в пути
                    if(connections.get(goal).get(i).getGoal().equals(close.get(goal).getFrom())){
                        distance += connections.get(goal).get(i).getWeight();
                    }
                }

                goal = close.get(goal).getFrom();

            }
            path += goal;
            System.out.println("Path: " + path);
            System.out.println("Path length: " + distance);
        }
    }
}
