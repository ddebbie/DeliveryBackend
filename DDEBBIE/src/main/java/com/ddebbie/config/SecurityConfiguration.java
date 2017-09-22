package com.ddebbie.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

import com.ddebbie.cache.CacheManager;
import com.ddebbie.handler.UserHandler;

/**
 * @author Ram
 * 13-Sep-2017
 */
@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired
	UserHandler userHandler;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		System.out.println("configureGlobal");
		auth.userDetailsService(userHandler);
		auth.authenticationProvider(authProvider());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userHandler);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Configuration
	@Order(1)
	public static class PublicSecurityConfiguration extends WebSecurityConfigurerAdapter {

		@Autowired
		CacheManager cacheManager;

		@Autowired
		MemCachedSessionSecurityContextRespository memCachedSessionSecurityContextRespository;

		public void configure(WebSecurity web) throws Exception {
			System.out.println("In Configure WebSecurity ..");
			// This is here to ensure that the static content (JavaScript, CSS,
			// etc)
			// is accessible from the login page without authentication
			web.ignoring().antMatchers("/resources/**");
			// web.ignoring().antMatchers("/");
			cacheManager.initService();
		}

		@Override
		@Order(110)
		protected void configure(HttpSecurity http) throws Exception {
			System.out.println("In Configure ..");
			 http
             .authorizeRequests()
                 .antMatchers("/resources/**", "/registration/signupUser", "/registration/signupUserConfirm","/registration/signupVerifyCode",
                		 "/user/login","/user/getUserInfoForHeader").permitAll()
                 .anyRequest().authenticated()
                 .and()
             .formLogin()
                 .loginPage("/login")
                 .permitAll()
                 .and()
             .logout()
                 .permitAll();
		http.securityContext().securityContextRepository(memCachedSessionSecurityContextRespository);
			http.addFilterAfter(new AuthenticationFilter(authenticationManager(), memCachedSessionSecurityContextRespository),
					SecurityContextPersistenceFilter.class).csrf().disable();
		}

	}

	@Configuration
	@Order(99)
	public static class LoggedInSecurityConfiguration extends WebSecurityConfigurerAdapter {
		@Autowired
		MemCachedSessionSecurityContextRespository memCachedSessionSecurityContextRespository;

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().anyRequest().authenticated().and().logout().invalidateHttpSession(Boolean.TRUE)
					.logoutUrl("/logout").logoutSuccessUrl("/logoutsuccess").and().csrf().disable();
			http.securityContext().securityContextRepository(memCachedSessionSecurityContextRespository);
			http.addFilterAfter(
					new AuthenticationFilter(authenticationManager(), memCachedSessionSecurityContextRespository),
					SecurityContextPersistenceFilter.class).csrf().disable();
		}
	}

	

 
    
   
}