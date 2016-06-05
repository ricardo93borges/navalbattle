/**
 * Created by ricardo on 21/05/16.
 */

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

}
