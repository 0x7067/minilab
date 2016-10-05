package model;

/**
 * Created by ccc on 03/10/16.
 */
public class SeqMatrixValue extends MatrixValue {
    private Value<?> left, right;
    boolean normal;

    public SeqMatrixValue(Value<?> left, Value<?> right, boolean normal, int line) {
        super(line);
        this.left = left;
        this.right = right;
        this.normal = normal;
    }

    @Override
    public Matrix value() {
        Value<?> v1 = (left instanceof Variable ? (Variable) left.value() : left);
        Value<?> v2 = (right instanceof Variable ? (Variable) right.value() : right);

        if (v1 instanceof ConstIntValue && v2 instanceof ConstIntValue){
            if(normal){
                int r = ((ConstIntValue)v1).value();
                int c = ((ConstIntValue)v2).value();
                return Matrix.seq(r, c);
            } else{
                int r = ((ConstIntValue)v1).value();
                int c = ((ConstIntValue)v2).value();
                return Matrix.iseq(r, c);
            }
        } else{
            // TokenType_InVALID
        }
        return null;
    }
}
