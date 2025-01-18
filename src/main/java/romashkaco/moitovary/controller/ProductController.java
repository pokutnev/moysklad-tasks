package romashkaco.moitovary.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import romashkaco.moitovary.entity.Product;
import romashkaco.moitovary.repository.ProductRepository;

import java.util.List;

@RestController
@RequestMapping("api/products")
public class ProductController {
    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productRepository.loadAll();
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Object> getProductByName(@PathVariable String name) {
        if (name != null) {
            if (name.length() > 255) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product name must be less than 255");
            }
            Product product = productRepository.loadByName(name);
            if (product != null) {
                return ResponseEntity.status(HttpStatus.OK).body(product);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody @Valid Product product) {
        product = productRepository.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PatchMapping
    public ResponseEntity<String> updateProductName(@RequestParam String productName, @RequestParam String newName) {
        if (newName != null && newName.length() > 255) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product name must be less than 255");
        }

        if (productRepository.updateProductName(productName, newName)) {
            return ResponseEntity.status(HttpStatus.OK).body("Product has been updated");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProductName(@RequestParam String name) {
        if (productRepository.deleteProductByName(name)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
