package model;

public abstract class IntMatrixValue extends IntValue {

    protected Value<?> matrix;

    public IntMatrixValue(Value<?> m, int line) {
        super(line);
        this.matrix = m;
    }

    @Override
    public abstract Integer value();
}
