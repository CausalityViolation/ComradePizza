package martinsgodapizzor.comradepizza.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {PizzaController.class})
class PizzaMVCTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllPizzasReturnsOnePizza() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/comrade"))
                .andExpect(status().is(200));

    }
}
