package model;

/**
 * Created by pedroguimaraes on 10/2/16.
 */
public class DualBoolExpr extends BoolValue {
    private BoolOp op;
    private BoolValue left, right;

    public DualBoolExpr(BoolOp op, BoolValue left, BoolValue right, int line) {
        super(line);
        this.op = op;
        this.left = left;
        this.right = right;
    }
    @Override
    public Boolean value() {
        if(op == BoolOp.And) {
            return this.left.value() && this.right.value();
        } else if(op == BoolOp.Or) {
            return this.left.value() || this.right.value();
        } else {
            //FIXME Comparação invalida
        }
        return null;
    }
}
