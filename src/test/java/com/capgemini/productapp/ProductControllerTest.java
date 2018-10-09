package com.capgemini.productapp;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.capgemini.productapp.controller.ProductController;
import com.capgemini.productapp.entity.Product;
import com.capgemini.productapp.service.ProductService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductControllerTest {

	private MockMvc mockMvc;
	@Mock
	private ProductService productService;

	@InjectMocks
	private ProductController productController;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
	}

	@Test
	public void testaddProduct() throws Exception {
		when(productService.addProduct(Mockito.isA(Product.class)))
				.thenReturn(new Product(1234, "lenovo", "laptop", 2340));

		String content = "{\r\n" + "	\"productId\":1234,\r\n" + "	\"productName\":\"lenovo\",\r\n"
				+ "	\"productCategory\":\"laptop\",\r\n" + "	\"productPrice\":2340\r\n" + "}";

		mockMvc.perform(post("/product").contentType(MediaType.APPLICATION_JSON).content(content)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.productId").exists())
				.andExpect(jsonPath("$.productName").exists())
				.andExpect(jsonPath("$.productCategory").exists())
				.andExpect(jsonPath("$.productPrice").exists())
				.andExpect(jsonPath("$.productId").value(1234))
				.andExpect(jsonPath("$.productName").value("lenovo"))
				.andExpect(jsonPath("$.productCategory").value("laptop"))
				.andExpect(jsonPath("$.productPrice").value(2340))
				.andDo(print());

	}

	@Test
	public void testfindproductbyId() throws Exception {
		when(productService.findProductById(1234)).thenReturn(new Product(1234, "xxx", "yyy", 234));
		mockMvc.perform(get("/products/1234").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
				.andExpect(jsonPath("$.productId").exists())
				.andExpect(jsonPath("$.productName").exists())
				.andExpect(jsonPath("$.productCategory").exists())
				.andExpect(jsonPath("$.productPrice").exists())
				.andExpect(jsonPath("$.productId").value(1234))
				.andExpect(jsonPath("$.productName").value("xxx"))
				.andExpect(jsonPath("$.productCategory").value("yyy"))
				.andExpect(jsonPath("$.productPrice").value(234));

	}

	@Test
	public void testupdateProduct() throws Exception {

		when(productService.updateProduct(Mockito.isA(Product.class))).thenReturn(new Product(1234, "xxx", "yyy", 234));
		when(productService.findProductById(1234)).thenReturn(new Product(1234, "lenovo", "laptop", 2340));
		String content = "{\r\n" + "	\"productId\":1234,\r\n" + "	\"productName\":\"xxx\",\r\n"
				+ "	\"productCategory\":\"yyy\",\r\n" + "	\"productPrice\":234\r\n" + "}";

		mockMvc.perform(put       ("/product").contentType(MediaType.APPLICATION_JSON).content(content)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.productId").exists())
				.andExpect(jsonPath("$.productName").exists())
				.andExpect(jsonPath("$.productCategory").exists())
				.andExpect(jsonPath("$.productPrice").exists())
				.andExpect(jsonPath("$.productId").value(1234))
				.andExpect(jsonPath("$.productName").value("xxx"))
				.andExpect(jsonPath("$.productCategory").value("yyy"))
				.andExpect(jsonPath("$.productPrice").value(234))
				.andDo(print());

	}

	@Test
	public void testDelete() throws Exception {
		when(productService.findProductById(1234)).thenReturn(new Product(1234, "xxx", "yyy", 2340));
		mockMvc.perform(delete("/products/1234")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andDo(print());
	}
}
