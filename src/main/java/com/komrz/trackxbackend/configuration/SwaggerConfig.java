package com.komrz.trackxbackend.configuration;

import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.google.common.collect.Lists;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";
    
    @Bean
    public Docket postsApi(SwaggerConfigProperties swaggerConfigProperties) {
    	
    	Docket docket = new Docket(DocumentationType.SWAGGER_2)
    			.apiInfo(apiInfo(swaggerConfigProperties))
    			.pathMapping("/")
    			.apiInfo(ApiInfo.DEFAULT)
                .forCodeGeneration(true)
                .genericModelSubstitutes(ResponseEntity.class)
                .ignoredParameterTypes(Pageable.class)
                .ignoredParameterTypes(java.sql.Date.class)
                .directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
                .directModelSubstitute(java.time.LocalDateTime.class, Date.class)
                .securityContexts(Lists.newArrayList(securityContext()))
                .securitySchemes(Lists.newArrayList(apiKey()))
                .useDefaultResponseMessages(false);
    	
    	return docket;
    			
    			
    	
//    	Docket docket= new Docket(DocumentationType.SWAGGER_2)
//                .alternateTypeRules( AlternateTypeRules.newRule(
//                        typeResolver.resolve(Collection.class, Instant.class),
//                        typeResolver.resolve(Collection.class, Date.class), Ordered.HIGHEST_PRECEDENCE))
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.test"))
//                .paths(PathSelectors.any())
//                .build();
//        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo(swaggerConfigProperties)).enable(Boolean.valueOf(swaggerConfigProperties.getEnabled())).select().apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any()).build().pathMapping("/").directModelSubstitute(LocalDate.class, String.class)
//                .genericModelSubstitutes(ResponseEntity.class).useDefaultResponseMessages(Boolean.valueOf(swaggerConfigProperties.getUseDefaultResponseMessages()))
//                .enableUrlTemplating(Boolean.valueOf(swaggerConfigProperties.getEnableUrlTemplating()));
//                .alternateTypeRules(AlternateTypeRules.newRule(Collection.class, WildcardType.class));
    }
//    @Bean
//    UiConfiguration uiConfig(SwaggerConfigProperties swaggerConfigProperties) {
//        return UiConfigurationBuilder.builder().deepLinking(Boolean.valueOf(swaggerConfigProperties.getDeepLinking())).displayOperationId(Boolean.valueOf(swaggerConfigProperties.getDisplayOperationId()))
//                .defaultModelsExpandDepth(Integer.valueOf(swaggerConfigProperties.getDefaultModelsExpandDepth())).defaultModelExpandDepth(Integer.valueOf(swaggerConfigProperties.getDefaultModelExpandDepth()))
//                .defaultModelRendering(ModelRendering.EXAMPLE).displayRequestDuration(Boolean.valueOf(swaggerConfigProperties.getDisplayRequestDuration())).docExpansion(DocExpansion.NONE)
//                .filter(Boolean.valueOf(swaggerConfigProperties.getFilter())).maxDisplayedTags(Integer.valueOf(swaggerConfigProperties.getMaxDisplayedTags())).operationsSorter(OperationsSorter.ALPHA)
//                .showExtensions(Boolean.valueOf(swaggerConfigProperties.getShowExtensions())).tagsSorter(TagsSorter.ALPHA)
//                .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS).validatorUrl(null).build();
//    }
    private ApiInfo apiInfo(SwaggerConfigProperties swaggerConfigProperties) {
        return new ApiInfoBuilder().title(swaggerConfigProperties.getTitle()).description(swaggerConfigProperties.getDescription())
                                   .version(swaggerConfigProperties.getApiVersion()).build();
    }
    
    private ApiKey apiKey() {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }
    
    private SecurityContext securityContext() {
        return SecurityContext.builder()
            .securityReferences(defaultAuth())
            .forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))
            .build();
    }
    
    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
            = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(
            new SecurityReference("JWT", authorizationScopes));
    }
}
