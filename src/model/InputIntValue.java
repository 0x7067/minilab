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
        Value<?> v = (value instanceof Variable ? (Variable) value.value() : value);
        if(v instanceof  StringValue) {
            String s = ((StringValue) v).value();
            System.out.println(s);
        } else {
            System.out.println("Tipo do input inválido");//FIXME Abortar tipo inválido
        }
        return lerInt.nextInt();
    }
}
