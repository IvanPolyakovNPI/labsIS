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

    private static Matrix parseMatrix(String path){
        Vector<Vector<Integer>> matrix = new Vector<>();
        Vector<Integer> size = new Vector<>();
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonData = (JSONObject) parser.parse(getJsonFile(path));
            JSONArray array = (JSONArray) jsonData.get("matrix");
            array.forEach(a ->{//  получим матрицу состоящию из 0 и 1
                JSONArray array1 = (JSONArray) a;
                Vector<Integer> vector = new Vector<>();
                array1.forEach(a1 ->{
                    Integer number = Integer.parseInt(a1.toString());
                    vector.add(number);
                });
                matrix.add(vector);
            });
            JSONArray array1 = (JSONArray) jsonData.get("size");// далее получим размер матрицы (это нужно для проверки на всякий случай)
            size.add(Integer.parseInt(array1.get(0).toString()));
            size.add(Integer.parseInt(array1.get(1).toString()));
        }catch (Exception e){
            e.printStackTrace();
        }
        Matrix matrix1 = new Matrix(matrix, size);
        return matrix1;
    }

    public static void main(String args[]){
        // для начала создадим классы
        System.out.println("Enter amount of classes: ");
        Scanner scanner = new Scanner(System.in);
        int amountOfClasses = Integer.parseInt(scanner.nextLine());
        Vector<Core> cores = new Vector<>();
        while (amountOfClasses > 0){
            Vector<Matrix> matrices = new Vector<>();
            System.out.println("Enter amount of objects required to create the core: ");
            int amountOfObjects = Integer.parseInt(scanner.nextLine());
            while (amountOfObjects > 0){
                System.out.println("Enter path:");
                String path = scanner.nextLine();
                Matrix matrix = parseMatrix(path);
                matrix.printMatrix();
                matrices.add(matrix);
                amountOfObjects--;
            }
            System.out.println("Enter name of this class: ");
            String name = scanner.nextLine();
            Core core = new Core(matrices, name);
            cores.add(core);
            amountOfClasses--;
        }
        System.out.println("Classes: ");// распечатаем полученые классы
        cores.forEach(Core::printCore);
        // далее запускается бесконечный цикл (пока вы не введете "end"), в котором вы вводите путь до объектов, которые хотите классифицировать
        int stop = 0;
        while (stop == 0){
            System.out.println("Enter the path to the object to classify or enter \"end\" to stop the program:");
            String p = scanner.nextLine();
            if(p.equals("end")){
                stop++;
            }else {
                Matrix m = parseMatrix(p);
                m.printMatrix();
                m.foundClass(cores);
            }
        }


    }
}
