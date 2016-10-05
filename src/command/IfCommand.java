package command;

import model.BoolValue;
import model.Command;

/**
 * Created by pedroguimaraes on 10/2/16.
 */
public class IfCommand extends Command {
    private BoolValue expr;
    private Command _then_;
    private Command _else_;

    public IfCommand(BoolValue expr, Command _then_, Command _else_, int line) {
        super(line);
        this.expr = expr;
        this._then_ = _then_;
        this._else_ = _else_;
    }

    public void execute() {
        System.out.println("Operação execute do IfCommand");
        if(this.expr.value()) {
            System.out.println("Operação if");
            this._then_.execute();
        }
        else if(this._else_ != null) {
            System.out.println("Operação else");
            this._else_.execute();
        }
    }
}
