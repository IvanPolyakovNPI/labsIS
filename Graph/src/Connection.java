public class Connection {// класс Connection нужен для хранения информации о стоимости пути до узла (тут он обозначен как goal)
    private String goal;
    private float weight;

    public Connection(String goalKnot, float weight){
        this.goal = goalKnot;
        this.weight = weight;
    }

    public float getWeight() {
        return weight;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}
