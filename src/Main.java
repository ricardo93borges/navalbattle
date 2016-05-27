/**
 * Created by ricardo on 21/05/16.
 */
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

/**
*TODO motodo para distribuir embarcações aleatoreamente
**/
public class Main {

    public static Matrix init(){
	try{
                Matrix m = new Matrix(10,10);
		return m;
        }catch(NavalBattleException e){
                System.out.println(e.getMessage());
		return null;
        }
    }

    public static void main(String[] args){
	Scanner scanner = new Scanner(System.in);
        Controller controller = new Controller();
	Matrix m = init();
	
	ArrayList<Ship> ships = controller.setShips();

	String orientation="";
	String column="";
	int row=0;
	boolean invalid=true;

	for(Ship ship : ships){
		invalid = true;
		while(invalid){
			System.out.println("Embarcação "+ship.getName()+", "+ship.getSlots()+" slots.");
			System.out.println("Informe a orientação: \n h - Horizontal \n v - Vertical");
			orientation = scanner.nextLine();
			if(orientation.equals("h") || orientation.equals("v")){
				invalid = false;
				clearConsole();
			}else{
				clearConsole();
				System.out.println("Orientação informada inválida, informe h ou v. \n");
			}
		}

		invalid = true;
		while(invalid){
			Set columns = m.getColumnsLetters();
			System.out.println("Informe a coluna:");
			System.out.println("Colunas: "+columns.toString());
                	column = scanner.nextLine();
			
			if(columns.contains(column)){
				invalid = false;
				clearConsole();
			}else{
				clearConsole();
                                System.out.println("Coluna informada inválida.\n");
			}
		}

		invalid = true;
		while(invalid){
			System.out.println("Informe a linha:");
			for(int i=0; i < m.getRows(); i++){
				System.out.print(i+",");
			}
			System.out.println();
			String input = scanner.nextLine();
			row = stringToInt(input);
			if(row >= 0 && row <= m.getRows()-1){
				invalid = false;
				clearConsole();
			}else{
				clearConsole();
                                System.out.println("Linha informada inválida.\n");
			}
		}
	}
	m.display();
    }

    public static boolean insertShip(Controller c, Matrix m, Ship s, int row, String col){
	try{
		c.insertShip(s,m,row,col);
		return true;
	}catch(NavalBattleException e){
		return false;
	}
    }

    public static int stringToInt(String s){
	int n;
	try { 
        	n = Integer.parseInt(s); 
    	} catch(NumberFormatException e) { 
        	return -1; 
    	} catch(NullPointerException e) {
        	return -1;
    	}
	    return n;
    }

    public static void attack(Controller controller, Matrix m, int row, String column ){
	if(controller.attack(m, row, column)){
            System.out.println("Hit");
        }else{
	    System.out.println("Water!!!");
        }
    }

    public static void clearConsole(){
	if(System.getProperty("os.name").equals("Linux")){
		System.out.print("\033[H\033[2J");
	}else{
		for(int i=0; i<40; i++){
			System.out.println();
		}
	}
    }

}
