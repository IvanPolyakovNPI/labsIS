import java.util.*;

public class Graph {// класс для хранения информации о графе
    private Vector<String> knots;// вектор названий узлов
    private Vector<Connection> connections;// хэшмэп название узла --> вектор его соседей (объекты Connection)

    public Graph(Vector<String> knots, Vector<Connection> connections){//конструктор класса
        this.knots = knots;
        this.connections = connections;
    }
    // далее геттеры и сеттеры для переменных класса
    public void setConnections(Vector<Connection> connections) {
        this.connections = connections;
    }

    public void setKnots(Vector<String> knots) {
        this.knots = knots;
    }

    public Vector<Connection> getConnections() {
        return connections;
    }

    public Vector<String> getKnots() {
        return knots;
    }

    public void printGraph(){// метод выводящий в консоль всю информацию о графе (я использовал его для проверки парсера)
        System.out.println("1)Knots:");
        knots.forEach(k -> System.out.print(k + " "));
        System.out.println("\n2)Connections:");
        connections.forEach(c ->{
            System.out.println(c.getFrom() + " <--> " + c.getTo() + " (weight: " + c.getWeight() + ") ");
        });
    }

    public float foundAllWeight(){// метод метод для нахождения общего веса графа
        float weight = 0;
        for (Connection connection : connections) {
            weight += connection.getWeight();
        }
        return weight;
    }

    public Graph prim(){// далее создаем метод, который работает по алгоритму прима и возвращает максимальное покрывающее дерево для данного графа
        Vector<String> scannedKnots = new Vector<>();// вектор для хранения рассмотренных вершин
        Vector<Connection> scannedConnections = new Vector<>();// вектор для хранения рассмотреных и подходящих ребер
        Vector<Connection> sortedConnections = new Vector<>();// вектор для хранения ребер, рассположенных по убываюнию веса
        //далее находим максимальный вес
        float maxWeight = connections.get(0).getWeight();
        for (Connection connection : connections) {
            if (connection.getWeight() > maxWeight) {
                maxWeight = connection.getWeight();
            }
        }
        // далее заполняем вектор для хранения ребер
        while (maxWeight > 0){
            for (Connection connection : connections) {
                if(connection.getWeight() == maxWeight && !sortedConnections.contains(connection)){
                    sortedConnections.add(connection);
                }
            }
            maxWeight--;
        }
        //для запуска алгоритма добавим в наши векторы ребро с самым большим весом и 2 узла, которые оно связывает
        scannedKnots.add(sortedConnections.get(0).getFrom());
        scannedKnots.add(sortedConnections.get(0).getTo());
        scannedConnections.add(sortedConnections.get(0));
        sortedConnections.remove(0);
        int i = 0;
        while(scannedKnots.size() < knots.size()){// запускаем цыкл, где параметр остановки -- когда в векторе scannedKnots будут все узлы вектора
            Connection c = sortedConnections.get(i);
            //далее проверяем является ли данное ребро инцедентным нашим вершинам и добавляем его в вектор ребер, если является (так же добавляем новый узел)
            //если однин изу узлов уже есть в списке, ав торого нет, добавляем ребро и обнулем i, елси у нас уже есть ребро...
            //соединяющее данные узлы удаялем данное ребро из отсортированного списка, и если мы не рассматривали ни один узел, связанный данным ребром, увеличиваем i на 1  идем дальше по списку
            if(scannedKnots.contains(c.getTo()) && !scannedKnots.contains(c.getFrom())){
                scannedKnots.add(c.getFrom());
                scannedConnections.add(c);
                sortedConnections.remove(i);
                i = 0;
            }else if (!scannedKnots.contains(c.getTo()) && scannedKnots.contains(c.getFrom())){
                scannedKnots.add(c.getTo());
                scannedConnections.add(c);
                sortedConnections.remove(i);
                i = 0;
            }else if (scannedKnots.contains(c.getTo()) && scannedKnots.contains(c.getFrom())){
                sortedConnections.remove(i);
                i = 0;
            }else if(!scannedKnots.contains(c.getTo()) && !scannedKnots.contains(c.getFrom())){
                i++;
            }

        }
        Graph graph = new Graph(scannedKnots,scannedConnections);// создаем дерево
        return graph;
    }

}

