package model;

/**
 * Created by pedroguimaraes on 9/27/16.
 */
public class ConstIntValue extends IntValue {

    private int value;

    public ConstIntValue(int value, int line) {
        super(line);

        this.value = value;
    }

    public Integer value() {
        return value;
    }
}
