package sg.edu.ntu.cart_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import sg.edu.ntu.cart_api.Exceptions.ProductNotFoundException;
import sg.edu.ntu.cart_api.entity.Product;
import sg.edu.ntu.cart_api.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepo;
    
    // Get all products
    public List<Product> getAllProducts() {
        return (List<Product>)productRepo.findAll();
    }

    // Get Product by ID
    public Product getProductById(Integer id) throws RuntimeException {
        Optional<Product> prod = productRepo.findById(id);
        if(!prod.isPresent()) {
            throw new RuntimeException("Product with id " + id + " not found!");
        }
        return prod.get();
    }

    // Create new Product
    public Product createNewProduct(Product product) {
        return productRepo.save(product);
    }

    // Edit Product
    public Product editProduct(Product product, Integer id) throws RuntimeException {
        Optional<Product> prod = productRepo.findById(id);
        if(!prod.isPresent()) {
            throw new RuntimeException("Product with id " + id + " not found!");
        }
        Product prodFound = prod.get();
        prodFound.setName(product.getName());
        prodFound.setPrice(product.getPrice());
        prodFound.setDescription(product.getDescription());
        return productRepo.save(prodFound);
    }

    // Delete Product
    public void deleteProductById(Integer id) throws RuntimeException {
        Optional<Product> prod = productRepo.findById(id);
        if(!prod.isPresent()) {
            throw new RuntimeException("Product with id " + id + " not found!");
        }
        productRepo.delete(prod.get());
    }

    // Check for optional
    public static Product unwrapProduct (Optional<Product> product, Integer id) {
        if(product.isEmpty()) {
            throw new ProductNotFoundException("Product with Id " + id + " cannot be found!");
        }
        return product.get();
    }


    
}
