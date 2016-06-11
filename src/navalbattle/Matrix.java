package navalbattle;
/**
 * Created by ricardo on 21/05/16.
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Matrix {

    private int rows;
    private int columns;
    private String[][] matrix;
    Map<String, Integer> mapColumns = new HashMap<String, Integer>();


    public Matrix(int rows, int columns){
        this.rows = rows;
        this.columns = columns;
        this.matrix = new String[rows][columns];
        this.initMapColumns();
    }

    private void initMapColumns() {
        mapColumns.put("a", 0);
        mapColumns.put("b", 1);
        mapColumns.put("c", 2);
        mapColumns.put("d", 3);
        mapColumns.put("e", 4);
        mapColumns.put("f", 5);
        mapColumns.put("g", 6);
        mapColumns.put("h", 7);
        mapColumns.put("i", 8);
        mapColumns.put("j", 9);
    }

    public int getColumn(String letter) {
        try {
            return this.mapColumns.get(letter);
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public Set getColumnsLetters() {
        return mapColumns.keySet();
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public boolean set(String element, int row, int column) {
        try {
            if (row > this.matrix.length) {
                return false;
            }
            if (column > this.matrix[row].length) {
                return false;
            }

            this.matrix[row][column] = element;

            return true;
        }catch(ArrayIndexOutOfBoundsException e){
            return false;
        }
    }

    public String get(int row, int column) throws ArrayIndexOutOfBoundsException {
        try {
            return this.matrix[row][column];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public String display() {
        String display = "|~|A|B|C|D|E|F|G|H|I|J| \n";

        for (int i = 0; i < matrix.length; i++) {
            display += "|" + i + "|";
            for (int j = 0; j < matrix[i].length; j++) {
                String element = (matrix[i][j] != null) ? matrix[i][j] : "~";
                display += element + "|";
            }
            display += "\n";
        }
        return display;
    }
}
