
package model;

public class Variable extends Value {

    private String name;
    private Value<?> value;

    public Variable(String name) {
        super(-1);
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setValue(Value<?> v){
        this.value = v;
    }

    public Value<?> value(){
        return value;
    }
}
