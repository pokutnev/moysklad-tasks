package romashkaco.moitovary.repository;

import org.springframework.stereotype.Repository;
import romashkaco.moitovary.entity.Product;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {
    private List<Product> products = new ArrayList<>();

    public List<Product> loadAll() {
        return products;
    }

    public Product loadByName(String name) {
        return products.stream().filter(product -> product.getName().equals(name)).findFirst().orElse(null);
    }

    public Product createProduct(Product product) {
        products.add(product);
        return product;
    }

    public boolean updateProductName(String productName, String newName) {
        for (Product product : products) {
            if (product.getName().equals(productName)) {
                product.setName(newName);
                return true;
            }
        }
        return false;
    }

    public boolean deleteProductByName(String name) {
        return products.removeIf(product -> product.getName().equals(name));
    }
}
