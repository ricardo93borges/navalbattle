package navalbattle;
import static org.junit.Assert.assertEquals;
import org.junit.Test;


public class MatrixTest {

    @Test
    public void testGetColumn(){
        Matrix m = new Matrix(10,10);
        assertEquals("Must be 0", 0, m.getColumn("a"));
        assertEquals("Must be 9", 9, m.getColumn("j"));
    }

    @Test
    public void testGetColumns(){
        Matrix m = new Matrix(10,10);
        assertEquals("Must be 10", 10, m.getColumns());
    }

    @Test
    public void testGetRows(){
        Matrix m = new Matrix(10,10);
        assertEquals("Must be 10", 10, m.getRows());
    }

    @Test
    public void testSet(){
        Matrix m = new Matrix(10,10);
        assertEquals("Must be true", true, m.set("a", 1,1));
        assertEquals("Must be false", false, m.set("a", 11,1));
        assertEquals("Must be false", false, m.set("a", 1,11));
        assertEquals("Must be false", false, m.set("a", 1,-1));
    }

    @Test
    public void testGet(){
        Matrix m = new Matrix(10,10);
        m.set("a", 1,1);
        assertEquals("Must be a", "a", m.get(1,1));
    }
}