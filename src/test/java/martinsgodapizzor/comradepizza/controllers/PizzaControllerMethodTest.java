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

}