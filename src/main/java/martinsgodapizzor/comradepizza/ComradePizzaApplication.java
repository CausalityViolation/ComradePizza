package martinsgodapizzor.comradepizza;

import martinsgodapizzor.comradepizza.entities.Pizza;
import martinsgodapizzor.comradepizza.repositories.PizzaRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ComradePizzaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComradePizzaApplication.class, args);
    }

    @Bean
    public CommandLineRunner initiate(PizzaRepo PizzaRepository) {

        return (args) -> {
            if (PizzaRepository.count() == 0) {

                PizzaRepository.save(new Pizza("Tyresö Special", 110,"Ost,Tomat,Oxfilé,Lök,Vitlök,Bea" ));
                PizzaRepository.save(new Pizza("Köttboden", 200,"Fläsk,Biff,Bacon,Korv" ));
                PizzaRepository.save(new Pizza("Fiskpizzan", 250,"Ost,Mozzarella,Fiskbullar,Lök" ));
                PizzaRepository.save(new Pizza("Ferdinand", 110,"Chorizo,Tjur,Ost" ));
                PizzaRepository.save(new Pizza("Grand Danois", 110,"Ost,Tomat,Hår" ));
                PizzaRepository.save(new Pizza("Cocktickler", 110,"Tupp,fisk" ));
                PizzaRepository.save(new Pizza("Hellfire", 666,"Fire,Brimstone" ));
            }

        };
    }

}
