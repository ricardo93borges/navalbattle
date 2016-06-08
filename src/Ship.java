package navalbattle;
/**
 * Created by ricardo on 21/05/16.
 */
public class Ship {

    private String name;
    private int slots;
    private char orientation;//H(orizontal) or V(ertical)
    private String slug;
    private String column;
    private int row;

    public Ship(String name, int slots, char orientation) {
        this.name = name;
        this.slots = slots;
        this.orientation = orientation;
        this.slug = String.valueOf(name.charAt(0));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSlots() {
        return slots;
    }

    public void setSlots(int slots) {
        this.slots = slots;
    }

    public char getOrientation() {
        return orientation;
    }

    public void setOrientation(char orientation) {
        this.orientation = orientation;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
}