package hu.flowacademy.iotaspring;

import hu.flowacademy.iotaspring.exchange.ExchangeData;
import hu.flowacademy.iotaspring.exchange.ExchangeJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class IotaSpringApplicationTests {

	@Autowired
	private ExchangeJpaRepository exchangeJpaRepository;

	@Autowired
	private MockMvc mvc;

	@Test
	void testSave() {
		exchangeJpaRepository.save(ExchangeData.builder()
				.id(UUID.randomUUID().toString())
				.to("USD").from("HUF").result(BigDecimal.valueOf(29400)).amount(BigDecimal.valueOf(100))
				.build());

		Assertions.assertEquals(1, exchangeJpaRepository.findAll().size());
	}

	@Test
	void testApi() throws Exception {
		mvc.perform(
				get("/api/exchange")
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk());
	}

}
