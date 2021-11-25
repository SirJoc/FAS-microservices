package upc.edu.pe.inventoryservice;

import org.assertj.core.api.Assertions;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import upc.edu.pe.inventoryservice.client.UserClient;
import upc.edu.pe.inventoryservice.entities.Product;
import upc.edu.pe.inventoryservice.exception.ResourceNotFoundException;
import upc.edu.pe.inventoryservice.model.User;
import upc.edu.pe.inventoryservice.repositories.CategoryRepository;
import upc.edu.pe.inventoryservice.repositories.ProductRepository;
import upc.edu.pe.inventoryservice.services.CategoryService;
import upc.edu.pe.inventoryservice.services.ProductService;
import upc.edu.pe.inventoryservice.services.impl.CategoryServiceImpl;
import upc.edu.pe.inventoryservice.services.impl.ProductServiceImpl;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class InventoryServiceApplicationTests {
	@MockBean
	private ProductRepository productRepository;

	@Autowired
	private ProductService productService;

	@MockBean
	private CategoryRepository categoryRepository;

	@Autowired
	private CategoryService categoryService;


	@MockBean
	@Qualifier("pe.edu.upc.paymentservice.client.UserClient")
	private UserClient userClient;

	@TestConfiguration
	static class ProductImplTestConfiguration{
		@Bean
		public ProductService productServiceService(){
			return new ProductServiceImpl();
		}

		@Bean
		public CategoryService categoryServiceService(){
			return new CategoryServiceImpl();
		}
	}


	@TestConfiguration
	static class CategoryImplTestConfiguration{
		@Bean
		public CategoryService categoryServiceService() {
			return new CategoryServiceImpl();
		}
	}

	@Test
	@DisplayName("When findProductById With Valid Id Then Returns Product")
	public void whenFindProductByIdThenReturnsProduct() {
		// Arrange
		Long id = 1L;
		String name = "PruebaName1";
		String description = "PruebaDesc1";
		String corporation = "PruebaCorp1";
		Double price = 0.00;
		Product product = new Product();
		product.setName(name);
		product.setDescription(description);
		product.setCorporation(corporation);
		product.setPrice(price);
		product.setUserId(1L);
		User user = User.builder().fullName("none").id(product.getUserId()).build();
		when(userClient.fetchById(product.getUserId())).thenReturn(ResponseEntity.of(Optional.of(user)));
		when(productRepository.findById(id)).thenReturn(Optional.of(product));

		// Act
		Product foundProduct = productService.findById(id);

		// Assert
		assertThat(foundProduct.getName()).isEqualTo(name);
		assertThat(foundProduct.getDescription()).isEqualTo(description);
		assertThat(foundProduct.getCorporation()).isEqualTo(corporation);
		assertThat(foundProduct.getPrice()).isEqualTo(price);
	}


	@Test
	@DisplayName("When findProductById With Invalid Id Then Returns RNFE")
	public void whenFindProductByIdThenReturnsResourceNotFound() {
		// Arrange
		Long id = 1L;
		String name = "PruebaName1";
		String description = "PruebaDesc1";
		String corporation = "PruebaCorp1";
		Double price = 0.00;
		Product product = new Product();
		product.setName(name);
		product.setDescription(description);
		product.setCorporation(corporation);
		product.setPrice(price);
		product.setUserId(1L);
		User user = User.builder().fullName("none").id(product.getUserId()).build();
		//Act
		when(productRepository.findById(id)).thenReturn(Optional.empty());
		when(userClient.fetchById(product.getUserId())).thenReturn(ResponseEntity.of(Optional.of(user)));
		Throwable exception = Assertions.catchThrowable(() -> {
			Product product1 = productService.findById(id);
		});
		String template = "Resource %s not found for %s with value %s";
		String exceptedMessage = String.format(template, "Product", "Id", id);
		assertThat(exception.getMessage()).isEqualTo(exceptedMessage);
	}
	@Test
	@DisplayName("DELETE service")
	public void WhenDeleteAProductItGetsNoValuePresent() throws Exception {
		// Arrange
		Long id = 1L;
		String name = "PruebaName1";
		String description = "PruebaDesc1";
		String corporation = "PruebaCorp1";
		Double price = 0.00;
		Product product = new Product();
		product.setName(name);
		product.setDescription(description);
		product.setCorporation(corporation);
		product.setPrice(price);
		product.setUserId(1L);
		User user = User.builder().fullName("none").id(product.getUserId()).build();
		when(userClient.fetchById(product.getUserId())).thenReturn(ResponseEntity.of(Optional.of(user)));
		when(productRepository.findById(id)).thenReturn(Optional.of(product));
		productService.deleteById(id);
		when(productRepository.findById(id)).thenReturn(Optional.empty());
		Throwable exception = Assertions.catchThrowable(() -> {
			Product product1 = productService.findById(id);
		});
		String template = "Resource %s not found for %s with value %s";
		String exceptedMessage = String.format(template, "Product", "Id", id);
		assertThat(exception.getMessage()).isEqualTo(exceptedMessage);
	}


	@Test
	@DisplayName("Create service")
	public void WhenCreateAProductGetsOk() throws Exception {
		// Arrange
		Long id = 1L;
		String name = "PruebaName1";
		String description = "PruebaDesc1";
		String corporation = "PruebaCorp1";
		Double price = 0.00;
		Product product = new Product();
		product.setName(name);
		product.setDescription(description);
		product.setCorporation(corporation);
		product.setPrice(price);

		when(productRepository.save(product)).thenReturn(product);
		Product result = productService.save(product);
		assertThat(result.getName()).isEqualTo(product.getName());
	}


	@Test
	@DisplayName("UPDATE service")
	public void WhenUpdateAProductGetsOk() throws Exception {
		// Arrange
		Long id = 1L;
		String name = "PruebaName1";
		String description = "PruebaDesc1";
		String corporation = "PruebaCorp1";
		Double price = 0.00;
		Product product = new Product();
		product.setId(id);
		product.setName(name);
		product.setDescription(description);
		product.setCorporation(corporation);
		product.setPrice(price);
		product.setUserId(1L);
		User user = User.builder().fullName("none").id(product.getUserId()).build();
		when(userClient.fetchById(product.getUserId())).thenReturn(ResponseEntity.of(Optional.of(user)));

		when(productRepository.save(product)).thenReturn(product);


		// Arrange
		Long id1 = 1L;
		String name1 = "PruebaName1";
		String description1 = "PruebaDesc1";
		String corporation1 = "PruebaCorp1";
		Double price1 = 0.00;
		Product product1 = new Product();
		product1.setName(name1);
		product1.setDescription(description1);
		product1.setCorporation(corporation1);
		product1.setPrice(price1);
		product1.setUserId(1L);
		User user1 = User.builder().fullName("none").id(product1.getUserId()).build();
		when(userClient.fetchById(product1.getUserId())).thenReturn(ResponseEntity.of(Optional.of(user1)));
		when(productRepository.save(product)).thenReturn(product1);
		when(productRepository.findById(product.getId())).thenReturn(Optional.of(product1));
		assertThat(productService.findById(id).getName()).isEqualTo(product1.getName());

	}
}