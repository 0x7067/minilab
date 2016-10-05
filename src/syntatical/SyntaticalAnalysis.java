package syntatical;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import command.AssignCommand;
import command.CommandBlock;
import command.ForCommand;
import command.IfCommand;
import command.ShowCommand;
import command.WhileCommand;
import lexical.Lexeme;
import lexical.LexicalAnalysis;
import lexical.TokenType;
import model.BoolOp;
import model.BoolValue;
import model.Command;
import model.CompareBoolValue;
import model.ConstIntValue;
import model.ConstStringValue;
import model.DualBoolExpr;
import model.DualIntExpr;
import model.FillMatrixValue;
import model.IdMatrixValue;
import model.InputIntValue;
import model.IntOp;
import model.MatrixValue;
import model.NullMatrixValue;
import model.OpposedMatrixValue;
import model.RandMatrixValue;
import model.RelOp;
import model.SeqMatrixValue;
import model.StringConcat;
import model.TransposedMatrixValue;
import model.Value;
import model.Variable;

public class SyntaticalAnalysis {
    private LexicalAnalysis la;
    private Lexeme current;

    private Map<String, Variable> variables = new HashMap<String, Variable>();

    public SyntaticalAnalysis(LexicalAnalysis la) throws IOException {
        this.la = la;
        this.current = la.nextToken();
    }

    public CommandBlock start() throws IOException{
        CommandBlock cb = procStatements();
        matchToken(TokenType.END_OF_FILE);
        return cb;
    }

    private void matchToken(TokenType type) throws IOException {
    	System.out.println("(" + current.type + ", \"" + current.token + "\") = " + type);
        if (current.type == type) {
            current = la.nextToken();
        } else {
            if (current.type == TokenType.END_OF_FILE ||
            current.type == TokenType.UNEXPECTED_EOF)
            abortEof();
            else {
                abortUnexpectedToken(current.token);
            }
        }
    }

    private void abortUnexpectedToken(String token) {
        // TODO Auto-generated method stub
        System.out.println(token);
    }

    private void abortEof() {
        // TODO Auto-generated method stub
        System.out.println("EOF");
    }

    //<statements> ::= <statement> { <statement> }
    private CommandBlock procStatements() throws IOException{
        CommandBlock cb = new CommandBlock();

        Command c = procStatement();
        cb.addCommand(c);

        while(  current.type == TokenType.SHOW ||
                current.type == TokenType.VAR ||
                current.type == TokenType.IF ||
                current.type == TokenType.WHILE ||
                current.type == TokenType.FOR){
            c = procStatement();
            cb.addCommand(c);
        }

        return cb;
    }

    //<statement>::= <show> | <assign> | <if> | <while> | <for>
    private Command procStatement() throws IOException{
        if(current.type == TokenType.SHOW){
            return procShow();
        } else if(current.type == TokenType.VAR){
            return procAssign();
        } else if(current.type == TokenType.IF){
            return procIf();
        } else if(current.type == TokenType.WHILE){
            return procWhile();
        } else if(current.type == TokenType.FOR){
             return procFor();
        } else {
            abortUnexpectedToken(current.token);
        }
        return null;
    }

    // <show> ::= show '(' <text> ')' ';'
    private ShowCommand procShow() throws IOException {
        matchToken(TokenType.SHOW);
        matchToken(TokenType.OPEN_PAR);
        Value<?> v = procText();
        System.out.println("v = " + v);
        matchToken(TokenType.CLOSE_PAR);
        matchToken(TokenType.DOT_COMMA);

        ShowCommand c = new ShowCommand(v, la.line());
        return c;
    }

    //<assign> ::= <var> '=' <expr> ';'
    private AssignCommand procAssign() throws IOException {
        Variable var = procVar();
        matchToken(TokenType.ASSIGN);
        Value<?> val = procExpr();
        matchToken(TokenType.DOT_COMMA);
        AssignCommand c = new AssignCommand(var, val, la.line());
        return c;
    }

    // <if>::= if <boolexpr> <statements> [ else <statements> ] end
    private IfCommand procIf() throws IOException {
        matchToken(TokenType.IF);
        BoolValue bexpr = procBoolExpr();
        CommandBlock cb_if = procStatements();
        IfCommand ifCmd;

        if(current.type == TokenType.ELSE) {
            matchToken(TokenType.ELSE);
            CommandBlock cb_else = procStatements();
            ifCmd = new IfCommand(bexpr, cb_if, cb_else, la.line());
            matchToken(TokenType.END);
        } else {
            ifCmd = new IfCommand(bexpr, cb_if, null, la.line());
            matchToken(TokenType.END);
        }

        return ifCmd;
    }

