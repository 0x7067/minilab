package model;

public class SumMatrixValue extends MatrixValue{
    Value<?> matrix1;
    Value<?> matrix2;

    public SumMatrixValue(Value<?> m1, Value<?> m2, int line){
        super(line);
        this.matrix1 = m1;
        this.matrix2 = m2;
    }

    @Override
    public Matrix value(){
        Value<?> v1 = (matrix1 instanceof Variable ? (Variable) matrix1.value() : matrix1);
        Value<?> v2 = (matrix2 instanceof Variable ? (Variable) matrix2.value() : matrix2);

        if (v1 instanceof MatrixValue && v2 instanceof MatrixValue){
            Matrix x = ((MatrixValue) v1).value();
            Matrix y = ((MatrixValue) v2).value();
            return x.sum(x, y);
        } else {
            // FIXME: Erro de tipos!
            return null;
        }
    }
}
