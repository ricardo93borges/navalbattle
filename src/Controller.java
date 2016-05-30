/**
 * Created by ricardo on 21/05/16.
 */
import java.util.ArrayList;
import java.util.Random;

public class Controller{

    public void insertShip(Ship ship, Matrix matrix, int row, String col) throws NavalBattleException{
        //validate and repositioning if necessary
	int column = matrix.getColumn(col);
	System.out.println("Column:"+column);
	//System.out.println(matrix.get(row,column));
        if(ship.getOrientation() == 'H'){
            int h = (row+ship.getSlots())-1;
            if(h > matrix.getRows()){
                row = h-matrix.getRows();
            }
	    int v = (column+ship.getSlots());
	    if(v > matrix.getColumns()){
                column = v-matrix.getColumns();
            }
        }else if(ship.getOrientation() == 'V'){
            int v = (column+ship.getSlots())-1;
            if(v > matrix.getColumns()){
                column = v-matrix.getColumns();
            }
        }

	//Check if it already has an element
	int slots = ship.getSlots();
        while (slots > 0){
	    if(matrix.get(row,column) != null){
		throw new NavalBattleException("Posição inválida, slots ocupados.");
            }
	    slots--;
	}

	//Insert
        slots = ship.getSlots();
        while (slots > 0){
		System.out.println(column+","+row);
            	matrix.set(ship.getSlug(), row, column);
            slots--;
            if(ship.getOrientation() == 'H'){
                column++;
            }else{
                row++;
            }
        }
    }

    public void randomInsertShips(Matrix m, ArrayList<Ship> ships){
	String[] columns = {"a","b","c","d","e","f","g","h","i","j"};
	for(Ship ship : ships){
		try{
			
			Random r = new Random();
			int n = r.nextInt(9);
			String l = columns[n];
			System.out.println(l+","+n);
			this.insertShip(ship, m, n, l);
			
		}catch(NavalBattleException e){
			System.out.println(e.getMessage());
		}	

	}
	m.display();

    }

    public boolean attack(Matrix matrix, int row, String col){
        try{
	    int column = matrix.getColumn(col);
	    if(matrix.get(row, column) == null){
		return false;
	    }
            matrix.set("X", row, column);
            return true;
        }catch (ArrayIndexOutOfBoundsException e){
            return false;
        }
    }

    public ArrayList<Ship> setShips(){
	ArrayList<Ship> ships = new ArrayList<Ship>();
	ships.add(new Ship("A", 5, 'H'));
        ships.add(new Ship("B", 4, 'H'));
        ships.add(new Ship("C", 3, 'H'));
       	ships.add(new Ship("D", 2, 'H'));
	return ships;
    }

}
