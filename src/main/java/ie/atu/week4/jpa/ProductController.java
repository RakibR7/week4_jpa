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
    public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody Product updatedProduct) {
        Product existingProduct = findProductById(id);

        if (existingProduct != null) {
            existingProduct.setProductName(updatedProduct.getProductName());
            existingProduct.setProductDescription(updatedProduct.getProductDescription());
            existingProduct.setProductPrice(updatedProduct.getProductPrice());
            return ResponseEntity.ok(existingProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public <product> Object deleteProduct(@PathVariable long id) {
        productservice.deleteProduct(id);
        return("Product Deleted\n");
    }
}
