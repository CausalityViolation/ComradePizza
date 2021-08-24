package martinsgodapizzor.comradepizza.controllers;

import martinsgodapizzor.comradepizza.entities.Pizza;
import martinsgodapizzor.comradepizza.repositories.PizzaRepo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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


    @GetMapping("/comrade/{searchParameter}")
    Set<Pizza> getPizzasWithIngredient(@PathVariable String searchParameter) {
        Set<Pizza> pizzor = pizzaRepo.findPizzaByIngredientsContaining(searchParameter);
        Set<Pizza> mer = pizzaRepo.findAllByNameContaining(searchParameter);
        pizzor.addAll(mer);
        return pizzor;
    }

    @Transactional
    @DeleteMapping("/comrade/admin/{name}")
    public String removePizza(@PathVariable String name) {

        if (!pizzaRepo.findAllByName(name).isEmpty()) {
            pizzaRepo.deleteByName(name);
            return "Pizza <" + name + "> Removed";
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @Transactional
    @PatchMapping("/comrade/admin/{pizza}")
    public String updatePizza(@PathVariable String pizza) {
        String[] values = pizza.split("[$]");

        if (pizzaRepo.findById(values[0]).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        }
        Pizza newPizza = pizzaRepo.getById(values[0]);
        int price;
        if (values.length == 2) {
            try {
                price = Integer.parseInt(values[1]);
                newPizza.setPrice(price);
            } catch (NumberFormatException a) {
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
            }
        } else if (values.length > 2) {
            if (!values[1].isBlank()) {
                try {
                    price = Integer.parseInt(values[1]);
                    newPizza.setPrice(price);
                } catch (NumberFormatException a) {
                    throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
                }
            }
            newPizza.setIngredients(values[2]);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }
        pizzaRepo.save(newPizza);
        return "Pizza Updated";

    }

    //comrade/admin/PIZZANAMN$PRIS$INGREDIENSER
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
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }
        try {
            price = Integer.parseInt(values[1]);
        } catch (NumberFormatException num) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
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

        if (values.length < 3) throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);

        name = values[0];
        ingredients = values[2];

        try {
            price = Integer.parseInt(values[1]);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }

        if (pizzaRepo.findById(name).isPresent()) {
            pizzaRepo.deleteById(name);
            pizzaRepo.save(new Pizza(name, price, ingredients));
            return "Pizza updated";
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

}
