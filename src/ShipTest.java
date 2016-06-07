import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ShipTest {

    @Test
    public void shipTest(){
        Ship ship = new Ship("A", 5, 'H');
        assertEquals("Must be A", "A", ship.getName());
        assertEquals("Must be 5", 5, ship.getSlots());
        assertEquals("Must be H", 'H', ship.getOrientation());
        assertEquals("Must be A", "A", ship.getSlug());
    }
}