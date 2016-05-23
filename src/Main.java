/**
 * Created by ricardo on 21/05/16.
 */
public class Main {

    public static void main(String[] args){
        Controller controller = new Controller();

        Matrix m = new Matrix(10,10);
        Ship s = new Ship("S4", 4, 'H');
        Ship s2 = new Ship("T3", 3, 'V');
	
	try{
            controller.insertShip(s, m, 1, 1);
            controller.insertShip(s2, m, 2, 1);
	}catch(NavalBattleException e){
	     System.out.println(e.getMessage());
	}
        System.out.println(m.display());

        try {
            System.out.println(m.get(5,5));
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("ArrayIndexOutOfBoundsException");
        }

    }
}
