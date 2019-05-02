import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class AnimalTest {
    @Rule
    public DatabaseRule database = new DatabaseRule();

    @Test
    public void animal_instantiatesCorrectly_false() {
        Animal testAnimal = new Animal("Monkey");
        assertEquals(true, testAnimal instanceof Animal);
    }

    @Test
    public void getName_animalInstantiatesWithName_Deer() {
        Animal testAnimal = new Animal("Monkey");
        assertEquals("Monkey", testAnimal.getName());
    }

    @Test
    public void equals_returnsTrueIfNameIsTheSame_false() {
        Animal firstAnimal = new Animal("Monkey");
        Animal anotherAnimal = new Animal("Monkey");
        assertTrue(firstAnimal.equals(anotherAnimal));
    }

    @Test
    public void save_assignsIdToObjectAndSavesObjectToDatabase() {
        Animal testAnimal = new Animal("Monkey");
        testAnimal.save();
        Animal savedAnimal = Animal.all().get(0);
        assertEquals(testAnimal.getId(), savedAnimal.getId());
    }

    @Test
    public void all_returnsAllInstancesOfAnimal_false() {
        Animal firstAnimal = new Animal("Monkey");
        firstAnimal.save();
        Animal secondAnimal = new Animal("Gorilla");
        secondAnimal.save();
        assertEquals(true, Animal.all().get(0).equals(firstAnimal));
        assertEquals(true, Animal.all().get(1).equals(secondAnimal));
    }

    @Test
    public void find_returnsAnimalWithSameId_secondAnimal() {
        Animal firstAnimal = new Animal("Monkey");
        firstAnimal.save();
        Animal secondAnimal = new Animal("Black Bear");
        secondAnimal.save();
        assertEquals(Animal.find(secondAnimal.getId()), secondAnimal);
    }

    @Test
    public void delete_deletesAnimalFromDatabase_0() {
        Animal testAnimal = new Animal("Monkey");
        testAnimal.save();
        testAnimal.delete();
        assertEquals(0, Animal.all().size());
    }

    public void updateName_updatesAnimalNameInDatabase_String() {
        Animal testAnimal = new Animal("Monkey");
        testAnimal.save();
        testAnimal.updateName("Buffalo");
        assertEquals("Bufallo", testAnimal.getName());
    }

}