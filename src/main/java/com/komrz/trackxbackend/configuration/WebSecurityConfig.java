package com.komrz.trackxbackend.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.BeanIds;

import com.komrz.trackxbackend.security.JwtAuthenticationEntryPoint;
import com.komrz.trackxbackend.security.JwtAuthenticationFilter;
import com.komrz.trackxbackend.service.CustomUserDetailsService;

/**
 * @author souravbhattacharjee
 * @author shivang gupta
 *
 */
@Configuration
// This is the primary spring security annotation that is used to enable web security in a project.
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	CustomUserDetailsService customUserDetailsService;

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(15);
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
//		http.csrf().disable() .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
		http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers("/"
//                "/favicon.ico",
//                "/**/*.png",
//                "/**/*.gif",
//                "/**/*.svg",
//                "/**/*.jpg",
//                "/**/*.html",
//                "/**/*.css",
//                "/**/*.js"
				).permitAll().antMatchers("/swagger-resources/**").permitAll()
				.antMatchers("/swagger-ui.html").permitAll()
				.antMatchers("/webjars/**").permitAll()
				.antMatchers("/auth/**").permitAll()
				.antMatchers("/auth/signin").permitAll()
				.antMatchers("/tenant/new").permitAll()
				.antMatchers("/industrytype").permitAll()
				.antMatchers("/prefcurr").permitAll()
				.antMatchers("/contract/format").permitAll()
				.antMatchers("/contract/type").permitAll()
				.antMatchers("/contract/status").permitAll()
				.antMatchers("/contract/paymenttype").permitAll()
				.antMatchers("/vendor/status").permitAll()
				.antMatchers("/vendor/type").permitAll()
				.antMatchers("/event/type").permitAll()
				.antMatchers("/user/invite/**").permitAll()
				.antMatchers("/user/new/**").permitAll()
				.antMatchers("/user/forgot").permitAll()
				.antMatchers("/user/forgot/info").permitAll()
				.antMatchers("/user/forgot/change").permitAll()
				
				.antMatchers("/api/v1/auth/**").permitAll()
				.antMatchers("/v2/api-docs").permitAll()
//            .antMatchers("/api/user/checkUsernameAvailability", "/api/user/checkEmailAvailability")
//                .permitAll()
				.antMatchers(HttpMethod.GET, "/api/v1/portfolio/**", "/api/v1/contractCategory/**").permitAll()
				.anyRequest().authenticated();

		// Add our custom JWT security filter
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

//        http.authorizeRequests()
//            .antMatchers("/api/**").permitAll()
//            //.antMatchers("/api/**").hasAnyAuthority("reader", "author", "manager", "admin")
//            .antMatchers("/contract/api/**").hasAnyAuthority("reader", "author", "manager", "admin")
//            .antMatchers("**/new**").hasAnyAuthority("admin", "author", "manager")
//            .antMatchers("**/edit/**").hasAnyAuthority("admin", "author", "manager")
//            .antMatchers("**/delete/**").hasAuthority("admin")
//            .anyRequest().authenticated()
//            .and()
//            .formLogin().permitAll()
//            .and()
//            .logout().permitAll()
//            .and()
//            .exceptionHandling().accessDeniedPage("/403")
//            ;
//        http.csrf().disable();
	}

}
