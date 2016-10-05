package model;

/**
 * Created by pedroguimaraes on 9/27/16.
 */
public class StringConcat extends StringValue {
    private String left, right;

    public StringConcat(String left, String right, int line) {
        super(line);
        this.left = left;
        this.right = right;
    }
    @Override
    public String value() {
            return left + right;
    }
}
