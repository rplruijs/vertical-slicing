package improve.neso.vertical_slicing

import org.junit.jupiter.api.Test
import org.springframework.boot.devtools.restart.RestartScope
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

@SpringBootTest
@Import(TestContainersConfiguration::class)
class VerticalSlicingApplicationTests {
	@Test
	fun contextLoads() {
	}
}

@TestConfiguration(proxyBeanMethods = false)
class TestContainersConfiguration {
	@Bean
	@ServiceConnection
	@RestartScope
	fun postgresContainer(): PostgreSQLContainer<*> {
		return PostgreSQLContainer(DockerImageName.parse("postgres:latest"))
	}
}
