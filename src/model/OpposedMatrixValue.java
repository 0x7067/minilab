package model;

/**
 * Created by ccc on 03/10/16.
 */
public class OpposedMatrixValue extends MatrixValue{
    private Value<?> m;

    public OpposedMatrixValue(Value<?> m, int line) {
        super(line);

        this.m = m;
    }

    @Override
    public Matrix value() {
        Value<?> v = (m instanceof Variable ? ((Variable) m).value() : m);

        if (v instanceof MatrixValue){
            Matrix mat = ((MatrixValue) v).value();
            return mat.opposed();
        } else {
        	throw new UnsupportedOperationException("Tipos invalidos");
        }
    }
}

