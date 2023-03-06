package sg.edu.ntu.cart_api.webController;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


import sg.edu.ntu.cart_api.entity.Product;
import sg.edu.ntu.cart_api.service.ProductService;


@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService service;

    @Autowired
    ProductService productService;
    
    record Message(String msg){}
    
    // @RequestMapping(method = RequestMethod.GET)
    // public ResponseEntity<List<Product>> findAll(){
    //     List<Product> products = (List<Product>)repo.findAll(); // fetch all products from database 
    //     return ResponseEntity.ok().body(products);
    // }

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.ACCEPTED);
    }

    // @RequestMapping(value="/{id}", method = RequestMethod.GET)
    // public ResponseEntity<Object> findById(@PathVariable int id){
    //     Optional<Product> prod = repo.findById(id);
    //     if(!prod.isPresent()) {
    //         return ResponseEntity.notFound().build();
    //     }
    //     return ResponseEntity.ok().body(prod.get());
    // }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Integer id) {
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.ACCEPTED);
    }

    // @RequestMapping(method = RequestMethod.POST)
    // public ResponseEntity<Object> create(@RequestBody Product product){
    //    return ResponseEntity.ok().body(repo.save(product));
    // }

    @PostMapping
    public ResponseEntity<Product> createNewProduct(@RequestBody Product product) {
        return new ResponseEntity<>(productService.createNewProduct(product), HttpStatus.ACCEPTED);
    }

    // @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    // public ResponseEntity<Object> update(@RequestBody Product product, @PathVariable int id){
    //     Optional<Product> prod = repo.findById(id);
    //     if(!prod.isPresent()) {
    //         return ResponseEntity.notFound().build();
    //     }
    //     Product searchedProd = prod.get();
    //     searchedProd.setName(product.getName());
    //     searchedProd.setDescription(product.getDescription());
    //     searchedProd.setPrice(product.getPrice());
    //     return ResponseEntity.ok().body(repo.save(searchedProd));
        
    // }

    @PutMapping("/{id}")
    public ResponseEntity<Product> editProduct(@RequestBody Product product, @PathVariable Integer id) {
        return new ResponseEntity<>(productService.editProduct(product, id), HttpStatus.ACCEPTED);
    }
    
    // @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    // public ResponseEntity<Object> delete(@PathVariable int id){
    //     Optional<Product> prod = repo.findById(id);
    //     if(!prod.isPresent()) {
    //         return ResponseEntity.notFound().build();
    //     }
    //     repo.delete(prod.get());
    //     return ResponseEntity.ok().body(new Message("Product deleted!"));
    // }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(Integer id) {
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

            // @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    // public ResponseEntity<Product> update(@RequestBody Product product, @PathVariable Integer id){
    //     Optional<Product> currentProduct = repo.findById(id); // Optional is a utility class to handle null
    //     if(currentProduct.isPresent()){ // Check if the expected object is present
    //         try{
    //             Product p = currentProduct.get(); // Get the object - Product

    //             // Update the fetched product with name, description, price sent via Request Body
    //             p.setName(product.getName());
    //             p.setDescription(product.getDescription());
    //             p.setPrice(product.getPrice());

    //             p = repo.save(p); // When "id" is present, .save() will perform update operation.
    //             return ResponseEntity.ok().body(p);
    //         }catch(Exception e){
    //             e.printStackTrace();
    //             return ResponseEntity.badRequest().build();
    //         }            
    //     }
    //     return ResponseEntity.notFound().build();
    // }

}