package martinsgodapizzor.comradepizza.controllers;

import martinsgodapizzor.comradepizza.entities.Pizza;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PizzaControllerMethodTest {

    @Mock
    private PizzaController pizzaController;

    String[] mockURLlistIncorrect = {"/comrade/fiskpizzahejsan", "/comrade/hejsan$bokstäver$merbokstäver",
            "/comrade/pizza$4$", "/comrade/pizza$$saker", "/comrade/pizza$$", "/comrade/$57$mmjöl"};
    String mockUrlCorrect = "/comrade/pizzanamn$45$ingredienser";



    @Test
    void addPizzaTest() {

        for (String s : mockURLlistIncorrect) {
            pizzaController.addPizza(s);
            assertThat(pizzaController.pizzaList().isEmpty());
        }

        pizzaController.addPizza(mockUrlCorrect);
        assertThat(pizzaController.pizzaList().size()==1);


    }

    @Test
    void updatePizzaTest() {

        /*
        String mockPizzaPriceUpdate = "/comrade/pizzanamn$80";
        String mockPizzaIngredientsUpdate = "/comrade/pizzanamn$$nyasaker";


         */
        pizzaController.addPizza(mockUrlCorrect);
        String mockUrlCorrectFAIL = "/comrade/pizzanamn4&45ingredien#ser";



            pizzaController.updatePizza(mockUrlCorrectFAIL);
            assertThat(pizzaController.pizzaList().get(0).getName().equals("pizzanamn"));
            assertThat(pizzaController.pizzaList().get(0).getPrice()==45);
            assertThat(pizzaController.pizzaList().get(0).getIngredients().equals("ingredienser"));



    }

}