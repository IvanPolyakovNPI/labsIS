public class Knot {// класс Knot нужен для хранения информации о стоимости пути в текущем узле и хранения названия узла из которого мы пришли
    private String name;
    private String from;
    private float weight;

    public Knot(String name,String from,float weight){
        this.from = from;
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
