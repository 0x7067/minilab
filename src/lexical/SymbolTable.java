package lexical;

import java.util.Map;
import java.util.HashMap;


public class SymbolTable
{

    private Map<String, TokenType> st;

    public SymbolTable()
    {
        st = new HashMap<String, TokenType>();

        //functions
        add("=",     TokenType.ASSIGN);
        add("input", TokenType.INPUT);
        add("show",  TokenType.SHOW);
        add("if",    TokenType.IF);
        add("else",  TokenType.ELSE);
        add("for",   TokenType.FOR);
        add("while", TokenType.WHILE);
        add("end",   TokenType.END);


        //matrix functions
        add("rand",    TokenType.RAND);
        add("seq",     TokenType.SEQ);
        add("iseq",    TokenType.ISEQ);
        add("null",    TokenType.NULL);
        add("fill",    TokenType.FILL);
        add("id",      TokenType.ID);
        add("opposed", TokenType.OPPOSED);
        add("transposed", TokenType.TRANSPOSED);
        add("sum",     TokenType.SUM);
        add("mul",     TokenType.MUL);
        add("size",    TokenType.SIZE);
        add("rowls",   TokenType.ROWS);
        add("cols",    TokenType.COLS);
        add("value",    TokenType.VALUE);


        //operators
        add("==", TokenType.EQUAL);
        add("!=", TokenType.DIFF);
        add("<",  TokenType.LOWER);
        add(">",  TokenType.GREATER);
        add("<=", TokenType.LOWER_EQ);
        add(">=", TokenType.GREATER_EQ);
        add("&",  TokenType.AND);
        add("|",  TokenType.OR);
        add("+",  TokenType.PLUS);
        add("-",  TokenType.MINS);
        add("*",  TokenType.TIMES);
        add("/",  TokenType.DIV);
        add("%",  TokenType.MOD);

        //symbols
        add("(", TokenType.OPEN_PAR);
        add(")", TokenType.CLOSE_PAR);
        add("[", TokenType.OPEN_BRA);
        add("]", TokenType.CLOSE_BRA);
        add(".", TokenType.DOT);
        add(",", TokenType.COMMA);
        add(";", TokenType.DOT_COMMA);

    }

    //add no HashMap
    public void add(String token, TokenType type)
    {
        st.put(token, type);
    }

    public boolean contains(String token)
    {
        return st.containsKey(token);
    }

    public TokenType find(String token)
    {
        return this.contains(token) ?
            st.get(token) : TokenType.INVALID_TOKEN;
    }
}
