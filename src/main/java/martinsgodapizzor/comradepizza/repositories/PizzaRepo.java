package martinsgodapizzor.comradepizza.repositories;

import martinsgodapizzor.comradepizza.entities.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface PizzaRepo extends JpaRepository<Pizza, String> {

    Set<Pizza> findAllByName(String name);

    Set<Pizza> findAllByNameContaining(String name);

    Set<Pizza> findPizzaByIngredientsContaining(String ingredients);

    Set<Pizza> deleteByName (String name);



}
