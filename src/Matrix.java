/**
 * Created by ricardo on 21/05/16.
 */
public class Matrix {

    private int rows;
    private int columns;
    private String[][] matrix;

    public Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.matrix = new String[rows][columns];
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public boolean set(String element, int row, int column){
        if(row > this.matrix.length){
            return false;
        }
        if(column > this.matrix[row].length){
            return false;
        }

        this.matrix[row][column] = element;

        return true;
    }

    public String get(int row, int column) throws ArrayIndexOutOfBoundsException{
        try {
            return this.matrix[row][column];
        }catch (ArrayIndexOutOfBoundsException e){
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public String display(){
        String display = "|~|A|B|C|D|E|J|H|I|J|L| \n";

        for(int i=0; i < matrix.length; i++){
            display += "|"+i+"|";
            for(int j=0; j < matrix[i].length; j++){
                String element = (matrix[i][j] != null) ? matrix[i][j] : "~";
                display += element+"|";
            }
            display += "\n";
        }
        return display;
    }
}
