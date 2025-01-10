package com.course.springsecuritycourse.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.course.springsecuritycourse.repository.UserRepository;

/*
 * La interfaz AuthenticationManager en el contexto de la seguridad de Spring es una parte fundamental del proceso de autenticación de usuarios en una aplicación. Su función principal es autenticar a los usuarios en función de sus credenciales (como nombre de usuario y contraseña) y decidir si se les permite o no acceder a recursos protegidos.

Para entender cómo funciona AuthenticationManager y cómo se relaciona con AuthenticationService y UsuarioRepository, aquí tienes una descripción general:

- AuthenticationManager: Es una interfaz que define un solo método: authenticate. Su objetivo es tomar una instancia de Authentication, que generalmente es un objeto que contiene las credenciales del usuario (como UsernamePasswordAuthenticationToken), y determinar si es una autenticación válida. La implementación de AuthenticationManager en Spring se encarga de realizar la autenticación, como verificar si las credenciales son correctas y cargar los detalles del usuario autenticado en un objeto Authentication. Puedes personalizar la implementación de AuthenticationManager según tus necesidades, por ejemplo, para utilizar diferentes métodos de autenticación (por contraseña, por token, etc.).

- AuthenticationService: Esta no es una interfaz estándar en Spring Security, pero es común crear un servicio personalizado que interactúe con el AuthenticationManager. Este servicio puede encargarse de recibir las credenciales del usuario, crear un objeto Authentication, invocar el AuthenticationManager para autenticar al usuario y devolver un objeto de usuario autenticado. Esencialmente, el AuthenticationService es una capa intermedia entre la aplicación y el AuthenticationManager.

- UsuarioRepository: El UsuarioRepository es una parte de la capa de persistencia de datos que se utiliza para acceder a la información del usuario almacenada en una base de datos u otro sistema de almacenamiento de datos. El UsuarioRepository generalmente se utiliza para buscar y recuperar información de usuario, como detalles de usuario, roles, contraseñas hash, etc.

El flujo típico de autenticación en una aplicación de Spring Security sería el siguiente:

1- El usuario proporciona sus credenciales (nombre de usuario y contraseña) a través de un formulario de inicio de sesión o mediante algún otro mecanismo de entrada de datos.

2- El AuthenticationService recibe estas credenciales y crea un objeto Authentication (por ejemplo, UsernamePasswordAuthenticationToken) que las encapsula.

3- El AuthenticationService llama al AuthenticationManager para autenticar al usuario utilizando el objeto Authentication creado en el paso anterior.

4- El AuthenticationManager realiza la autenticación, que puede implicar la verificación de las credenciales del usuario y, en caso de éxito, la carga de los detalles del usuario autenticado en un nuevo objeto Authentication.

5- El AuthenticationService recibe el objeto Authentication autenticado y, si es necesario, carga información adicional sobre el usuario desde el UsuarioRepository.

6- La aplicación puede utilizar la información del usuario autenticado para tomar decisiones sobre el acceso a recursos protegidos.


 */

@Configuration
public class ApplicationConfig {
    
    @Autowired
    private UserRepository userRepository;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                                         .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
