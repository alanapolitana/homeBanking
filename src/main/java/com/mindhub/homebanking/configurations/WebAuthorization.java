package com.mindhub.homebanking.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Configuration
@EnableWebSecurity
public class WebAuthorization extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/index.html").permitAll()
              /*.antMatchers("/api/clients").hasAuthority("USER");  */
                .anyRequest().denyAll();

        http.formLogin()
                .usernameParameter("name")
                .passwordParameter("pwd")
                .loginPage("/app/login");

        http.logout().logoutUrl("/app/logout");

        // turn off checking for CSRF tokens
        // Desactivar la verificación de tokens CSRF
        http.csrf().disable();

        //disabling frameOptions so h2-console can be accessed
        // Deshabilitar frameOptions para que se pueda acceder a h2-console
        http.headers().frameOptions().disable();

        // if user is not authenticated, just send an authentication failure response
        // Si el usuario no está autenticado, enviar una respuesta de fallo de autenticación
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) ->
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if login is successful, just clear the flags asking for authentication
        // Si el inicio de sesión tiene éxito, simplemente eliminar las marcas que solicitan autenticación
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // if login fails, just send an authentication failure response
        // Si el inicio de sesión falla, enviar una respuesta de fallo de autenticación
        http.formLogin().failureHandler((req, res, exc) ->
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED));


        // if logout is successful, just send a success response
        // Si el cierre de sesión tiene éxito, enviar una respuesta de éxito
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
    }


    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}

