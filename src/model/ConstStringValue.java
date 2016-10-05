package model;

/**
 * Created by pedroguimaraes on 9/27/16.
 */
public class ConstStringValue extends StringValue {
    private String value;

    public ConstStringValue(String value, int line) {
        super(line);
        this.value = value;
    }
    @Override
    public String value() {
        return value;
    }
}
