package edu.mum.coffee.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	DataSource dataSource;
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {

		
		http.formLogin().loginPage("/login").usernameParameter("userId").passwordParameter("password");
		http.formLogin().defaultSuccessUrl("/index").failureUrl("/login?error");
		http.logout().logoutSuccessUrl("/login?logout");
		http.exceptionHandling().accessDeniedPage("/login?accessDenied");
		http.authorizeRequests().antMatchers("/").permitAll();
		http.authorizeRequests().antMatchers("/person/new").anonymous();
		http.authorizeRequests().antMatchers("/person/create").permitAll();

		
		http
    	.csrf()
    		.disable()
        .authorizeRequests()
        	.antMatchers(HttpMethod.GET, "/ws/**").permitAll()	
        	.antMatchers(HttpMethod.POST, "/ws/**").permitAll()
        	.antMatchers(HttpMethod.PUT, "/ws/**").permitAll() 
        	.antMatchers(HttpMethod.DELETE, "/ws/**").permitAll()
        	.antMatchers(HttpMethod.GET, "/images/**").permitAll();
		
		http
        .authorizeRequests()
        	.antMatchers("/product/**").access("hasRole('ROLE_ADMIN')")
    		.antMatchers("/order/orderNew*").access("hasRole('ROLE_USER')")
    		.antMatchers("/order/all*").access("hasRole('ROLE_ADMIN')")
    		.antMatchers("/person/all").access("hasRole('ROLE_ADMIN')")
    		.antMatchers("/person/**").access("hasRole('ROLE_USER')")
            .antMatchers("/static/**", "/", "/home", "/index", "/css/**", "/fonts/**", "/js/**", "/images/**", "**/favicon.ico").permitAll()
            .antMatchers("/static/**", "/css/**", "/fonts/**", "/js/**", "/images/**", "**/favicon.ico").anonymous()
            ;
    }

	@Override
	public void configure(WebSecurity web) throws Exception {
	    web
	       .ignoring()
	       .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	}
	
		
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {

	  auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery(
			"select email,password, enable from person where email=?")
		.authoritiesByUsernameQuery(
			"select name, role from role where name=?");
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("super").password("pw").roles("ADMIN");
	}
}