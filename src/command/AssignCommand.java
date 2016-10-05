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
        Value<?> v = (val instanceof Variable ? ((Variable) val).value() : val );

        if(v instanceof IntValue) {
            ConstIntValue c = new ConstIntValue( ((IntValue) v).value(), -1);
            var.setValue(c);
        } else if(v instanceof MatrixValue) {
            Matrix m = ((MatrixValue) v).value();
            RefMatrixValue r = new RefMatrixValue(m, -1);
            var.setValue(r);
        } else {
            throw new UnsupportedOperationException("Tipos invalidos");
        }
    }
}