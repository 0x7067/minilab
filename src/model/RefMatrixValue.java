package model;

/**
 * Created by ccc on 29/09/16.
 */
public class RefMatrixValue extends MatrixValue {
    private Matrix m;

    public RefMatrixValue(Matrix m, int line) {
        super(line);
        this.m = m;
    }
    public Matrix value(){
        return m;
    }
}
