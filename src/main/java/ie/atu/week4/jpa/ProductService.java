package ie.atu.week4.jpa;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private ProductRepository productRepository;

    private List<Product> productList = new ArrayList<>();

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> addProduct(Product product){
        productRepository.save(product);
        return productRepository.findAll();
    }

    private Product findProductById(Long id) {
        for (Product product : productList) {
            if (product.getProductCode() == id) {
                return product;
            }
        }
        return null;
    }

    public List<Product> updateProduct(Long id, Product product){
        if(productRepository.existsById(id)){
            product.setId(id);
            productRepository.save(product);
        }
        return productRepository.findAll();
    }

    public List<Product> deleteProduct(Long id){
        if(productRepository.existsById(id)) {
            productRepository.deleteById(id);
        }
        return productRepository.findAll();
    }
}