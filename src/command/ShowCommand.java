package command;

import model.*;

public class ShowCommand extends Command {
    private Value<?> value;
    public ShowCommand(Value<?> value, int line) {
        super(line);
        this.value = value;
    }

    public void execute() {
        System.out.println("Operação: Show");
        Value<?> v = (value instanceof Variable ? ((Variable) value).value() : value);

        if(v instanceof IntValue){
            int n = ((IntValue) v).value();
            System.out.println(n);
        } else if(v instanceof StringValue){
            String s = ((StringValue) v).value();
            System.out.println(s);
        } else if(v instanceof RefMatrixValue){
            Matrix m = ((RefMatrixValue) v).value();
            m.show();
        } else {
            // TT_INVALID_TOKEN
        }
    }
}