package command;

import model.*;

public class AssignCommand extends Command {
    private Variable var;
    private Value<?> val;

    public AssignCommand(Variable var, Value<?> val, int line) {
        super(line);
        this.var = var;
        this.val = val;
    }

    public void execute() {
        System.out.println("Operação: Assign");
        Value<?> v = (val instanceof Variable ? ((Variable) val).value():val );

        if(v instanceof IntValue) {
            ConstIntValue c = new ConstIntValue( ((IntValue) v).value(), val.line());
            var.setValue(c);
        } else if(v instanceof MatrixValue) {
            RefMatrixValue ref = new RefMatrixValue( ((MatrixValue) v).value(), val.line());
            var.setValue(ref);
        } else {
            // TT_INVALID_TOKEN
        }
    }
}