    // <while> ::= while <boolexpr> <statements> end
    private WhileCommand procWhile() throws IOException {

        matchToken(TokenType.WHILE);
        BoolValue bexpr = procBoolExpr();
        CommandBlock cb_while = procStatements();
        WhileCommand whileCmd = new WhileCommand(bexpr, cb_while, la.line());
        matchToken(TokenType.END);

        return whileCmd;
    }

    // <for> ::= for <var> '=' <value> <statements> end
    private ForCommand procFor() throws IOException {
        matchToken(TokenType.FOR);
        Variable var = procVar();
        matchToken(TokenType.ASSIGN);
        Value<?> v = procValue();
        CommandBlock cb = procStatements();
        matchToken(TokenType.END);
        ForCommand fm = new ForCommand(var, v, cb, la.line());
        return fm;
    }

    //<text> ::= { <string> | <expr> }
    private Value<?> procText() throws IOException {
    	Value<?> ret = null;
    	
    	while (current.type == TokenType.STRING ||
    		current.type == TokenType.NUMBER ||
            current.type == TokenType.INPUT ||
            current.type == TokenType.VAR ||
            current.type == TokenType.OPEN_BRA ||
            current.type == TokenType.OPEN_PAR) {
    	  
    		Value<?> v = (current.type == TokenType.STRING ?
    						procString() : procExpr());
    		if (ret == null)
    			ret = v;
    		else {
    			ret = new StringConcat(ret, v, la.line());
    		}
    		
        }
        
        return ret;
    }

    //<var>
    private Variable procVar() throws IOException {
        String varName = current.token;
        matchToken(TokenType.VAR);
        Variable v = variables.get(varName);
        if (v == null) {
            v = new Variable(varName);
            variables.put(varName, v);
        }
        return v;
    }

    //<expr> ::= <term> [ ('+' | '-') <term> ]
    private Value<?> procExpr() throws IOException {
        Value<?> left = procTerm();
        if (current.type == TokenType.PLUS || current.type == TokenType.MINS) {
            IntOp op = null;
            if(current.type == TokenType.PLUS) {
                matchToken(TokenType.PLUS);
                op = IntOp.Add;
            } else if (current.type == TokenType.MINS) {
                matchToken(TokenType.MINS);
                op = IntOp.Sub;
            }

            Value<?> right = procTerm();
            DualIntExpr expr = new DualIntExpr(op, left, right, la.line());
            return expr;
        } else {
            return left;
        }
    }
    //<boolexpr> ::= <expr> <boolop> <expr> { ('&' | '|') <boolexpr> }
    private BoolValue procBoolExpr() throws IOException {
        Value<?> left = procExpr();
        RelOp relOp = procBoolOp();
        Value<?> right = procExpr();
        CompareBoolValue cbvLeft, cbvRight;
        BoolOp boolOP = null;
        DualBoolExpr dbe = null;
        cbvLeft = new CompareBoolValue(relOp, left, right, la.line());
        while(current.type == TokenType.AND || current.type == TokenType.OR) {
            if(current.type == TokenType.AND) {
                matchToken(TokenType.AND);
                boolOP = BoolOp.And;
                left = procTerm();
                relOp = procBoolOp();
                right = procExpr();
                cbvRight = new CompareBoolValue(relOp, left, right, la.line());
                dbe = new DualBoolExpr(boolOP, cbvLeft, cbvRight, la.line());
            }
            if(current.type == TokenType.OR) {
                matchToken(TokenType.OR);
                boolOP = BoolOp.Or;
                left = procTerm();
                relOp = procBoolOp();
                right = procExpr();
                cbvRight = new CompareBoolValue(relOp, left, right, la.line());
                dbe = new DualBoolExpr(boolOP, cbvLeft, cbvRight, la.line());
            }
        }
        if(dbe != null) {
            return dbe;
        }
        else {
            return cbvLeft;
        }
    }

    // <term> ::= <factor> [ ('*' | '/' | '%') <factor> ]
    private Value<?> procTerm() throws IOException {
        IntOp op = null;
        Value<?> left = procFactor();
        DualIntExpr expr = null;
        while(current.type == TokenType.TIMES || current.type == TokenType.DIV || current.type == TokenType.MOD) {
            if (current.type == TokenType.TIMES) {
                matchToken(TokenType.TIMES);
                op = IntOp.Times;
                Value<?> right = procFactor();
                expr = new DualIntExpr(op, left, right, la.line());
                left = expr;
            } else if (current.type == TokenType.DIV) {
                matchToken(TokenType.DIV);
                op = IntOp.Div;
                Value<?> right = procFactor();
                expr = new DualIntExpr(op, left, right, la.line());
                left = expr;
            } else {
                matchToken(TokenType.MOD);
                op = IntOp.Mod;
                Value<?> right = procFactor();
                expr = new DualIntExpr(op, left, right, la.line());
                left = expr;
            }
        }
        return left;
    }

