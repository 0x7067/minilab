package model;

/**
 * Created by pedroguimaraes on 9/27/16.
 */
public abstract class StringValue extends Value<String> {
    public StringValue(int line) {
        super(line);
    }

    public abstract String value();
}
