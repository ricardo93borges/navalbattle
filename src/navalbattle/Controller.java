package navalbattle;
/**
 * Created by ricardo on 21/05/16.
 */

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Controller {

    /**
     * Insert ship by coordenates
     * @param ship
     * @param matrix
     * @param row
     * @param col
     * @throws NavalBattleException
     */
    public void insertShip(Ship ship, Matrix matrix, int row, String col) throws NavalBattleException {
        //validate and position if necessary, check if ship will go out of matrix
        int column = matrix.getColumn(col);

        int h = (row + ship.getSlots());
        if (h > matrix.getRows()) {
            row = h - matrix.getRows();
        }
        int v = (column + ship.getSlots());
        if (v > matrix.getColumns()) {
            column = v - matrix.getColumns();
        }

        //Check if coordanates already has an element
        int slots = ship.getSlots();
        while (slots > 0) {
            if (matrix.get(row, column) != null) {
                throw new NavalBattleException("Posição inválida, slots ocupados.");
            }
            slots--;
        }
        
        //Set ship coordinates
        ship.setColumn(col);
        ship.setRow(row);

        //Insert
        slots = ship.getSlots();
        while (slots > 0) {
            matrix.set(ship.getSlug(), row, column);
            slots--;
            if (ship.getOrientation() == 'H') {
                column++;
            } else {
                row++;
            }
        }
    }

    /**
     * Check if the coordinates have already been used
     * @param coordenates Hashmap
     * @param column String
     * @param row int
     * @return boolean
     */
    public boolean checkCoordenate(HashMap coordenates, String column, int row) {
        if (coordenates.containsKey(column)) {
            if (coordenates.get(column).equals(row)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Insert ships randomly in the matrix
     * @param m Matrix
     * @param ships Arraylisy
     */
    public void randomInsertShips(Matrix m, ArrayList<Ship> ships) {
        String[] columns = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j"};
        char[] orientations = {'V', 'H'};
        HashMap<String, Integer> usedCoordinates = new HashMap<String, Integer>();
        ArrayList<Ship> remaining = new ArrayList<Ship>();
        Random r = new Random();
        for (Ship ship : ships) {
            try {
                //Define orientation, 2 to increase the chances of not repeating
                int o = r.nextInt(2);
                if (o > 1) o = o - 1;
                char orientation = orientations[o];
                ship.setOrientation(orientation);
                int n = r.nextInt(9);
                String l = columns[n];
                while (!checkCoordenate(usedCoordinates, l, n)) {
                    n = r.nextInt(9);
                    l = columns[n];
                }
                usedCoordinates.put(l, n);

                this.insertShip(ship, m, n, l);

            } catch (NavalBattleException e) {
                System.out.println("Exception");
                remaining.add(ship);
            }
        }
        if (remaining.size() > 0) {
            randomInsertShips(m, remaining);
        }
    }
    
     /**
     * Randomly attack
     * @param m Matrix
     */
    public boolean randomAttack(Matrix m) {
        String[] columns = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j"};
        HashMap<String, Integer> usedCoordinates = new HashMap<String, Integer>();
        Random r = new Random();
        
        int row = r.nextInt(9);
        String column = columns[row];
        while (!checkCoordenate(usedCoordinates, column, row)) {
            row = r.nextInt(9);
            column = columns[row];
        }
        System.out.println("Adversário atacando em "+column+" "+row+" ... ");
        usedCoordinates.put(column, row);
        return this.attack(m, row, column);
    }

    /**
     * Attack from the coordenates
     * @param matrix Matrix
     * @param row Int
     * @param col String
     * @return boolean
     */
    public boolean attack(Matrix matrix, int row, String col) {
        try {
            int column = matrix.getColumn(col);
            if (matrix.get(row, column) == null) {
                return false;
            }
            matrix.set("X", row, column);
            return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    /**
     * Set up ships
     * @return ArrayList<Ship>
     */
    public ArrayList<Ship> setShips() {
        ArrayList<Ship> ships = new ArrayList<Ship>();
        ships.add(new Ship("A", 5, 'H'));
        ships.add(new Ship("B", 4, 'H'));
        ships.add(new Ship("C", 3, 'H'));
        ships.add(new Ship("D", 2, 'H'));
        return ships;
    }
    
    /**
     * Load game
     * @param playerMatrix
     * @param computerMatrix
     * @return 
     */
    public HashMap loadGame(Matrix playerMatrix, Matrix computerMatrix) {        
        FileHandler fh = new FileHandler();
        String filename = "save.txt";

        //Set data to return
        HashMap<String, HashMap<String, Integer>> data = new HashMap<String, HashMap<String, Integer>>();
        data.put("points", new HashMap<String, Integer>());
        data.put("playerHits", new HashMap<String, Integer>());
        data.put("computerHits", new HashMap<String, Integer>());
        data.put("computerShips", new HashMap<String, Integer>());
        data.put("playerShips", new HashMap<String, Integer>());
        
        //Read saved data 
        ArrayList<String> file = fh.read(filename);
        for(int i=0; i<file.size(); i++){
            String line = file.get(i);
            String[] parts = line.split(":");
            if(parts.length <= 1 || parts[1].isEmpty()) continue;
            if(parts[0].equals("playerPoints")){
                int playerPoints = Integer.parseInt(parts[1]);
                data.get("points").put("playerPoints", playerPoints);
            }else if(parts[0].equals("computerPoints")){
                int computerPoints = Integer.parseInt(parts[1]);
                data.get("points").put("computerPoints", computerPoints);
            }else if(parts[0].equals("player ships")){
                String[] ships = parts[1].split(";");
                this.loadSavedShips(playerMatrix, ships, data, "computerShips");
            }else if(parts[0].equals("computer ships")){
                String[] ships = parts[1].split(";");
                this.loadSavedShips(computerMatrix, ships, data, "playerShips");
            }else if(parts[0].equals("player hits")){
                String[] hits = parts[1].split(";");
                this.loadSavedHits(computerMatrix, hits, data, "playerHits");
            }else if(parts[0].equals("computer hits")){
                String[] hits = parts[1].split(";");
                this.loadSavedHits(playerMatrix, hits, data, "computerHits");
            }
        }
        return data;
    }
    
    /**
     * Load saved hits
     * @param m
     * @param hits
     * @param data
     * @param key 
     */
    public void loadSavedHits(Matrix m, String[] hits, HashMap<String, HashMap<String, Integer>> data, String key){
        for(int j=0; j < hits.length; j++){
            String[] hit = hits[j].split(",");
            if(hit.length < 2) continue;
            String column = hit[0];
            int row = Integer.parseInt(hit[1]);
            data.get(key).put(column, row);
            this.attack(m, row, column);
        }
    }
    
    /**
     * Load saved ships
     * @param m
     * @param ships
     * @param data
     * @param key 
     */
    public void loadSavedShips(Matrix m, String[] ships, HashMap<String, HashMap<String, Integer>> data, String key){
        for(int j=0; j < ships.length; j++){
            String[] shipData = ships[j].split(",");
            Ship ship = new Ship(shipData[0], Integer.parseInt(shipData[2]), shipData[1].charAt(0));
            String column = shipData[3];
            int row = Integer.parseInt(shipData[4]);
            data.get(key).put(column, row);
            try{
                this.insertShip(ship, m, row, column);
            }catch(NavalBattleException e){
                System.out.println("Error on insert ship. "+e.getMessage());
            }
        }
    }
}