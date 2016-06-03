package bn;
/**
 * Created by ricardo on 21/05/16.
 */

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

/**
 * TODO finalizar motodo para distribuir embarcações aleatoreamente,
 * distribuir verticalmente
 * redistribuir em exceptions
 **/
public class Main {

    public static Matrix init() {
        try {
            Matrix m = new Matrix(10, 10);
            return m;
        } catch (NavalBattleException e) {
            System.out.println(e.getMessage());
            return null;
        }
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

    /*
        public static void test(){
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
        }
    */
    public static boolean insertShip(Controller c, Matrix m, Ship s, int row, String col) {
        try {
            c.insertShip(s, m, row, col);
            return true;
        } catch (NavalBattleException e) {
            return false;
        }
    }

    public static int stringToInt(String s) {
        int n;
        try {
            n = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return -1;
        } catch (NullPointerException e) {
            return -1;
        }
        return n;
    }

    public static void attack(Controller controller, Matrix m, int row, String column) {
        if (controller.attack(m, row, column)) {
            System.out.println("Hit");
        } else {
            System.out.println("Water!!!");
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
