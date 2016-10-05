package model;

/**
 * Created by ccc on 03/10/16.
 */
public class SeqMatrixValue extends MatrixValue {
    private Value<?> from, to;
    private boolean inverted;

    public SeqMatrixValue(Value<?> from, Value<?> to, boolean inverted, int line) {
        super(line);
        this.from = from;
        this.to = to;
        this.inverted = inverted;
    }

    @Override
    public Matrix value() {
        Value<?> v1 = (from instanceof Variable ? ((Variable) from).value() : from);
        Value<?> v2 = (to instanceof Variable ? ((Variable) to).value() : to);

        if (v1 instanceof IntValue && v2 instanceof IntValue){
            //FIXME Operacao invalida
            if(inverted){
                int r = ((IntValue)v1).value();
                int c = ((IntValue)v2).value();
                return Matrix.iseq(r, c);
            } else{
                //FIXME Operacao invalida
                int r = ((IntValue)v1).value();
                int c = ((IntValue)v2).value();
                return Matrix.seq(r, c);
            }
        } else{
            throw new UnsupportedOperationException("Tipos inválidos");
        }
    }
}
