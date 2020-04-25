package cn.com.config;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

/**
 * @author LiDaDa
 *
 * 注解开启 swagger2 功能
 * 注解标示,这是一个配置类,@Configuation注解包含了@Component注解
 * 可以不用在使用@Component注解标记这是个bean了,
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {
	
    private String pathMapping;

    @Bean
    public Docket restfulApi() {
        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("RestfulApi")
//                .genericModelSubstitutes(ResponseEntity.class)
//                .useDefaultResponseMessages(true)
//                .forCodeGeneration(false)
                // base，最终调用接口后会和paths拼接在一起
//                .pathMapping(pathMapping)
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("cn.com.controller.rest"))
                .build()
                .apiInfo(apiInfo());
    }
    
    /**
     * 设置过滤规则
     * 这里的过滤规则支持正则匹配
     * @return
     */
    /*private Predicate<String> doFilteringRules() {
        return or(
                regex("/wap.*"),
                regex("/app.*")
        );
    }
*/

    /**
     * 构建 api文档的详细信息函数
     *
     * @return
     */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				//页面标题
				.title("品功装饰系统")
				//版本号
				.version("1.0")
//                .termsOfServiceUrl()
				//描述
				.description("品功装饰系统API")
				.build();
	}

}
