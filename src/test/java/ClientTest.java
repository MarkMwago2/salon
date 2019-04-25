import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class ClientTest {

    @Before
    public void setUp() {
        DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/salon_test", "mark", "87654321");
    }

    @Test
    public void savingClient_true() {
        Client client = new Client("1, Wanja", "0700100000", 2);
        client.save();
        assertEquals("Wanja", client.getName());
    }

    @Test
    public void findCLient_true() {
        Client client = new Client("Wanja","0700100000", 2);
        assertEquals("Wanja", Client.find(client.getId()).getName());
    }
    // updating client test
    @Test
    public void updatingClient_true() {
        Client client = new Client("Wanja","0700100000", 2);
        client.update("Brandon", "0700200000", 2);
        assertEquals("Brandon", Client.find(client.getId()).getName());
        assertEquals("0700200000", Client.find(client.getId()).getContact());
        assertEquals(2, Client.find(client.getId()).getStylist());
    }
    //delete client test
    @Test
    public void delete_deletesClient_true() {
        Client client = new Client("Wanja","0700100000", 2);
        int clientId = client.getId();
        client.delete();
        assertEquals(null, Client.find(clientId));
    }

}


