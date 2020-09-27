package lt.myserver.backend;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lt.myserver.backend.currencyConverter.controllers.CurrencyConverterController;

@SpringBootTest
class BackEndApplicationTests {

	@Autowired
	private CurrencyConverterController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
