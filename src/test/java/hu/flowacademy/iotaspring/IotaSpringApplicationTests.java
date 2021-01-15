package hu.flowacademy.iotaspring;

import hu.flowacademy.iotaspring.exchange.ExchangeData;
import hu.flowacademy.iotaspring.exchange.ExchangeJpaRepository;
import hu.flowacademy.iotaspring.exchange.ExchangeRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class IotaSpringApplicationTests {

	@Autowired
	private ExchangeJpaRepository exchangeJpaRepository;

	@Autowired
	private MockMvc mvc;

	@Test
	void contextLoads() {
		exchangeJpaRepository.save(ExchangeData.builder()
				.id(UUID.randomUUID().toString())
				.to("USD").from("HUF").result(BigDecimal.valueOf(29400)).amount(BigDecimal.valueOf(100))
				.build());
		List<ExchangeData> all = exchangeJpaRepository.findAll();
		log.info("data is {}", all);
	}

	@Test
	void testApi() throws Exception {
		mvc.perform(
				get("/api/exchange")
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk());
	}

}
