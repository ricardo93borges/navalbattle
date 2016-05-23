/**
 * Created by ricardo on 21/05/16.
 */
public class Controller{

    public void insertShip(Ship ship, Matrix matrix, int row, int column) throws NavalBattleException{
        //validate and repositioning if necessary
        if(ship.getOrientation() == 'H'){
            int h = (row+ship.getSlots())-1;
            if(h > matrix.getRows()){
                row = h-matrix.getRows();
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
		System.out.println(matrix.get(row,column));
		throw new NavalBattleException("Posição inválida, slots ocupados.");
            }
	    slots--;
	}

        slots = ship.getSlots();
        while (slots > 0){
            matrix.set(ship.getSlug(), row, column);
            slots--;
            if(ship.getOrientation() == 'H'){
                column++;
            }else{
                row++;
            }
        }
    }

    public boolean attack(Matrix matrix, int row, int column){
        try{
            String target = matrix.get(row, column);
            matrix.set("X", row, column);
            return true;
        }catch (ArrayIndexOutOfBoundsException e){
            return false;
        }
    }

}
