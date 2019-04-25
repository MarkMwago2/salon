import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;
public class Client {
    private int id;
    private String name;
    private String contact;
    private int stylist_id;
    // client constuructor
    public Client(String name, String contact, int stylist_id) {
        this.name = name;
        this.contact = contact;
        this.stylist_id = stylist_id;
    }

    // client class getter methods
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getContact(){
        return contact;
    }
    public int getStylist_id() {return stylist_id;}

    //return stylist Id
    public Stylist getStylist() {
        int id_check = stylist_id;
        Stylist stylist;
        if(id_check==0){
            stylist = new Stylist("","");
        } else {
            try(Connection con = DB.sql2o.open()) {
                String sql = "SELECT * FROM stylists where id=:id";
                stylist = con.createQuery(sql)
                        .addParameter("id", id)
                        .executeAndFetchFirst(Stylist.class); }
        }
        return stylist;
    }
    //return a Client object.
    public static Client find(int id) {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM clients where id=:id";
            Client client = con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Client.class);
            return client;
        }
    }

    //updating Client.all() method's SQL query to include Sylist Id.
    public static List<Client> all() {
        String sql = "SELECT id, name, contact, stylist_id FROM clients ORDER BY name";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Client.class);
        }
    }
    //saving new object to the database && assign the object the same id as its data in the database:
    public void save() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO clients(name, contact, stylist_id) VALUES (:name, :contact, :stylist_id)";
            this.id = (int) con.createQuery(sql, true)
                    .addParameter("name", this.name)
                    .addParameter("contact", this.contact)
                    .addParameter("stylist_id", this.stylist_id)
                    .executeUpdate()
                    .getKey();
        }
    }
    // updating a client object
    public void update(String name, String contact, int stylist_id) {
        try(Connection con = DB.sql2o.open()) {
            String sql = "UPDATE clients SET name = :name, contact = :contact, stylist_id = :stylist_id WHERE id = :id";
            con.createQuery(sql)
                    .addParameter("name", name)
                    .addParameter("contact", contact)
                    .addParameter("stylist_id", stylist_id)
                    .executeUpdate();
        }
    }
    //deleting a client object
    public void delete() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "DELETE FROM clients WHERE id = :id;";
            con.createQuery(sql).addParameter("id", id).executeUpdate();
        }
    }

}