    // <factor> ::= <number> | <input> | <value> | '(' <expr> ')'
    private Value<?> procFactor() throws IOException {
            if (current.type == TokenType.NUMBER) {
                return procNumber();
            } else if (current.type == TokenType.OPEN_BRA || current.type == TokenType.VAR) {
                return procValue();
            } else if (current.type == TokenType.INPUT) {
                return procInput();
            } else if (current.type == TokenType.OPEN_PAR) {
                matchToken(TokenType.OPEN_PAR);
                Value<?> val = procExpr();
                matchToken(TokenType.CLOSE_PAR);
                return val;
            } else {
                abortUnexpectedToken(current.token);
            }
        return null;
    }

    // <value>::= (<var> | <gen>)
    //			                { '.' (<opposed> | <transposed> | <sum> | <mul>) }
    // 					        [ '.' (<size> | <rows> | <cols> | <val>) ]
    private Value<?> procValue() throws IOException {
        Value<?> value = null;
        
        if(current.type == TokenType.VAR) {
            value = procVar();
        } else if (current.type == TokenType.OPEN_BRA){
            value = procGen();
        } else {
        	// FIXME: Erro!
        }
        
        while(current.type == TokenType.DOT) {
            matchToken(TokenType.DOT);
            if(current.type == TokenType.OPPOSED) {
                value = procOpposed(value);
            } else if(current.type == TokenType.TRANSPOSED) {
                value = procTransposed(value);
            } else if(current.type == TokenType.SUM) {
                procSum();
            } else if(current.type == TokenType.MUL) {
                procMul();
            } else if(current.type == TokenType.SIZE) {
                procSize();
                break;
            } else if (current.type == TokenType.ROWS) {
                procRows();
                break;
            } else if (current.type == TokenType.COLS) {
                procCols();
                break;
            } else {
                // ?
                //abortUnexpectedToken(current.type);
            }
        }
        return value; //FIXME Consertar
    }

    private MatrixValue procGen() throws IOException {
        matchToken(TokenType.OPEN_BRA);
        matchToken(TokenType.CLOSE_BRA);
        matchToken(TokenType.DOT);

        if(current.type == TokenType.NULL) {
        	System.out.println("Got here");
            NullMatrixValue nmv = procNull();
            return nmv;
        } else if(current.type == TokenType.RAND) {
            RandMatrixValue rmv = procRand();
            return rmv;
        } else if(current.type == TokenType.FILL) {
            FillMatrixValue fmv = procFill();
            return fmv;
        } else if(current.type == TokenType.ID) {
            IdMatrixValue imv = procId();
            return imv;
        } else if(current.type == TokenType.SEQ) {
            SeqMatrixValue smv = procSeq();
            return smv;
        }
        return null;
    }

    private SeqMatrixValue procSeq() throws IOException {
        matchToken(TokenType.SEQ);
        matchToken(TokenType.OPEN_PAR);
        Value<?> expr1 = procExpr();
        matchToken(TokenType.COMMA);
        Value<?> expr2 = procExpr();
        matchToken(TokenType.CLOSE_PAR);
        SeqMatrixValue smv = new SeqMatrixValue(expr1, expr2, true, la.line());
        return smv;
    }

    private IdMatrixValue procId() throws IOException {
        matchToken(TokenType.ID);
        matchToken(TokenType.OPEN_PAR);
        Value<?> expr1 = procExpr();
        matchToken(TokenType.COMMA);
        Value<?> expr2 = procExpr();
        matchToken(TokenType.CLOSE_PAR);
        IdMatrixValue imv = new IdMatrixValue(expr1, expr2, la.line());
        return imv;
    }


    private FillMatrixValue procFill() throws IOException {
        matchToken(TokenType.FILL);
        matchToken(TokenType.OPEN_PAR);
        Value<?> expr1 = procExpr();
        matchToken(TokenType.COMMA);
        Value<?> expr2 = procExpr();
        matchToken(TokenType.COMMA);
        Value<?> expr3 = procExpr();
        matchToken(TokenType.CLOSE_PAR);
        FillMatrixValue fmv = new FillMatrixValue(expr1, expr2, expr3, la.line());
        return fmv;
    }


