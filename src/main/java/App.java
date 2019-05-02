import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }

        setPort(port);

        staticFileLocation("/public");
        String layout = "templates/layout.vtl";

        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("animals", Animal.all());
            model.put("endangered", Endangered.all());
            model.put("sightings", Sightings.all());
            model.put("template", "templates/index.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());


        get("/sightings", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("animals", Animal.all());
            model.put("endangered", Endangered.all());
            model.put("sightings", Sightings.all());
            model.put("template", "templates/sightings.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        post("/endangered_sighting", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String rangerName = request.queryParams("rangerName");
            int animalIdSelected = Integer.parseInt(request.queryParams("endangeredAnimalSelected"));
            String location = request.queryParams("location");
            Sightings sighting = new Sightings(animalIdSelected, location, rangerName);
            sighting.save();
            model.put("sightings", sighting);
            model.put("animals", Endangered.all());
            String animal = Endangered.find(animalIdSelected).getName();
            model.put("animal", animal);
            model.put("template", "templates/success.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        post("/sighting", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String rangerName = request.queryParams("rangerName");
            int animalIdSelected = Integer.parseInt(request.queryParams("animalSelected"));
            String location = request.queryParams("location");
            Sightings sighting = new Sightings(animalIdSelected, location, rangerName);
            sighting.save();
            model.put("sighting", sighting);
            model.put("animals", Animal.all());
            String animal = Animal.find(animalIdSelected).getName();
            model.put("animal", animal);
            model.put("template", "templates/success.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/animal/new", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("animals", Animal.all());
            model.put("endangered", Endangered.all());
            model.put("template", "templates/animal-form.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        post("/animal/new", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            boolean endangered = request.queryParamsValues("endangered")!=null;
            if (endangered) {
                String name = request.queryParams("name");
                String health = request.queryParams("health");
                String age = request.queryParams("age");
                Endangered endangeredAnimal = new Endangered(name, health, age);
                endangeredAnimal.save();
                model.put("animals", Animal.all());
                model.put("endangered", Endangered.all());
            } else {
                String name = request.queryParams("name");
                Animal animal = new Animal(name);
                animal.save();
                model.put("animals", Animal.all());
                model.put("endangered", Endangered.all());
            }
            response.redirect("/");
            return null;
        });

        get("/animal/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            Animal animal = Animal.find(Integer.parseInt(request.params("id")));
            model.put("animal", animal);
            model.put("template", "templates/animal.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/Endangered/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            Endangered endangeredAnimal = Endangered.find(Integer.parseInt(request.params("id")));
            model.put("Endangered", endangeredAnimal);
            model.put("template", "templates/endangered.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());


    }
}



