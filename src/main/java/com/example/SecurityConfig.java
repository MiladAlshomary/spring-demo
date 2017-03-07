package com.example;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.client.RestOperations;

@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");
    }
}

@Configuration
@EnableOAuth2Client
class ResourceConfiguration {

	private String accessTokenUri = "https://graph.facebook.com/oauth/access_token";

	private String userAuthorizationUri = "https://www.facebook.com/dialog/oauth";

	
	@Bean
	public FacebookController facebookController(@Qualifier("facebookRestTemplate")
	RestOperations facebookRestTemplate) {
		FacebookController controller = new FacebookController();
		controller.setFacebookRestTemplate(facebookRestTemplate);
		return controller;
	}
	
	@Bean
	public OAuth2ProtectedResourceDetails facebook() {
		AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
		details.setId("facebook");
		details.setClientId("1837356773205103");
		details.setClientSecret("e0c6ad5286a8bd197d3c2363506f1cb2");
		details.setAccessTokenUri("https://graph.facebook.com/oauth/access_token");
		details.setUserAuthorizationUri("https://www.facebook.com/dialog/oauth");
		details.setTokenName("oauth_token");
		details.setAuthenticationScheme(AuthenticationScheme.query);
		details.setClientAuthenticationScheme(AuthenticationScheme.form);
		return details;
	}
	
	@Bean
	public OAuth2RestTemplate facebookRestTemplate(OAuth2ClientContext clientContext) {
		OAuth2RestTemplate template = new OAuth2RestTemplate(facebook(), clientContext);
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON,
				MediaType.valueOf("text/javascript")));
		template.setMessageConverters(Arrays.<HttpMessageConverter<?>> asList(converter));
		return template;
	}

}