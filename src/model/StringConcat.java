package model;

/**
 * Created by pedroguimaraes on 9/27/16.
 */
public class StringConcat extends StringValue {

	private Value<?> left, right;

    public StringConcat(Value<?> left, Value<?> right, int line) {
        super(line);

        this.left = left;
        this.right = right;
    }

    @Override
    public String value() {
        Value<?> v1 = (left instanceof Variable ? ((Variable) left).value() : left);
        Value<?> v2 = (right instanceof Variable ? ((Variable) right).value() : right);

        String s1;
        if(v1 instanceof IntValue){
            int n = ((IntValue) v1).value();
            s1 = Integer.toString(n);
        } else if (v1 instanceof StringValue){
            s1 = ((StringValue) v1).value();
        } else if(v1 instanceof MatrixValue) {
            Matrix m = ((MatrixValue) v1).value();
            s1 = m.toString();
        } else {
        	throw new UnsupportedOperationException("Tipos invalidos");
        }
        
        String s2;
        if(v2 instanceof IntValue){
            int n = ((IntValue) v2).value();
            s2 = Integer.toString(n);
        } else if (v2 instanceof StringValue){
            s2 = ((StringValue) v2).value();
        } else if(v2 instanceof MatrixValue) {
            Matrix m = ((MatrixValue) v2).value();
            s2 = m.toString();
        } else {
        	throw new UnsupportedOperationException("Tipos invalidos");
        }
        
        return s1+s2;
    }
}
