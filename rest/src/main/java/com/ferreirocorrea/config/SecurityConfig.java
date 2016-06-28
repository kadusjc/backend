package com.ferreirocorrea.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

import com.ferreirocorrea.auth.Role;
import com.ferreirocorrea.auth.service.UserDetailsByAuthenticationServiceWrapper;
import com.ferreirocorrea.auth.service.UserDetailsService;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	private static final String REQUEST_HEADER_PARAM = "login";

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http
		  	.csrf().disable()
		 	.addFilter(requestHeaderAuthenticationFilter())
		 	.authorizeRequests()
		 	.antMatchers("/api/**").hasAuthority(Role.SYS_USER.name())
		 	.anyRequest().fullyAuthenticated();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
	    return super.authenticationManagerBean();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)throws Exception {
		auth.authenticationProvider(preauthAuthProvider());
	}

	@Bean
	public RequestHeaderAuthenticationFilter requestHeaderAuthenticationFilter() {
		RequestHeaderAuthenticationFilter filter = new RequestHeaderAuthenticationFilter();
		filter.setAuthenticationManager(authenticationManager);
		filter.setExceptionIfHeaderMissing(false);
		filter.setPrincipalRequestHeader(REQUEST_HEADER_PARAM);
		return filter;
	}

	@Bean
	public PreAuthenticatedAuthenticationProvider preauthAuthProvider() {

		UserDetailsByAuthenticationServiceWrapper<PreAuthenticatedAuthenticationToken> wrapper = new UserDetailsByAuthenticationServiceWrapper<PreAuthenticatedAuthenticationToken>();
		wrapper.setUserDetailsService(userDetailsService);

		PreAuthenticatedAuthenticationProvider preauthAuthProvider = new PreAuthenticatedAuthenticationProvider();
		preauthAuthProvider.setPreAuthenticatedUserDetailsService(wrapper);
		return preauthAuthProvider;
	}


	@Bean
	@ConfigurationProperties(prefix = "spring.ldap.contextSource")
	public LdapContextSource contextSource() {
		LdapContextSource contextSource = new LdapContextSource();
		return contextSource;
	}

	@Bean
	public LdapTemplate ldapTemplate(ContextSource contextSource) {
		return new LdapTemplate(contextSource);
	}

}
