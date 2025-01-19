package romashkaco.moitovary.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class Product {
    @Id
    @GeneratedValue()
    private UUID id;
    @Size(max = 255, message = "Product name must be less than 255")
    private String name;
    @Size(max = 4096, message = "Product description must be less than 4096")
    private String description;
    @DecimalMin(value = "0.0", message = "Product price must be more than 0")
    private BigDecimal price = BigDecimal.ZERO;
    private boolean ability = false;

    public Product(String name, String description, BigDecimal price, boolean ability) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.ability = ability;
    }

    public Product(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Product() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isAbility() {
        return ability;
    }

    public void setAbility(boolean ability) {
        this.ability = ability;
    }
}
