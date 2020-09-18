import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class Main {

    private static String getJsonFile(String path) // метод для парсинга json файла по пути к нему
    {
        StringBuilder builder = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            lines.forEach(builder::append);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return builder.toString();
    }


    private static Graph parseGraph(String path){// метод для расшифровки графа и создания объекта класса Graph
        //далее временные переменные для хранения информации о узлах графа и их соединениях
        HashMap<String,Vector<Connection>> map = new HashMap<>();
        Vector<String> vector = new Vector<>();
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonData = (JSONObject) parser.parse(getJsonFile(path));
            JSONArray knots = (JSONArray) jsonData.get("knots");
            knots.forEach(knot -> vector.add(knot.toString()));//заполняем вектор названий узлов
            JSONArray connections = (JSONArray) jsonData.get("connections");
            for (int i = 0; i < connections.size();i++){// далее заполняем HashMap соединений узлов
                JSONObject object = (JSONObject) connections.get(i);
                JSONArray array = (JSONArray) object.get("connectionKnots");
                int weight = Integer.parseInt(object.get("weight").toString());
                if(map.containsKey(array.get(0).toString())){
                    Connection connection = new Connection(array.get(1).toString(),weight);
                    map.get(array.get(0).toString()).add(connection);
                }else {
                    Connection connection = new Connection(array.get(1).toString(),weight);
                    Vector<Connection> t = new Vector<>();
                    t.add(connection);
                    map.put(array.get(0).toString(), t);
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        Graph graph = new Graph(vector,map);
        return graph;
    }

    public static Grid parseGrid(String path){// метод для расшифровки грида и создания объекта класса Grid
        Vector<Vector<Integer>> gridMap = new Vector<>();
        Vector<String> knots = new Vector<>();
        HashMap<String,Vector<Integer>> coordinates = new HashMap<>();
        HashMap<String, Vector<Connection>> connections = new HashMap<>();
        int amountOfConnections = 0;
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonData = (JSONObject) parser.parse(getJsonFile(path));
            JSONArray array = (JSONArray) jsonData.get("grid");
            amountOfConnections = Integer.parseInt(jsonData.get("amountOfConnections").toString());
            array.forEach(a ->{// сначала мы получим матрицу грида состоящию из 0 и 1, а потом по ней заполним вектор названий узлов и их соединений и координат
                JSONArray array1 = (JSONArray) a;
                Vector<Integer> vector = new Vector<>();
                array1.forEach(a1 ->{
                    Integer number = Integer.parseInt(a1.toString());
                     vector.add(number);
                        });
                gridMap.add(vector);
            });
            for (int i = 0; i < gridMap.size(); i++){// далее мы перибираем матрицу грида 2 раза и получаем всю нужную информацию
                for (int j = 0; j < gridMap.get(i).size(); j++){
                    if(gridMap.get(i).get(j) == 0){
                        Vector<Integer> coordinate = new Vector<>();
                        coordinate.add(i);
                        coordinate.add(j);
                        String name = Integer.toString(i) + j;
                        knots.add(name);
                        coordinates.put(name,coordinate);
                        Vector<Connection> connection = new Vector<>();
                        if(i < 9 && (i + j) != 18){
                            if(gridMap.get(i + 1).get(j) == 0){
                                String c1 = Integer.toString(i + 1) + j;
                                Connection connection1 = new Connection(c1,1);
                                connection.add(connection1);

                            }
                        }
                        if(j < 9 && (i + j) != 18){
                            if(gridMap.get(i).get(j + 1) == 0){
                                String c2 = i + Integer.toString(j + 1);
                                Connection connection2 = new Connection(c2,1);
                                connection.add(connection2);
                            }
                        }

                        connections.put(name,connection);


                    }
                }
            }
            for (int i = gridMap.size() - 1; i > -1; i--){// второй раз мы ее перебираем чтобы заполнить соединения в обе стороны
                for (int j = gridMap.get(i).size() - 1; j > -1; j--){
                    if(gridMap.get(i).get(j) == 0){
                        String name = Integer.toString(i) + j;
                        Vector<Connection> connection = new Vector<>();
                        if(i > 0){
                            if(gridMap.get(i - 1).get(j) == 0){
                                String c1 = Integer.toString(i - 1) + j;
                                Connection connection1 = new Connection(c1,1);
                                connection.add(connection1);

                            }
                        }
                        if(j > 0){
                            if(gridMap.get(i).get(j - 1) == 0){
                                String c2 = i + Integer.toString(j - 1);
                                Connection connection2 = new Connection(c2,1);
                                connection.add(connection2);
                            }
                        }
                        connection.forEach(c -> {
                           connections.get(name).add(c);
                        });
                    }
                }
            }
            if(amountOfConnections == 8){// если это восьмисвязный грид мы перебираем его еще 4 раза чтобы заполнить диагональные соединения
                for (int i = gridMap.size() - 1; i > -1; i--){
                    for (int j = gridMap.get(i).size() - 1; j > -1; j--){
                        if(gridMap.get(i).get(j) == 0){
                            String name = Integer.toString(i) + j;
                            if(i > 0 && j > 0){
                                String to = Integer.toString(i - 1) + (j - 1);
                                Connection c = new Connection(to, (float) Math.sqrt(2));
                                connections.get(name).add(c);
                            }
                        }
                    }
                }
                for (int i = gridMap.size() - 1; i > -1; i--){
                    for (int j = 0; j < gridMap.size(); j++){
                        if(gridMap.get(i).get(j) == 0){
                            String name = Integer.toString(i) + j;
                            if(i > 0 && j < 9){
                                String to = Integer.toString(i - 1) + (j + 1);
                                Connection c = new Connection(to, (float) Math.sqrt(2));
                                connections.get(name).add(c);
                            }
                        }
                    }
                }
                for (int i = 0; i < gridMap.size(); i++){
                    for (int j = gridMap.get(i).size() - 1; j > -1; j--){
                        if(gridMap.get(i).get(j) == 0){
                            String name = Integer.toString(i) + j;
                            if(i < 9 && j > 0){
                                String to = Integer.toString(i + 1) + (j - 1);
                                Connection c = new Connection(to, (float) Math.sqrt(2));
                                connections.get(name).add(c);
                            }
                        }
                    }
                }
                for (int i = 0; i < gridMap.size(); i++){
                    for (int j = 0; j < gridMap.size(); j++){
                        if(gridMap.get(i).get(j) == 0){
                            String name = Integer.toString(i) + j;
                            if(i < 9 && j < 9){
                                String to = Integer.toString(i + 1) + (j + 1);
                                Connection c = new Connection(to, (float) Math.sqrt(2));
                                connections.get(name).add(c);
                            }
                        }
                    }
                }
            }


        }catch (Exception e){
            e.printStackTrace();
        }


        Grid grid = new Grid(knots,  connections,  coordinates, amountOfConnections);
        return grid;
    }

    public static void main(String args[]){
       System.out.println("Enter path:");
        Scanner scanner = new Scanner(System.in);
        String path = scanner.nextLine();// вводим с клавиатуры путь к файлу и далее в зависимости от содержимого файла запускаются различные парсеры и методы
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonData = (JSONObject) parser.parse(getJsonFile(path));

            String type = jsonData.get("type").toString();
            String method = jsonData.get("method").toString();
            String start = jsonData.get("start").toString();
            String goal = jsonData.get("goal").toString();
            if(type.equals("graph")){
                Graph graph = parseGraph(path);
                if(method.equals("bfs")){
                    graph.bfs(start,goal);
                }else if (method.equals("dijkstra")) {
                    graph.dijkstra(start,goal);
                }
            }else if(type.equals("grid")){
                Grid grid = parseGrid(path);
                grid.aStar(start,goal);
            }


        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
