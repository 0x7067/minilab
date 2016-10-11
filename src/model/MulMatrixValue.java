package model;

public class MulMatrixValue extends MatrixValue{
    Value<?> matrix;
    Value<?> value;

    public MulMatrixValue(Value<?> m, Value<?> v, int line){
        super(line);
        this.matrix = m;
        this.value = v;
    }

    @Override
    public Matrix value(){
        Value<?> v1 = (matrix instanceof Variable ? (Variable) matrix.value() : matrix);
        Value<?> v2 = (value instanceof Variable ? (Variable) value.value() : value);

        if (v1 instanceof MatrixValue && v2 instanceof MatrixValue){
            Matrix x = ((MatrixValue) v1).value();
            Matrix y = ((MatrixValue) v2).value();
            return x.mul(y);
        }else if (v1 instanceof MatrixValue && v2 instanceof ConstIntValue){
            Matrix x = ((MatrixValue) v1).value();
            int y = ((ConstIntValue) v2).value();
            return x.mul(x);
        }else {
            // FIXME: Erro de tipos!
            return null;
        }
    }
}
