package upc.edu.pe.inventoryservice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import upc.edu.pe.inventoryservice.entities.Product;
import upc.edu.pe.inventoryservice.repositories.CategoryRepository;
import upc.edu.pe.inventoryservice.repositories.ProductRepository;
import upc.edu.pe.inventoryservice.services.CategoryService;
import upc.edu.pe.inventoryservice.services.ProductService;
import upc.edu.pe.inventoryservice.services.impl.CategoryServiceImpl;
import upc.edu.pe.inventoryservice.services.impl.ProductServiceImpl;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class InventoryServiceApplicationTests {


	@MockBean
	private ProductRepository productRepository;

	@Autowired
	private ProductService productService;

	@TestConfiguration
	static class ProductImplTestConfiguration{
		@Bean
		public ProductService productServiceService(){
			return new ProductServiceImpl();
		}
	}

	@MockBean
	private CategoryRepository categoryRepository;
	@Autowired
	private CategoryService categoryService;
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
		when(productRepository.findById(id)).thenReturn(Optional.of(product));

		// Act
		Product foundProduct = productService.findById(id);

		// Assert
		assertThat(foundProduct.getName()).isEqualTo(name);
		assertThat(foundProduct.getDescription()).isEqualTo(description);
		assertThat(foundProduct.getCorporation()).isEqualTo(corporation);
		assertThat(foundProduct.getPrice()).isEqualTo(price);
	}

}
