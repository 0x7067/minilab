package model;

/**
 * Created by ccc on 03/10/16.
 */
public class IdMatrixValue extends MatrixValue{
    Value<?> rows,cols;

    public IdMatrixValue(Value<?> r, Value<?> c, int line) {
        super(line);
        this.rows = r;
        this.cols = c;
    }

    @Override
    public Matrix value() {
        Value<?> v1 = (rows instanceof Variable ? (Variable) rows.value() : rows);
        Value<?> v2 = (cols instanceof Variable ? (Variable) cols.value() : cols);

        if (v1 instanceof ConstIntValue && v2 instanceof ConstIntValue){
            int r = ((ConstIntValue)v1).value();
            int c = ((ConstIntValue)v2).value();
            return Matrix.id(r, c);
        }else{
            // TT_INVALID
            return null;
        }
    }
}
