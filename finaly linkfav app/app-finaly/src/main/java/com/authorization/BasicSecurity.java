package com.authorization;

import com.authorization.repository.ProfilesRepository;
import com.authorization.service.ProfileService;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableJpaRepositories(basePackageClasses = ProfilesRepository.class)
@Configuration
public class BasicSecurity extends WebSecurityConfigurerAdapter {
    private ProfileService profileService;
    private PasswordEncoder passwordEncoder;

    public BasicSecurity(ProfileService profileService, PasswordEncoder passwordEncoder) {
        this.profileService = profileService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable().csrf().disable();
        http
                .authorizeRequests() //włączamy filtr autoryzacyjny
                .antMatchers("/login**", "/register**").permitAll() //wyjątki filtra autoryzacyjnego
                .and() //spójnik łączący Builder obiektu http
                .formLogin() //włączamy stronę logowania
                .loginPage("/login") //ustawiamy adres do strony logowania
                .loginProcessingUrl("/signin") //ustaiwamy adres, pod który formularz html wyśle dane
                .usernameParameter("username") //parametr formularza (input name= ...)
                .passwordParameter("password") //parametr formularza (input name= ...)
                .successHandler((req, res, auth) -> { //przechwytywacz momentu poprawnego zalowania
                    for (GrantedAuthority authority : auth.getAuthorities()) {
                        System.out.println(authority.getAuthority());
                    }
                    System.out.println(auth.getName());
                    System.out.println(req.getRequestURI());
                    System.out.println(auth.toString());
                    res.sendRedirect("/"); //przekirowanie na podany url
                })
                //.successForwardUrl("/")  //pominięty, gdy istnieje konf. successHandler() w obiekcie http
                .failureUrl("/login?error='error!!!'") //konf. w przypadku błędnego logowania
                .permitAll()
                .and()
                .logout() //konf. zachowania podczas wylogowania
                .logoutUrl("/logout") //url do wylogowania
                .logoutSuccessHandler((req, res, auth) -> { //przechwytywacz błędnego logowania
                    req.getSession().setAttribute("message", "you are logged out!");
                    res.sendRedirect("/login");
                })
                .permitAll(); //wyłącz z filtrowania ustawienia logout()

        http.headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(profileService)
                .passwordEncoder(passwordEncoder);
    }
}
