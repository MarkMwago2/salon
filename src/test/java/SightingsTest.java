import org.junit.*;
import org.sql2o.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.text.DateFormat;
import java.util.Date;

public class SightingsTest {

    @Rule
    public DatabaseRule database = new DatabaseRule();

    @Test
    public void sighting_instantiatesCorrectly_true() {
        Animal testAnimal = new Animal("Leopard");
        testAnimal.save();
        Sightings testSighting = new Sightings(testAnimal.getId(), "zone A", "Ranger Mark");
        assertEquals(true, testSighting instanceof Sightings);
    }

    @Test
    public void equals_returnsTrueIfLocationAndDescriptionAreSame_true() {
        Animal testAnimal = new Animal("Leopard");
        testAnimal.save();
        Sightings testSighting = new Sightings(testAnimal.getId(), "zone A", "Ranger James");
        Sightings anotherSighting = new Sightings(testAnimal.getId(), "zone A", "Ranger James");
        assertTrue(testSighting.equals(anotherSighting));
    }

    @Test
    public void save_insertsObjectIntoDatabase_Sighting() {
        Animal testAnimal = new Animal("Leopard");
        testAnimal.save();
        Sightings testSighting = new Sightings (testAnimal.getId(), "zone A", "Ranger Mark");
        testSighting.save();
        assertEquals(true, Sightings.all().get(0).equals(testSighting));
    }

    @Test
    public void all_returnsAllInstancesOfSighting_true() {
        Animal testAnimal = new Animal("Leopard");
        testAnimal.save();
        Sightings testSighting = new Sightings (testAnimal.getId(), "zone A", "Ranger Mark");
        testSighting.save();
        Animal secondTestAnimal = new Animal("Rhino");
        secondTestAnimal.save();
        Sightings secondTestSighting = new Sightings (testAnimal.getId(), "zone A", "Ranger James");
        secondTestSighting.save();
        assertEquals(true, Sightings.all().get(0).equals(testSighting));
        assertEquals(true, Sightings.all().get(1).equals(secondTestSighting));
    }

    @Test
    public void find_returnsSightingWithSameId_secondSighting() {
        Animal testAnimal = new Animal("Leopard");
        testAnimal.save();
        Sightings testSighting = new Sightings (testAnimal.getId(), "zone A", "Ranger Mark");
        testSighting.save();
        Animal secondTestAnimal = new Animal("Rhino");
        secondTestAnimal.save();
        Sightings secondTestSighting = new Sightings (testAnimal.getId(), "zone A", "Ranger James");
        secondTestSighting.save();
        assertEquals(Sightings.find(secondTestSighting.getId()), secondTestSighting);
    }


}