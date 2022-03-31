package br.com.mcp.config.swagger;


import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import br.com.mcp.modelo.Usuario;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfigurations {
	
	@Bean
	public Docket aulaAPI() {
		  return new Docket(DocumentationType.SWAGGER_2) .select()
		  .apis(RequestHandlerSelectors.basePackage("br.com.mcp"))
		  .paths(PathSelectors.ant("/**"))
		  .build().apiInfo(getInfo())
		  .ignoredParameterTypes(Usuario.class)
		  .globalOperationParameters(
                  Arrays.asList(
                          new ParameterBuilder()
                              .name("Authorization")
                              .description("Header para Token JWT")
                              .modelRef(new ModelRef("string"))
                              .parameterType("header")
                              .required(false)
                              .build()));
		 
	}
	
	private ApiInfo getInfo() {
		return new ApiInfoBuilder().title("API Documentada").description("documentação técnica").license("MCP").build();
	}

}
