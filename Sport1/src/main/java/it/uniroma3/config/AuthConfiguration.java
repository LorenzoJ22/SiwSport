package it.uniroma3.config;

import static it.uniroma3.model.Credentials.ADMIN_ROLE;
import static it.uniroma3.model.Credentials.DEFAULT_ROLE;

import javax.sql.DataSource;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;



@Configuration
@EnableWebSecurity

public class AuthConfiguration {
	@Autowired

  private DataSource dataSource;
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.jdbcAuthentication()
    .dataSource(dataSource)
    .authoritiesByUsernameQuery("SELECT username, role from credentials WHERE username=?")
    .usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?");
    
  }

    @Bean
    
    PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }
    @Bean
    protected SecurityFilterChain configure(final HttpSecurity httpSecurity) 
    		throws Exception{
    	 httpSecurity
         .csrf().disable()  // Disabilita CSRF
         .cors().disable()  // Disabilita CORS
         .authorizeHttpRequests()
             .requestMatchers(HttpMethod.GET, "/", "/index", "/Squadre", "/Presidente/{id}","/Presidenti", "/Squadra/{id}", "/register","/SceltaRegistrazione","/registerAdmin", "/css/**", "/images/**", "favicon.ico").permitAll()
             .requestMatchers(HttpMethod.POST, "/register", "/login","/SceltaRegistrazione","/registerAdmin").permitAll()
             .requestMatchers(HttpMethod.GET, "/admin/**").hasAnyAuthority(ADMIN_ROLE)
             .requestMatchers(HttpMethod.POST, "/admin/**").hasAnyAuthority(ADMIN_ROLE)
             .requestMatchers(HttpMethod.GET, "/pres/**").hasAnyAuthority(DEFAULT_ROLE)
             .requestMatchers(HttpMethod.POST, "/pres/**").hasAnyAuthority(DEFAULT_ROLE)
             .anyRequest().authenticated()
             .and()
         .formLogin()
             .loginPage("/login")
             .permitAll()
             .defaultSuccessUrl("/success", true)
             .failureUrl("/login?error=true")
             .and()
         .logout()
             .logoutUrl("/logout")
             .logoutSuccessUrl("/")
             .invalidateHttpSession(true)
             .deleteCookies("JSESSIONID")
             .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
             .clearAuthentication(true)
             .permitAll();

     return httpSecurity.build();
    	
    }   
   
}


