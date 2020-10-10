import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

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
        Vector<Connection> con = new Vector<>();
        Vector<String> vector = new Vector<>();
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonData = (JSONObject) parser.parse(getJsonFile(path));
            JSONArray knots = (JSONArray) jsonData.get("knots");
            knots.forEach(knot -> vector.add(knot.toString()));//заполняем вектор названий узлов
            JSONArray connections = (JSONArray) jsonData.get("connections");
            for (int i = 0; i < connections.size();i++){// далее заполняем вектор соединений узлов
                JSONObject object = (JSONObject) connections.get(i);
                JSONArray array = (JSONArray) object.get("connectionKnots");
                int weight = Integer.parseInt(object.get("weight").toString());
                Connection connection = new Connection(array.get(1).toString(),array.get(0).toString(),weight);
                con.add(connection);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Graph graph = new Graph(vector,con);
        return graph;
    }

    public static void main(String args[]){
        System.out.println("Enter path:");
        Scanner scanner = new Scanner(System.in);
        String path = scanner.nextLine();// вводим с клавиатуры путь к файлу и далее в зависимости от содержимого файла запускаются различные парсеры и методы
        Graph graph = parseGraph(path);
        System.out.println("Graph info:");
        graph.printGraph();
        System.out.println("===================================================================================");
        System.out.println("Graph (tree) info:");
        Graph g = graph.prim();
        g.printGraph();
        System.out.println("Weight of all connections: " + g.foundAllWeight());

    }
}
