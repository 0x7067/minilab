package model;

/**
 * Created by ccc on 03/10/16.
 */
public class RandMatrixValue  extends MatrixValue{

    Value<?> rows,cols;

    public RandMatrixValue(Value<?> r, Value<?> c, int line) {
        super(line);
        this.rows = r;
        this.cols = c;
    }


    @Override
    public Matrix value() {
        Value<?> v1 = (rows instanceof Variable ? ((Variable) rows).value() : rows);
        Value<?> v2 = (cols instanceof Variable ? ((Variable) cols).value() : cols);

        if (v1 instanceof IntValue && v2 instanceof IntValue){
            int r = ((IntValue)v1).value();
            int c = ((IntValue)v2).value();
            return Matrix.rand(r, c);
        }else{
            return null;
        }
    }
}
