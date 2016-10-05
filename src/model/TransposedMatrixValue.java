package model;

/**
 * Created by ccc on 03/10/16.
 */
public class TransposedMatrixValue extends MatrixValue{
    private Value<?> m;

    public TransposedMatrixValue(Value<?> m, int line) {
        super(line);
        this.m = m;
    }

    @Override
    public Matrix value() {
        Value<?> v = (m instanceof Variable ? (Variable) m.value() : m);

        if (v instanceof MatrixValue){
            Matrix mat = ((MatrixValue) v).value();
            return mat.transposed();
        } else {
            //TT_INVALID
            return null;
        }
    }
}

