import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class StylistTest {

    // testing save method
    @Test
    public void savingStylist_true() {
        Stylist stylist = new Stylist("Carol","0700500000", 1);
        stylist.save();
        assertEquals("Carol", stylist.getName());
    }
    // testing for update method
    @Test
    public void updatingStylist_true() {
        Stylist stylist = new Stylist("Carol","070050000", 1);
        stylist.save();
        stylist.update("Brandon", "0700600000");
        assertEquals("Brandon", Stylist.find(stylist.getId()).getName());
        assertEquals("0700600000", Stylist.find(stylist.getId()).getContact());

    }
    //find stlist test
    @Test
    public void findStylist_true() {
        Stylist stylist = new Stylist("Carol","0700500000", 1);
        stylist.save();
        assertEquals("Carol", Stylist.find(stylist.getId()).getName());
    }
    //delete stylist test
    @Test
    public void delete_deletesStylist_true() {
        Stylist stylist = new Stylist("Carol",  "0700500000", 1);
        stylist.save();
        int stylistId = stylist.getId();
        stylist.delete();
        assertEquals(null, Stylist.find(stylistId));
    }

}
