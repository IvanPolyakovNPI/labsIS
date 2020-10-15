import java.util.Vector;

public class Matrix {
    private Vector<Vector<Integer>> matrix;
    private Vector<Integer> size;
    public Matrix(Vector<Vector<Integer>> matrix, Vector<Integer> size){// класс матрица для хранения объектов (бинарных изображенй)
        this.matrix = matrix;
        this.size = size;
    }
    // далее геттеры и сеттеры для данного класса
    public Vector<Integer> getSize() {
        return size;
    }

    public void setSize(Vector<Integer> size) {
        this.size = size;
    }

    public Vector<Vector<Integer>> getMatrix() {
        return matrix;
    }

    public void setMatrix(Vector<Vector<Integer>> matrix) {
        this.matrix = matrix;
    }

    public void printMatrix(){// метод для вывода бинарного изображения и его размерности
        System.out.println("Matrix: ");
        System.out.println(size.get(0) + "x" + size.get(1));
        matrix.forEach(m ->{
            m.forEach(number ->{
                System.out.print(number + " ");
            });
            System.out.print("\n");
        });
    }

    public void foundClass(Vector<Core> cores){// метода рассчитывающий расстояние до классов и определяющий класс данного объекта
        Vector<Double> distances = new Vector<>();
        for (Core core : cores) {// далее перебираем все ядра и находим расстояние до объекта по метрике Евклида
            double distance = 0;
            for (int h = 0; h < core.getCore().size(); h++) {
                for (int j = 0; j < core.getCore().get(h).size(); j++) {
                    // далее суммируем квадраты разностей всех элементов массивов (исходного объекта и ядра рассматриваемого класса)
                    float d = matrix.get(h).get(j) - core.getCore().get(h).get(j);
                    distance += (d * d);
                }
            }
            distances.add(Math.sqrt(distance));// добавляем в список расстояний квадратный кореень из данной суммы
        }
        // далее найдем минимальное расстояние и запомним его индекс в списке (индексы списка distances соответствуют индексам спика классов)
        int nearestClass = 0;
        double minDistance = distances.get(0);
        for (int z = 1; z < distances.size(); z++){
            if(minDistance > distances.get(z)){
                nearestClass = z;
                minDistance = distances.get(z);
            }
        }
        System.out.println("Distances: ");// далее выведем все расстояния до всех классов
        for(int a = 0; a < cores.size(); a++){
            System.out.println((a + 1) + ") distance to class " + cores.get(a).getTypeName() + ": " + distances.get(a));
        }
        // далее выведем к какому классу мы отнесем данный объект
        System.out.println("Nearest class: " + cores.get(nearestClass).getTypeName() + "\nDistance to this class: " + minDistance);
    }
}
