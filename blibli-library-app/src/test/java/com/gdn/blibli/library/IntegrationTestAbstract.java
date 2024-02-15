package com.gdn.blibli.library;

import com.gdn.blibli.library.helper.TestHelper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ExtendWith(IntegrationExtension.class)
@AutoConfigureWebTestClient()
@AutoConfigureWireMock(port = 0)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = {"/application-test.properties"},
        properties = {"spring.cloud.consul.enabled=false", "spring.cloud.vault.enabled=false"})
@ActiveProfiles("test")
public abstract class IntegrationTestAbstract extends TestHelper {

}
