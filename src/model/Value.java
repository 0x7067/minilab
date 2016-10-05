package model;

public abstract class Value<T> {
    private int line;

    public Value(int l) {
        line = l;
    }

    public int line() {
        return line;
    }
    public abstract T value();
}
