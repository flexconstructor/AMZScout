package ru.flexconstructor.AMZScout;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static junit.framework.TestCase.assertNull;

/**
 * Defines integration test for the test task.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AmzScoutApplicationTests {

	/**
	 * Test web client instance.
	 */
	@Autowired
	private WebTestClient webTestClient;

	/**
	 * Tests API method calling.
	 * Checks assertions:
	 *   1. First 50 calls returns status 200;
	 *   2. 51-th call after that returns status 502;
	 *   3. Next call returns status 200 after 1 minutes timeout.
	 */
	@Test
	public void call50Test() {
		for (int i = 0; i< 50; i++) {
			this.webTestClient.get().uri("/my_api")
					.accept(MediaType.TEXT_PLAIN)
					.exchange().expectStatus()
					.isOk()
					.expectBody().isEmpty();
		}

		this.webTestClient.get().uri("/my_api")
				.accept(MediaType.TEXT_PLAIN)
				.exchange().expectStatus()
				.isEqualTo(502)
				.expectBody().isEmpty();
		try {
			Thread.sleep(1000 * 60);
		} catch (Exception ex){
			assertNull(ex);
		}

		this.webTestClient.get().uri("/my_api")
				.accept(MediaType.TEXT_PLAIN)
				.exchange().expectStatus()
				.isOk()
				.expectBody().isEmpty();
	}

}
