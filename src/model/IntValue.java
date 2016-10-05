package model;

/**
 * Created by pedroguimaraes on 9/27/16.
 */
public abstract class IntValue extends Value<Integer> {
    public IntValue(int line) {
        super(line);
    }

    public abstract Integer value();
}
