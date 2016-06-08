package navalbattle;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class ControllerTest {

    @Test
    public void checkCoordenateTest(){
        Controller controller = new Controller();
        HashMap<String, Integer> usedCoordinates = new HashMap<String, Integer>();
        usedCoordinates.put("a", 1);
        assertEquals("Must be False", false, controller.checkCoordenate(usedCoordinates, "a", 1));
        assertEquals("Must be True", true, controller.checkCoordenate(usedCoordinates, "b", 2));
    }

    @Test
    public void attackTest(){
        Matrix matrix = new Matrix(10,10);
        Ship ship = new Ship("A", 5, 'H');
        Controller controller = new Controller();
        try {
            controller.insertShip(ship, matrix, 1, "c");
            assertEquals("Must be true", true, controller.attack(matrix, 1, "c"));
            assertEquals("Must be false", false, controller.attack(matrix, 1, "b"));
            assertEquals("Must be false", false, controller.attack(matrix, -1, "b"));
        }catch (NavalBattleException e){
            System.out.println(e.getMessage());
        }
    }

}