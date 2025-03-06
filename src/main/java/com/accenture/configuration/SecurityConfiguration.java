package com.accenture.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration//Spring va regarder dans les classes @Configuration, chercher les @Bean, et créer un instance dans le conteneur de spring à utiliser
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)//????
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(auth->
                    auth
                            .requestMatchers("/v3/api-docs/**",
                                    "/swagger-ui/**",
                                    "/swagger-ui/html"
                            ).permitAll()
                            .requestMatchers(HttpMethod.GET, "/vehicules/rechercher").permitAll()
                            .requestMatchers(HttpMethod.POST, "/clients","/admins").permitAll()


                            .requestMatchers(HttpMethod.GET, "/clients/infos").hasAnyRole("ADMIN","CLIENT")
                            .requestMatchers(HttpMethod.PATCH, "/clients").hasAnyRole("ADMIN","CLIENT")
                            .requestMatchers(HttpMethod.DELETE, "/clients").hasAnyRole("ADMIN","CLIENT")

                            .requestMatchers(HttpMethod.GET, "/locations/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PATCH, "/locations/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/locations/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.POST, "/locations/**").hasAnyRole("ADMIN","CLIENT")


                            .requestMatchers(HttpMethod.GET, "/admins/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PATCH, "/admins/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/admins/**").hasRole("ADMIN")
                            .requestMatchers( "/motos/**","/voitures/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/clients", "/vehicules/filtrer") .hasRole("ADMIN")
                            .anyRequest().authenticated()
                );
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    UserDetailsManager userDetailsManager(DataSource dataSource){//dataSource est postgre ici, c'est le seul dans le projet, spring va le trouver
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery("select email, password, 1 from utilisateur where email = ?");//1 est l'utilisateur valid==true : il a le droit d'utiliser le site
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select email, role from utilisateur where email = ?");
        return jdbcUserDetailsManager;
    }

    @Bean
    public OpenAPI apiConfiguration() {
        return new OpenAPI()
                .info(new Info()
                        .title("Location de Véhicule")
                        .description("Api pour l'application Location de Véhicule")
                        .version("0.0.1")
                        .contact(new Contact()
                                .name("Tianhong Huang").
                                email("tianhong.huang@accenture.com"))
                );
    }
}
