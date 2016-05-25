/**
 * Created by ricardo on 21/05/16.
 */
public class Main {

    public static void main(String[] args){
        Controller controller = new Controller();

        Matrix m = new Matrix(10,10);
        Ship s = new Ship("S4", 4, 'H');
        Ship s2 = new Ship("T3", 3, 'V');
	//Insert Ship
	try{
            controller.insertShip(s, m, 1, "a");
            controller.insertShip(s2, m, 2, "b");
	}catch(NavalBattleException e){
	     System.out.println(e.getMessage());
	}
	//Display Matrix
        System.out.println(m.display());

	//Attack and hit
	attack(controller, m, 1,"a");
	//Attack and miss
        attack(controller, m, 3,"c");

	//Display Matrix
        System.out.println(m.display());

	//Get element
        try {
            System.out.println(m.get(5,5));
	    System.out.println(m.get(1,"a"));
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("ArrayIndexOutOfBoundsException");
        }

    }

    public static void attack(Controller controller, Matrix m, int row, String column ){
	if(controller.attack(m, row, column)){
            System.out.println("Hit");
        }else{
	    System.out.println("Water!!!");
        }
    }
}
