package romashkaco.moitovary.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import romashkaco.moitovary.entity.PriceOperation;
import romashkaco.moitovary.entity.Product;
import romashkaco.moitovary.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/products")
public class ProductController {
    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepositoryNew) {
        this.productRepository = productRepositoryNew;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/search/price")
    public ResponseEntity<List<Product>> getProductByPrice(@RequestParam BigDecimal price,
                                                           @RequestParam(defaultValue = "=") String operation) {
        List<Product> products = new ArrayList<>();
        PriceOperation priceOperation = PriceOperation.fromString(operation);
        if (priceOperation != null){
            switch (priceOperation) {
                case EQUAL:
                    products = productRepository.findByPrice(price);
                    break;
                case GREATER:
                    products = productRepository.findByPriceGreaterThanEqual(price);
                    break;
                case LESS:
                    products = productRepository.findByPriceLessThan(price);
                    break;
                default:
                    return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<Product>> getProductsSorted(@RequestParam(defaultValue = "name") String sortBy,
                                                           @RequestParam(defaultValue = "asc") String order) {

        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        List<Product> products = productRepository.findAll(Sort.by(direction, sortBy));
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search/name")
    public ResponseEntity<Object> getProductByName(@RequestParam String name,
                                                   @RequestParam(defaultValue = "false") boolean isContainAllow) {
        if (name != null) {
            if (name.length() > 255) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product name must be less than 255");
            }
            if (isContainAllow) {
                List<Product> products = productRepository.findByNameContaining(name);
                if (!products.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.OK).body(products);
                }
            } else {
                Product product = productRepository.findByName(name);
                if (product != null) {
                    return ResponseEntity.status(HttpStatus.OK).body(product);
                }
            }
            Product product = productRepository.findByName(name);
            if (product != null) {
                return ResponseEntity.status(HttpStatus.OK).body(product);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search/ability")
    public ResponseEntity<Object> getProductsByAbility(@RequestParam boolean ability) {
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.findByAbility(ability));
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody @Valid Product product) {
        product = productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PatchMapping
    public ResponseEntity<String> updateProductName(@RequestParam String productName, @RequestParam String newName) {
        if (newName != null && newName.length() > 255) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product name must be less than 255");
        }

        if (productRepository.updateName(productName, newName) > 0) {
            return ResponseEntity.status(HttpStatus.OK).body("Product has been updated");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProductName(@RequestParam String name) {
        if (productRepository.deleteByName(name) > 0) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
