package martinsgodapizzor.comradepizza.controllers;

import martinsgodapizzor.comradepizza.entities.Pizza;
import martinsgodapizzor.comradepizza.repositories.PizzaRepo;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@RestController
public class PizzaController {

    private final PizzaRepo pizzaRepo;

    public PizzaController(PizzaRepo pizzaRepo) {
        this.pizzaRepo = pizzaRepo;
    }

    @GetMapping("/comrade")
    public List<Pizza> pizzaList() {

        return pizzaRepo.findAll();
    }


    @GetMapping("/comrade/search/{searchParameter}")
    Set<Pizza> getPizzasWithIngredient(@PathVariable String searchParameter) {
        Set<Pizza> pizzor = pizzaRepo.findPizzaByIngredientsContaining(searchParameter);
        Set<Pizza> mer = pizzaRepo.findAllByNameContaining(searchParameter);
        pizzor.addAll(mer);
        return pizzor;
    }

    @Transactional
    @DeleteMapping("/comrade/admin/delete/{name}")
    public String removePizza(@PathVariable String name) {

        if (!pizzaRepo.findAllByName(name).isEmpty()) {
            pizzaRepo.deleteByName(name);
            return "Pizza <" + name + "> Removed";
        }
        return "Pizza Not Found";
    }

    @Transactional
    @PatchMapping("/comrade/admin/{pizza}")
    public String updatePizza(@PathVariable String pizza) {
        String[] values = pizza.split("[$]");

        if (pizzaRepo.findById(values[0]).isEmpty()) {
            return "Invalid Pizza! You cannot replace something that doesn't exist.";

        }
        Pizza newPizza = pizzaRepo.getById(values[0]);
        int price;
        if (values.length == 2) {
            try {
                price = Integer.parseInt(values[1]);
                newPizza.setPrice(price);
            } catch (NumberFormatException a) {
                return "Incorrect Format";
            }
        } else if (values.length > 2) {
            if (!values[1].isBlank()) {
                try {
                    price = Integer.parseInt(values[1]);
                    newPizza.setPrice(price);
                } catch (NumberFormatException a) {
                    return "Incorrect Format";
                }
            }
            newPizza.setIngredients(values[2]);
        } else {
            return "Incorrect Format";
        }
        pizzaRepo.save(newPizza);
        return "Pizza Updated";

    }

    @PostMapping("/comrade/admin/{value}")
    public String addPizza(@PathVariable String value) {
        String[] values = value.split("[$]");
        int price;
        String ingredients;
        String name;
        try {
            ingredients = values[2];
            name = values[0];
        } catch (IndexOutOfBoundsException e) {
            return "Incorrect format";
        }
        try {
            price = Integer.parseInt(values[1]);
        } catch (NumberFormatException num) {
            return "Incorrect format";
        }
        pizzaRepo.save(new Pizza(name, price, ingredients));
        return "Pizza added";
    }

    @PutMapping("/comrade/admin/{pizza}")
    public String replacePizza(@PathVariable String pizza) {
        String[] values = pizza.split("[$]");
        int price;
        String name;
        String ingredients;
        if (values.length < 3) return "Incorrect format";

            name = values[0];
            ingredients = values[2];

            try {
                price = Integer.parseInt(values[1]);
            } catch (NumberFormatException e) {
                return "Incorrect format";
            }

            if (pizzaRepo.findById(name).isPresent()) {
                pizzaRepo.deleteById(name);
                pizzaRepo.save(new Pizza(name, price, ingredients));
                return "Pizza updated";
            }

            return "Pizza not found";
        }

    }
