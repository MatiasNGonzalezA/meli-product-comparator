package com.challenge.meli.product_comparator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("file")
@TestPropertySource(properties = {
		"app.data.source=classpath:products.json"
})
class ProductComparatorApplicationTests {
	@Test void contextLoads() { /* TODO document why this method is empty */ }
}