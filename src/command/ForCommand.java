package command;
import model.*;

public class ForCommand extends Command{

    private Variable var;
    private Value<?> val;
    private CommandBlock cb;

    public ForCommand(Variable var, Value<?> val, CommandBlock cb, int line){
        super(line);
        this.var = var;
        this.val = val;
        this.cb = cb;
    }
    @Override
    public void execute(){
        Value<?> var = (this.val instanceof Variable? ((Variable)this.val).value() : this.val);

        if(!(var instanceof MatrixValue)) {
            System.out.println("TokenType_INVALID");
            System.exit(1);
        }

        Matrix m = ((MatrixValue)var).value();
        for(int i = 0; i < m.rows(); i++) {
            for(int j = 0; j < m.cols(); j++) {
                ConstIntValue civ = new ConstIntValue(m.value(i, j), this.line());
                this.var.setValue(civ);
                this.cb.execute();
            }
        }
    }
}