    private RandMatrixValue procRand() throws  IOException {
        matchToken(TokenType.RAND);
        matchToken(TokenType.OPEN_PAR);
        Value<?> expr1 = procExpr();
        matchToken(TokenType.COMMA);
        Value<?> expr2 = procExpr();
        matchToken(TokenType.CLOSE_PAR);
        RandMatrixValue rmv = new RandMatrixValue(expr1, expr2, la.line());
        return rmv;
    }
    private NullMatrixValue procNull() throws IOException {
        matchToken(TokenType.NULL);
        matchToken(TokenType.OPEN_PAR);
        Value<?> left = procExpr();
        matchToken(TokenType.COMMA);
        Value<?> right = procExpr();
        matchToken(TokenType.CLOSE_PAR);
        NullMatrixValue nmv = new NullMatrixValue(left, right, la.line());
        return nmv;
    }
    
    private ConstStringValue procString() throws IOException {
        String str = current.token;
        matchToken(TokenType.STRING);
        return new ConstStringValue(str, la.line());
    }

    // <boolop> ::= '==' | '!=' | '<' | '>' | '<=' | '>='
    private RelOp procBoolOp() throws IOException {
        RelOp op = null;
        if(current.type == TokenType.EQUAL){
            matchToken(TokenType.EQUAL);
            op = RelOp.Equal;
        } else if(current.type == TokenType.DIFF){
            matchToken(TokenType.DIFF);
            op = RelOp.NotEqual;
        } else if(current.type == TokenType.LOWER){
            matchToken(TokenType.LOWER);
            op = RelOp.LowerThan;
        } else if(current.type == TokenType.GREATER){
            matchToken(TokenType.GREATER);
            op = RelOp.GreaterThan;
        } else if(current.type == TokenType.LOWER_EQ){
            matchToken(TokenType.LOWER_EQ);
            op = RelOp.LowerEqual;
        } else if(current.type == TokenType.GREATER_EQ){
            matchToken(TokenType.GREATER_EQ);
            op = RelOp.LowerEqual;
        }
        return op;
    }
    // <opposed> ::= opposed '(' ')'
    private MatrixValue procOpposed(Value<?> matrix) throws IOException {
        matchToken(TokenType.OPPOSED);
        matchToken(TokenType.OPEN_PAR);
        matchToken(TokenType.CLOSE_PAR);
        
        OpposedMatrixValue omv = new OpposedMatrixValue(matrix, la.line());
        return omv;
    }
    
    // <transposed> ::= transposed '(' ')'
    private TransposedMatrixValue procTransposed(Value<?> m) throws IOException {
        matchToken(TokenType.TRANSPOSED);
        matchToken(TokenType.OPEN_PAR);
        matchToken(TokenType.CLOSE_PAR);
        TransposedMatrixValue tmv = new TransposedMatrixValue(m, la.line());
        return tmv;
    }
    // <sum> ::= sum '(' <expr> ')'
    private void procSum() throws IOException {
        matchToken(TokenType.SUM);
        matchToken(TokenType.OPEN_PAR);
        procExpr();
        matchToken(TokenType.CLOSE_BRA);
    }
    // <mul> ::= mul '(' <expr> ')'
    private void procMul() throws IOException {
        matchToken(TokenType.MUL);
        matchToken(TokenType.OPEN_PAR);
        procExpr();
        matchToken(TokenType.CLOSE_BRA);
    }
    // <size> ::= size '(' ')'
    private void procSize() throws IOException {
        matchToken(TokenType.SIZE);
        matchToken(TokenType.OPEN_PAR);
        matchToken(TokenType.CLOSE_PAR);
    }
    // <rows> ::= rows '(' ')'
    private void procRows() throws IOException {
        matchToken(TokenType.ROWS);
        matchToken(TokenType.OPEN_PAR);
        matchToken(TokenType.CLOSE_PAR);
    }
    // <cols> ::= cols '(' ')'
    private void procCols() throws IOException {
        matchToken(TokenType.COLS);
        matchToken(TokenType.OPEN_PAR);
        matchToken(TokenType.CLOSE_PAR);
    }
    // <number>
    private ConstIntValue procNumber() throws IOException {
        String number = current.token;
        matchToken(TokenType.NUMBER);
        
        return new ConstIntValue(Integer.parseInt(number), la.line());
    }
    
    // <input> ::= input '(' <text> ')'
    private InputIntValue procInput() throws IOException {
        matchToken(TokenType.INPUT);
        matchToken(TokenType.OPEN_PAR);
        Value<?> sv = procText();
        matchToken(TokenType.CLOSE_PAR);
        InputIntValue iiv = new InputIntValue(sv, la.line());
        return iiv;
    }

    // FIXME: procs de operação em Matriz
}
