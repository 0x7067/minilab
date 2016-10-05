package command;
import model.BoolValue;
import model.Command;

/**
 * Created by pedroguimaraes on 10/3/16.
 */
public class WhileCommand extends Command {
    private BoolValue expr;
    private Command cmd;

    public WhileCommand(BoolValue expr, Command cmd, int line) {
        super(line);
        this.expr = expr;
        this.cmd = cmd;
    }

    public void execute() {
        while(expr.value())
            this.cmd.execute();
    }
}
