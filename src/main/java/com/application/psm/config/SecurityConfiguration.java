package com.application.psm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService getDetailsService() {
		return new CustomUserDetailsServiceImp();
	}

	@Bean
	public DaoAuthenticationProvider getAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(getDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
        .authorizeRequests(authorizeRequests ->
            authorizeRequests
                .requestMatchers("/user/new-user","/user/registration-user","/home","/sector/all-sectors","/job/all-jobs","/js/**", "/css/**", "/img/**").permitAll()
                .requestMatchers("/visitante/**").hasRole("VISITANTE") // Requiere rol USER
                .requestMatchers("/admin**").hasRole("ADMIN") // Requiere rol ADMIN
                .anyRequest().authenticated()
        )
        .formLogin(formLogin ->
            formLogin
                .loginPage("/user/login-user").permitAll()
                .defaultSuccessUrl("/home", true) // Redirige a "/home" después de un inicio de sesión exitoso
        )
        .logout(logout ->
            logout
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/user/login-user?logout").permitAll()
        );
		return http.build();
	}

}

	  
	/*  
	 * @Autowired
	private UserService userService;
	
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }
	
	@Bean
	 public SecurityFilterChain configure(HttpSecurity http) throws Exception {
	    
	    http.csrf().disable()
	      .authorizeHttpRequests().requestMatchers("/registration**", "/js/**", "/css/**", "/img/**").permitAll()
	       .anyRequest().authenticated()
	       .and().formLogin().loginPage("/login").permitAll();
	      
	    return http.build();
	}
	
	
	@Override
	 public SecurityFilterChain configure(HttpSecurity http) throws Exception {
	    
	    http
	      .authorizeHttpRequests((authz) -> {
	        authz
	          .requestMatchers("/registration**", "/js/**", "/css/**", "/img/**").permitAll() 
	          .anyRequest().authenticated();
	      })
	      .formLogin((form) -> form
	        .loginPage("/login")
	        .defaultSuccessUrl("/index",true)
	        .permitAll()
	      )
	      .logout((logout) -> logout
	        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	        .logoutSuccessUrl("/login?logout")  
	      );

	    return http.build();
	}*/

