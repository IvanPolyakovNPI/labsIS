import java.util.Vector;

public class Core {
    public Vector<Vector<Float>> core;
    public String typeName;

    public Core(Vector<Matrix> vectorOfMatrices, String typeName){// класс Core - это класс в котором хранятся ядра классов и их названия

        boolean equalityOfSizes = true;

        if(vectorOfMatrices.size() > 1){// для начала мы проверяем чтобы все матрицы были одной размерности
            int amountOfLines = vectorOfMatrices.get(0).getSize().get(0);
            int amountOfColumns = vectorOfMatrices.get(0).getSize().get(1);
            for(int i = 1; i < vectorOfMatrices.size(); i++) {
                if(amountOfLines != vectorOfMatrices.get(i).getSize().get(0) || amountOfColumns != vectorOfMatrices.get(i).getSize().get(1)){
                    equalityOfSizes = false;
                }

            }
        }
        // далее мы рассчитываем ядро из вектора матриц (vectorOfMatrices)
        if(equalityOfSizes){
            Vector<Vector<Float>> c = new Vector<>();
            for (int h = 0; h < vectorOfMatrices.get(0).getSize().get(0); h++){
                Vector<Float> vector = new Vector<>();
                for (int j = 0; j < vectorOfMatrices.get(0).getSize().get(1); j++){
                    float number = 0;
                    for (int z = 0; z < vectorOfMatrices.size(); z++){
                        number += Float.parseFloat(vectorOfMatrices.get(z).getMatrix().get(h).get(j).toString());
                    }
                    number /= vectorOfMatrices.size();
                    vector.add(number);
                }
                c.add(vector);
            }
            core = c;
        }else {
            System.out.println("Warning: matrices with different sizes!");
            core = null;
        }
        this.typeName = typeName;
    }

    public void printCore(){// метод для вывода информации о данном классе
        System.out.println("Class name: " + typeName);
        System.out.println("Core:");
        core.forEach(m ->{
            m.forEach(number ->{
                System.out.print(number + " ");
            });
            System.out.print("\n");
        });
    }
    // далее геттеры и сеттеры

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Vector<Vector<Float>> getCore() {
        return core;
    }

    public void setCore(Vector<Vector<Float>> core) {
        this.core = core;
    }


}
