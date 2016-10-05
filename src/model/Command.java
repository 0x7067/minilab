package model;

/**
 * Created by pedroguimaraes on 9/20/16.
 */
public class Command {
    private int line;

    public Command(int line) {
        this.line = line;
    }

    public int line() {
        return line;
    }

    public void execute() {

    }
}
