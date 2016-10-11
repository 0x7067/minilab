package model;

public class RowsIntMatrixValue extends IntMatrixValue{

    public RowsIntMatrixValue(Value<?> m, int line){
        super(m, line);
    }

    @Override
    public Integer value() {
        Value<?> v = (matrix instanceof Variable ? (Variable) matrix.value() : matrix);

        if(v instanceof MatrixValue){
            Matrix x = ((MatrixValue) v).value();

            return x.rows();
        }else {
            //FIXME: Erro de tipos!
            return null;
        }
    }
}
