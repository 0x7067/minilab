package model;

/**
 * Created by pedroguimaraes on 10/2/16.
 */
public abstract class BoolValue extends Value<Boolean> {
    public BoolValue(int line) {
        super(line);
    }

    public abstract Boolean value();
}
