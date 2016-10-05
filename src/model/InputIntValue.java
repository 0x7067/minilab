package model;

import java.util.Scanner;

/**
 * Created by pedroguimaraes on 9/27/16.
 */
public class InputIntValue extends IntValue {
    private Value<?> value;
    private static Scanner lerInt = new Scanner(System.in);
    public InputIntValue(Value<?> stringV, int line) {
        super(line);
        this.value = stringV;
    }

    @Override
    public Integer value() {
        Value<?> v = (value instanceof Variable ? ((Variable) value).value() : value);

        if(v instanceof IntValue){
            int n = ((IntValue) v).value();
            System.out.print(n);
        } else if(v instanceof StringValue){
            String s = ((StringValue) v).value();
            System.out.print(s);
        } else if(v instanceof MatrixValue){
            Matrix m = ((MatrixValue) v).value();
            System.out.print(m.toString());
        } else {
        	throw new UnsupportedOperationException("Tipos invalidos");
        }
        
        return lerInt.nextInt();
    }
}
