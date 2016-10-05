package model;

/**
 * Created by pedroguimaraes on 10/2/16.
 */
public class CompareBoolValue extends BoolValue {
    private RelOp op;
    private Value<?> left, right;

    public CompareBoolValue(RelOp op, Value<?> left, Value<?> right, int line) {
        super(line);
        this.op = op;
        this.left = left;
        this.right = right;
    }
    @Override
    public Boolean value() {
        Value<?> v1 = (left instanceof Variable ? ((Variable) left).value() : left);
        Value<?> v2 = (right instanceof Variable ? ((Variable) right).value() : right);
        if(v1 instanceof IntValue && v2 instanceof IntValue) {
            int t1 = ((IntValue) v1).value();
            int t2 = ((IntValue) v2).value();

            switch(op) {
                case Equal:
                    return t1 == t2;
                case NotEqual:
                    return t1 != t2;
                case LowerThan:
                    return t1 < t2;
                case LowerEqual:
                    return t1 <= t2;
                case GreaterThan:
                    return t1 > t2;
                case GreaterEqual:
                    return t1 >= t2;
                default:
                    return null; //FIXME Talvez haja algo melhor pra retornar aqui
            }
        } else {
            //FIXME COMPARAÇÃO INVÄLIDA
        }

        return null;
    }
}
