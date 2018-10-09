package com.capgemini.productapp;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.capgemini.productapp.entity.Product;
import com.capgemini.productapp.exception.ProductNotFoundException;
import com.capgemini.productapp.repository.ProductRepository;
import com.capgemini.productapp.service.impl.ProductServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

	private MockMvc mockMvc;
	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private ProductServiceImpl productServiceImpl;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(productServiceImpl).build();
	}

	@Test
	public void testdeleteproduct() {
		Product product = new Product(1234, "lenovo", "laptop", 2340);
		productServiceImpl.deleteProduct(product);

	}

	@Test
	public void testAddProduct() {
		Product product = new Product(12345, "Lenovo", "computer", 120.0);
		when(productRepository.save(product)).thenReturn(product);
		assertEquals(productServiceImpl.addProduct(product), product);
	}

	@Test
	public void testFindProductById() throws ProductNotFoundException {
		Product product = new Product(12345, "Lenovo", "computer", 120.0);
		Optional<Product> product1 = Optional.of(product);
		when(productRepository.findById(12345)).thenReturn(product1);
		assertEquals(productServiceImpl.findProductById(12345), product);
	}

	@Test
	public void testUpdateProduct() throws ProductNotFoundException {
		Product product = new Product(12345, "dell", "computer", 120.0);
		Product product2 = new Product(12345, "Lenovo", "computer", 120.0);
		when(productRepository.save(product)).thenReturn(product2);
		assertEquals(productServiceImpl.updateProduct(product), product2);
	}

}
