package model;
import java.util.Random;

public class Matrix {
    private int rows, cols;
    public int[][] matrix;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.matrix = new int[rows][cols];
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public void setValue(int r, int c, int v){
        matrix[r][c] = v;
    }

    public void show(){
    	System.out.println(this.toString());
    }

    public String toString() {
    	StringBuffer ret = new StringBuffer();
    	
        for (int i = 0; i < rows; i++) {
        	ret.append("[ ");
            for (int j = 0; j < cols; j++) {
            	ret.append(matrix[i][j]).append(" ");
            }
        	ret.append("]\n");
        }

    	return ret.toString();
    }
    public int rows() {
        return rows;
    }

    public int cols() {
        return cols;
    }

    public int value(int r, int c) {
        return matrix[r][c];
    }

    public Matrix opposed() {
        Matrix m = new Matrix(rows, cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                m.setValue(i, j, matrix[i][j]*(-1));
            }
        }

        return m;
    }

    public Matrix transposed(){
        Matrix m = new Matrix(cols, rows);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                m.setValue(j, i, matrix[i][j]);
            }
        }

        return m;
    }

    public int size(){
        return rows*cols;
    }

    public Matrix sum(Matrix x, Matrix y){
        if(x.rows == y.rows && x.cols == y.cols){
            Matrix m = new Matrix(x.rows,y.cols);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    m.matrix[i][j] = x.matrix[i][j] + y.matrix[i][j];
                }
            }
            return m;
        }
        return null;
    }

    public Matrix mul(Matrix m){
        Matrix aux = new Matrix(rows, cols);

        if(m.rows == cols && m.cols == rows){
            for(int i = 0; i < cols; i++){
                for(int j = 0; j < rows; j++){
                    aux.matrix[i][j] = matrix[i][j] + m.matrix[i][j];
                }
            }
        } else {
            System.out.println("Mult de Matrizes: Dimensoes erradas");
        }

        return aux;
    }

    public void mul(int n){
        for(int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                matrix[i][j] = (-n) * matrix[i][j];
            }
        }
    }

    public static Matrix m_null(int r, int c){
        Matrix matrix2 = new Matrix(r, c);

        for(int i = 0; i < r; i++){
            for(int j = 0; j < c; j++){
                matrix2.matrix[i][j] = 0;
            }
        }

        return matrix2;
    }

    public static Matrix fill(int r, int c, int v){
        Matrix matrix2 = new Matrix(r,c);

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                matrix2.matrix[i][j] = v;
            }
        }
        return matrix2;
    }

    public static Matrix rand(int r, int c){
        Matrix matrix2 = new Matrix(r, c);
        Random gerador = new Random();

        for(int i = 0; i < r; i++){
            for(int j = 0; j < c; j++){
                matrix2.matrix[i][j] = gerador.nextInt(101);
            }
        }

        return matrix2;
    }

    public static Matrix id(int r, int c){
        Matrix matrix2 = new Matrix(r,c);
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                matrix2.matrix[i][j] = (i==j) ? 1 : 0;
            }
        }
        return matrix2;
    }

    public static Matrix seq(int x, int y){
        if(y > x){
            Matrix m = new Matrix(0,y-x+1);
            int c = x;
            for (int i = 0; i < y-x; i++) {
                m.matrix[0][i] = c++;
            }
            return m;
        }
        return null;
    }

    public static Matrix iseq(int x, int y){
        if(y < x){
            Matrix m = new Matrix(0,x-y+1);
            int c = x;
            for (int i = x; i > x-y+1; i--) {
                m.matrix[0][i-1] = c--;
            }
            return m;
        }
        return null;
    }
}