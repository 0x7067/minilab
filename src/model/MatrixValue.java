package model;

public abstract class MatrixValue extends Value<Matrix> {
    public MatrixValue(int line) {
        super(line);
    }

    public abstract Matrix value();
}
