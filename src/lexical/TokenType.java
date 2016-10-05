package lexical;

public enum TokenType {
    //special tokens
    INVALID_TOKEN,
    UNEXPECTED_EOF,
    END_OF_FILE,

    //Functions
    ASSIGN,
    INPUT,
    SHOW,
    IF,
    ELSE,
    FOR,
    WHILE,
    END,

    //matrix functions
    RAND,
    SEQ,
    ISEQ,
    NULL,
    FILL,
    ID,
    OPPOSED,
    TRANSPOSED,
    SUM,
    MUL,
    SIZE,
    ROWS,
    COLS,
    VALUE,

    //operators
    EQUAL,
    DIFF,
    LOWER,
    GREATER,
    LOWER_EQ,
    GREATER_EQ,
    AND,
    OR,
    PLUS,
    MINS,
    TIMES,
    DIV,
    MOD,

    // symbols
    OPEN_PAR,
    CLOSE_PAR,
    OPEN_BRA,
    CLOSE_BRA,
    DOT,
    COMMA,
    DOT_COMMA,

    //others
    VAR,          // variable
    STRING,       // string
    NUMBER,       // number
}
