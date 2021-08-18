package martinsgodapizzor.comradepizza.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Pizza {

    @Id
    String name;
    int price;
    String ingredients;

    public Pizza() {
    }

    public Pizza(String name, int price, String ingredients) {
        this.name = name;
        this.price = price;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
}
