package model;

/**
 * Created by ccc on 03/10/16.
 */
public class NullMatrixValue extends MatrixValue {
    private Value<?> rows, cols;

    public NullMatrixValue(Value<?> rows, Value<?> cols, int line) {
        super(line);
        this.rows = rows;
        this.cols = cols;
    }

    @Override
    public Matrix value() {
        Value<?> v1 = (rows instanceof Variable ? (Variable) rows.value() : rows);
        Value<?> v2 = (cols instanceof Variable ? (Variable) cols.value() : cols);

        if(v1 instanceof ConstIntValue && v2 instanceof IntValue) {
            int r = ((ConstIntValue)v1).value();
            int c = ((ConstIntValue)v2).value();
            return Matrix.m_null(r, c);
        } else {
            return null;
        }
    }

}
