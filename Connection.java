public class Connection {// класс Connection нужен для хранения информации о стоимости пути до узла (тут он обозначен как goal)
    private String to;
    private String from;
    private float weight;

    public Connection(String toKnot, String fromKnot, float weight){
        to = toKnot;
        from = fromKnot;
        this.weight = weight;
    }

    public float getWeight() {
        return weight;
    }

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}
