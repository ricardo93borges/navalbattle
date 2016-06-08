package navalbattle;
/**
 * Created by ricardo on 21/05/16.
 */

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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

        ArrayList<Ship> playerShips = controller.setShips();
        ArrayList<Ship> computerShips = controller.setShips();
        HashMap<String, Integer> playerHits = new HashMap<String, Integer>();
        HashMap<String, Integer> computerHits = new HashMap<String, Integer>();
        
        controller.randomInsertShips(playerMatrix, playerShips);
        controller.randomInsertShips(computerMatrix, computerShips);
        
        System.out.println(computerMatrix.display());
        System.out.println(playerMatrix.display());
        
        int opt;
        boolean run = true;
        String column = "";
        int row = 0;
        
        int playerPoints = 0;
        int computerPoints = 0;
        
        System.out.println("Digite: \n 1 para jogar \n 2 para carregar jogo anterior .");
        opt = scanner.nextInt();
        
        if(opt == 2){
            //Load matrix, player and computer points
        }
        
        while(run){
            System.out.println("Placar ==> Você: "+playerPoints+"/14 | Adversário: "+computerPoints+"/14");
            System.out.println("Digite: \n 0 para sair \n 1 para atacar \n 2 para salvar e sair");
            opt = scanner.nextInt();
            
            if(opt==0){
                run = false;
            }else if(opt==2){
                try{
                    FileHandler fh = new FileHandler();
                    String filename = "save.txt";
                    //Erase file content
                    fh.erase(filename);
                    //Save points
                    String str = "playerPoints:"+playerPoints+"\n";
                    str += "computerPoints:"+computerPoints+"\n";
                    //Save ships
                    str += "player ships:\n";
                    for(int i=0; i < playerShips.size(); i++){
                        str += playerShips.get(i).getColumn()+","+playerShips.get(i).getRow()+"\n";
                    }
                    str += "computer ships:\n";
                    for(int i=0; i < computerShips.size(); i++){
                        str += computerShips.get(i).getColumn()+","+computerShips.get(i).getRow()+"\n";
                    }
                    //Save hits
                    Iterator it = playerHits.entrySet().iterator();
                    str += "player hits:\n";
                    while (it.hasNext()) {
                        Map.Entry pair = (Map.Entry)it.next();
                        str += pair.getKey()+","+pair.getValue()+"\n";
                        it.remove();
                    }
                    it = computerHits.entrySet().iterator();
                    str += "computer hits:\n";
                    while (it.hasNext()) {
                        Map.Entry pair = (Map.Entry)it.next();
                        str += pair.getKey()+","+pair.getValue()+"\n1"
                                + "";
                        it.remove();
                    }
                    //save
                    fh.write(filename, str);
                    //read
                    ArrayList<String> file = fh.read(filename);
                    for(int i=0; i<file.size(); i++){
                        System.out.println(file.get(i));
                    }
                    run = false;
                }catch(FileNotFoundException e){
                    System.out.println("Error on write file. "+e.getMessage());
                }catch(UnsupportedEncodingException e){
                    System.out.println("Error on write file. "+e.getMessage());
                }
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
                    playerHits.put(column, row);
                    System.out.println("Hit!");
                }else{
                    System.out.println("Water!");
                }
                //Computer attack                
                if(controller.randomAttack(playerMatrix)){
                    System.out.println("Adversário acertou!");
                    computerHits.put(column, row);
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
