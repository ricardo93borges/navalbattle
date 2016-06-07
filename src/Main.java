/**
 * Created by ricardo on 21/05/16.
 */

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

/**
 * distribuir verticalmente
 * redistribuir em exceptions
 **/
public class Main {

    public static Matrix init() {
        Matrix m = new Matrix(10, 10);
        return m;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Controller controller = new Controller();
        Matrix playerMatrix = init();
        Matrix computerMatrix = init();

        ArrayList<Ship> ships = controller.setShips();
        
        controller.randomInsertShips(playerMatrix, ships);
        controller.randomInsertShips(computerMatrix, ships);
        
        System.out.println(computerMatrix.display());
        System.out.println(playerMatrix.display());
        
        boolean run = true;
        int opt;
        String column = "";
        int row = 0;
        
        int playerPoints = 0;
        int computerPoints = 0;
        
        while(run){
            System.out.println("Placar ==> Você: "+playerPoints+"/14 | Adversário: "+computerPoints+"/14");
            System.out.println("Digite 1 para atacar ou 0 para sair.");
            opt = scanner.nextInt();
            
            if(opt==0){
                run = false;
            }else{
                boolean valid = false;
                
                while(!valid){
                    System.out.println("Digite a coluna:");
                    column = scanner.next();
                    if(computerMatrix.mapColumns.get(column) != null){
                        valid = true;
                    }
                }
                
                valid = false;
                while(!valid){
                    System.out.println("Digite a linha:");
                    if(scanner.hasNextInt()){
                        row = scanner.nextInt();
                        if(row >= 0 || row <= 9){
                            valid = true;
                        }
                    }else{
                        scanner.next();
                    }
                }
                if(controller.attack(computerMatrix, row, column)){
                    playerPoints++;
                    System.out.println("Hit!");
                }else{
                    System.out.println("Water!");
                }
                //Computer attack                
                if(controller.randomAttack(playerMatrix)){
                    System.out.println("Adversário acertou!");
                    computerPoints++;
                }else{
                    System.out.println("Adversário errou!");
                }
                
                //Display
                System.out.println(computerMatrix.display());
                System.out.println(playerMatrix.display());
                
                //Check points
                if(playerPoints >= 14){
                    System.out.println("Você venceu !!!");
                    run = false;
                }else if(computerPoints >= 14){
                    System.out.println("Seu adversário venceu !!!");
                    run = false;
                }
            }
        }
    }

    public static void clearConsole() {
        if (System.getProperty("os.name").equals("Linux")) {
            System.out.print("\033[H\033[2J");
        } else {
            for (int i = 0; i < 40; i++) {
                System.out.println();
            }
        }
    }

}
