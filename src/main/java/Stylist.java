import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;

public class Stylist { ;
    private String name;
    private String contact;
    private int stylist_id;

    public Stylist(String name,  String contact, int stylist_id) {
        this.name = name;
        this.contact = contact;
        this.stylist_id = stylist_id;
}

    public int getId(){
        return stylist_id;
    }
    public String getName(){
        return name;
    }
    public String getContact(){
        return contact;
    }

    //return a Stylist object.
    public static Stylist find(int id) {
        try (Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM stylists where id=:id";
            Stylist stylist = con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Stylist.class);
            return stylist;
        }
    }
    // code to return all Stylist information from our stylist database table in the all() method:
    public static List<Stylist> all() {
        String sql = "SELECT id, name, contact FROM stylists ORDER BY name";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Stylist.class);
        }
    }
    // saving new object to the database && assign the object the same id as its data in the database:
    public void save() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO stylists(name, contact) VALUES (:name, :contact)";
            this.stylist_id = (int) con.createQuery(sql, true)
                    .addParameter("name", this.name)
                    .addParameter("contact", this.contact)
                    .executeUpdate()
                    .getKey();
        }
    }
    // getting clients from stylist object
    public List<Client> getClients() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM clients where stylist_id=:id ORDER BY name";
            return con.createQuery(sql)
                    .addParameter("id", this.stylist_id)
                    .executeAndFetch(Client.class);
        }
    }
    //updating the stylist object
    public void update(String name, String contact) {
        try (Connection con = DB.sql2o.open()) {
            String sql = "UPDATE stylists SET name = :name, contact = :contact WHERE id = :stylist_id";
            con.createQuery(sql)
                    .addParameter("name", name)
                    .addParameter("contact", contact)
                    .addParameter("id", stylist_id)
                    .executeUpdate();
        }
    }
    //deleting stylist object
    public void delete() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "DELETE FROM stylists WHERE id = :id;";
            con.createQuery(sql).addParameter("id", stylist_id).executeUpdate();
        }
        //Assign client_id to 0 for Clients allocated to the deleted Stylist
        try(Connection con = DB.sql2o.open()) {
            String sql = "UPDATE clients SET stylist_id = 0 WHERE stylist_id = :id;";
            con.createQuery(sql).addParameter("id", stylist_id).executeUpdate();
        }
    }

}
