package model;

/**
 * Created by pedroguimaraes on 9/27/16.
 */
public class DualIntExpr extends IntValue {
    private IntOp op;
    private Value<?> left, right;

    public DualIntExpr(IntOp op, Value<?> left, Value<?> right, int line) {
        super(line);
        this.op = op;
        this.left = left;
        this.right = right;
    }
    @Override
    public Integer value() {
        Value<?> v1 = (left instanceof Variable ? ((Variable) left).value() : left);
        Value<?> v2 = (right instanceof Variable ? ((Variable) right).value() : right);

        if(v1 instanceof IntValue && v2 instanceof IntValue) {
            int t1 = ((IntValue) v1).value();
            int t2 = ((IntValue) v2).value();

            switch (op) {
                case Add:
                    return t1 + t2;
                case Sub:
                    return t1 - t2;
                case Times:
                    return t1 * t2;
                case Div:
                    return t1 / t2;
                case Mod:
                    return t1 % t2;
                default:
                    return -1; //FIXME Talvez haja algo melhor pra retornar aqui
            }
        } else {
            // FIXME: operações inválidas entre tipos incompatíveis
        }
        return -1; //FIXME Talvez haja algo melhor pra retornar aqui
    }
}
