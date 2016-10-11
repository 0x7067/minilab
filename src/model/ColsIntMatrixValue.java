package model;

public class ColsIntMatrixValue extends IntMatrixValue{

    public ColsIntMatrixValue(Value<?> m, int line){
        super(m, line);
    }

    @Override
    public Integer value() {
        Value<?> v = (matrix instanceof Variable ? (Variable) matrix.value() : matrix);

        if(v instanceof MatrixValue){
            Matrix x = ((MatrixValue) v).value();

            return x.cols();
        }else {
            //FIXME: Erro de tipos!
            return null;
        }
    }
}
