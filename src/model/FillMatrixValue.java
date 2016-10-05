package model;

/**
 * Created by ccc on 03/10/16.
 */
public class FillMatrixValue extends MatrixValue {
    private Value<?> rows,cols, valor;


    public FillMatrixValue(Value<?> rows, Value<?> cols, Value<?> valor, int line) {
        super(line);
        this.rows = rows;
        this.cols = cols;
        this.valor = valor;
    }

    @Override
    public Matrix value() {
        Value<?> v1 = (rows instanceof Variable ? (Variable) rows.value() : rows);
        Value<?> v2 = (cols instanceof Variable ? (Variable) cols.value() : cols);
        Value<?> v3 = (valor instanceof Variable ? (Variable) valor.value() : valor);

        if (v1 instanceof ConstIntValue && v2 instanceof ConstIntValue){
            int r = ((ConstIntValue)v1).value();
            int c = ((ConstIntValue)v2).value();
            int v = ((ConstIntValue)v3).value();

            return Matrix.fill(r, c, v);
        } else {
            // TOKEN_TYPE_INVALID
            return null;
        }
    }
}
