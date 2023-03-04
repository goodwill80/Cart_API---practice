package sg.edu.ntu.cart_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import sg.edu.ntu.cart_api.entity.Product;
import sg.edu.ntu.cart_api.entity.Users;
import sg.edu.ntu.cart_api.helper.MinimumPayableCheckHelper;
import sg.edu.ntu.cart_api.repository.ProductRepository;
import sg.edu.ntu.cart_api.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringBootApplication
public class CartApiApplication {

	@Value("${MIN_PURCHASE}")
    float minimumPurchase;

	@Autowired
	ProductRepository productRepo;

	@Autowired
	UserRepository userRepo;

	public static void main(String[] args) {
		SpringApplication.run(CartApiApplication.class, args);
	}

	@Bean
	public MinimumPayableCheckHelper minimumPayableCheckHelper(){
		return new MinimumPayableCheckHelper(minimumPurchase);
	}

//	@PostConstruct
//	public void seeder() {
//
//		List<Product> productSeeder = List.of(
//				new Product("iPhone10", "latest apple phone",  999.00f),
//		   		new Product("Samsung Galaxy", "latest galaxy phone",  888.00f),
//				new Product("MiPhone", "latest Mi phone",  777.00f)
//		);
//
//		List<Users> userSeeder = List.of(
//				new Users("oliver@gmail.com", "password"),
//				new Users("jon@gmail.com", "password"),
//				new Users("dave@gmail.com", "password")
//		);
//
//		for(int i = 0; i < productSeeder.size(); i++) {
//			productRepo.save(productSeeder.get(i));
//			userRepo.save(userSeeder.get(i));
//		}
//	}

}
