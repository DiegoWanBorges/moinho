package com.twokeys.moinho.config;


import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private Environment env;
	
	@Autowired
	private JwtTokenStore tokenStore;
	
	private static final String[] PUBLIC = { "oauth/token","/h2-console/**" };
	private static final String[] PARAMETER = { "/parameters/**" };
	private static final String[] USER = { "/users/**" };
	private static final String[] GROUP = { "/groups/**" };
	private static final String[] UNITY = { "/units/**" };
	private static final String[] PRODUCT = { "/products/**" };
	private static final String[] SECTOR = { "/sectors/**" };
	private static final String[] EMPLOYEE = { "/employees/**" };
	private static final String[] LABORCOSTTYPE = { "/laborcosttypes/**" };
	private static final String[] OPERATIONALCOSTTYPE = { "/operationalcosttypes/**" };
	private static final String[] OCCURRENCE = { "/occurrences/**" };
	private static final String[] STATUSPALLET = { "/palletstatus/**" };
	private static final String[] FORMULATION = { "/formulations/**","/formulationsitems/**" };
	private static final String[] PRODUCTION = { "/productionorders/**","/productionorderitems/**","/productionordersproduced/**" };
	private static final String[] STOCK = { "/stocks/**" };
	private static final String[] LABOR_PAYMENT = { "/laborpayments/**" };
	private static final String[] OPERATIONAL_PAYMENT = { "/operationalpayments/**" };
	private static final String[] COST_CALCULATION = { "/costcalculations/**" };
	
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		/*H2*/
		if( Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}
		
		http.authorizeRequests()
				.antMatchers(PUBLIC).permitAll()
				.antMatchers(PARAMETER).hasAnyRole("REGISTRATION_PARAMETER", "ADMIN")
				.antMatchers(USER).hasAnyRole("REGISTRATION_PARAMETER", "ADMIN")
				.antMatchers(GROUP).hasAnyRole("REGISTRATION_GROUP", "ADMIN")
				.antMatchers(UNITY).hasAnyRole("REGISTRATION_UNITY", "ADMIN")
				.antMatchers(PRODUCT).hasAnyRole("REGISTRATION_PRODUCT", "ADMIN")
				.antMatchers(SECTOR).hasAnyRole("REGISTRATION_SECTOR", "ADMIN")
				.antMatchers(EMPLOYEE).hasAnyRole("REGISTRATION_EMPLOYEE", "ADMIN")
				.antMatchers(LABORCOSTTYPE).hasAnyRole("REGISTRATION_LABORCOSTTYPE", "ADMIN")
				.antMatchers(OPERATIONALCOSTTYPE).hasAnyRole("REGISTRATION_OPERATIONALCOSTTYPE", "ADMIN")
				.antMatchers(OCCURRENCE).hasAnyRole("REGISTRATION_OCCURRENCE", "ADMIN")
				.antMatchers(STATUSPALLET).hasAnyRole("REGISTRATION_STATUSPALLET", "ADMIN")
				.antMatchers(FORMULATION).hasAnyRole("FORMULATION", "ADMIN")
				.antMatchers(PRODUCTION).hasAnyRole("PRODUCTION", "ADMIN")
				.antMatchers(STOCK).hasAnyRole("STOCK", "ADMIN")
				.antMatchers(LABOR_PAYMENT).hasAnyRole("LABOR_PAYMENT", "ADMIN")
				.antMatchers(OPERATIONAL_PAYMENT).hasAnyRole("OPERATIONAL_PAYMENT", "ADMIN")
				.antMatchers(COST_CALCULATION).hasAnyRole("COST_CALCULATION", "ADMIN")
				.anyRequest().authenticated();
		http.cors().configurationSource(corsConfigurationSource());
	}
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.setAllowedOriginPatterns(Arrays.asList("*"));
		corsConfig.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "PATCH"));
		corsConfig.setAllowCredentials(true);
		corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);
		return source;
	}

	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		FilterRegistrationBean<CorsFilter> bean 
			= new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}
	
}
