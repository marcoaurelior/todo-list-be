import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.servers.Server
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        val server = Server().url("https://todo-list-be-production-5c1f.up.railway.app")
        return OpenAPI()
            .addServersItem(server)
            .info(
                Info()
                    .title("Todo List API")
                    .version("1.0.0")
                    .description("Documentação da API para gerenciamento de tarefas")
            )
    }

    @Bean
    fun publicApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("tasks")
            .pathsToMatch("/tasks/**")
            .build()
    }
}