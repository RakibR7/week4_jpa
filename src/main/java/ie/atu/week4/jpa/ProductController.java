package ie.atu.week4.jpa;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private List<Product> productList = new ArrayList<>();
    private ProductService productservice;
    public ProductController(ProductService productservice) {
        this.productservice = productservice;
    }
    @GetMapping("/get")
    public List<Product> getProducts() {
        return productList;
    }

    @PostMapping("/add")
    public ResponseEntity<List> addProduct(@Valid @RequestBody Product product) {
        productList = productservice.addProduct(product);
        return ResponseEntity.ok(productList);
    }

    private Product findProductById(int id) {
        for (Product product : productList) {
            if (product.getProductCode() == id) {
                return product;
            }
        }
        return null;
    }

    @PutMapping("/updated/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody Product updatedProduct) {
        Product existingProduct = productservice.findProductById(id);

        if (existingProduct != null) {
            productservice.updateProduct(id, updatedProduct);
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        if (productservice.findProductById(id) != null) {
            productservice.deleteProduct(id);
            return ResponseEntity.ok("Product Deleted\n");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
