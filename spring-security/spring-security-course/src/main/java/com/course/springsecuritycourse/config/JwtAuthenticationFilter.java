package com.course.springsecuritycourse.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.course.springsecuritycourse.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    /*
     * Utilizamos OncePerRequestFilter para asegurarnos que este filtro se ejecute solo una vez 
     * por cada peticion http y no varias veces por error.
     * 
     * Por otro lado, el siguiente metodo es el que va a manejar todo lo relacionado a los filtros 
     * para validar el JWT
     * 
     * 
     * 
     * 
     * Paso a Paso
        1) Obtener el Token de la Solicitud:

        final String token = getTokenFromRequest(request);

        Aquí se llama a un método getTokenFromRequest(request) que se encarga de extraer el token JWT de la solicitud HTTP. Este método podría buscar el token en los encabezados de la solicitud, en los parámetros de la URL, o en las cookies, dependiendo de cómo esté implementado.
        
        2) Verificar si el Token es Nulo:

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }
        Si el token es null, significa que no se proporcionó un token en la solicitud. En este caso, el filtro simplemente pasa la solicitud y la respuesta a la siguiente cadena de filtros (filterChain.doFilter(request, response)) y termina la ejecución del método (return).
        
        3) Obtener el Nombre de Usuario del Token:

        final String username = jwtService.getUsernameFromToken(token);
        Si el token no es nulo, se extrae el nombre de usuario del token utilizando el servicio jwtService. Este servicio probablemente decodifica el token y obtiene el nombre de usuario que se almacenó en él.
        
        4) Verificar el Nombre de Usuario y la Autenticación:

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        Aquí se verifica que el nombre de usuario no sea null y que no haya una autenticación existente en el contexto de seguridad (SecurityContextHolder). Esto es importante para evitar que un usuario ya autenticado vuelva a ser autenticado.
        
        5) Cargar los Detalles del Usuario:

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        Si el nombre de usuario es válido y no hay una autenticación existente, se carga el objeto User Details correspondiente al nombre de usuario utilizando el userDetailsService. Este objeto contiene información sobre el usuario, como su contraseña y sus roles.

        Este metodo se ejecuta a traves del bean establecido en el archivo ApplicationConfig de UserDetailsService
        
        6) Validar el Token:

        if (jwtService.isTokenValid(token, userDetails)) {
        Se valida el token utilizando el método isTokenValid del jwtService. Este método probablemente verifica si el token no ha expirado y si coincide con los detalles del usuario.
        
        7) Crear un Token de Autenticación:

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities());
        Si el token es válido, se crea un objeto UsernamePasswordAuthenticationToken, que es una implementación de Authentication que Spring Security utiliza para representar la autenticación del usuario. Este objeto incluye los detalles del usuario y sus autoridades (roles).
        
        8) Establecer Detalles de la Solicitud:

        usernamePasswordAuthenticationToken.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request));
        Se establecen los detalles de la solicitud en el token de autenticación. Esto puede incluir información adicional sobre la solicitud, como la dirección IP del cliente.
        
        9) Establecer la Autenticación en el Contexto de Seguridad:

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        Por qué es necesario esto ultimo?

        - Duración de la Autenticación
        
        Contexto de Seguridad por Solicitud:

        El SecurityContextHolder almacena el contexto de seguridad en un ThreadLocal, lo que significa que el contexto es específico para el hilo que maneja la solicitud actual. Esto permite que diferentes solicitudes sean manejadas de manera independiente, cada una con su propio contexto de seguridad.
        
        Alcance de la Solicitud:

        Cuando se establece la autenticación en el SecurityContextHolder durante el procesamiento de una solicitud, esa autenticación está disponible para cualquier parte de la aplicación que se ejecute en el mismo hilo durante esa solicitud. Esto incluye filtros, controladores y servicios que se invocan como parte de la misma cadena de procesamiento.
        
        - ¿Qué Sucede Después de Enviar la Respuesta?
        
        Fin de la Solicitud:

        Una vez que se envía la respuesta al cliente y se completa el ciclo de vida de la solicitud, el contexto de seguridad se limpia. Esto significa que el SecurityContextHolder se restablece y la información de autenticación se elimina.
        
        Nueva Solicitud:

        En la siguiente solicitud que el mismo cliente envíe (por ejemplo, una nueva llamada a la API), se iniciará un nuevo ciclo de vida de solicitud. Si el cliente envía un token JWT válido en esta nueva solicitud, el filtro de seguridad volverá a extraer el token, validarlo y establecer la autenticación en el SecurityContextHolder nuevamente.
        
        Sin Estado:

        Este enfoque es parte del diseño sin estado de las aplicaciones web modernas. En lugar de mantener el estado de la sesión en el servidor (como en las aplicaciones basadas en sesiones), se utiliza un token (como JWT) que el cliente envía con cada solicitud. Esto permite que el servidor sea más escalable y que las aplicaciones se distribuyan más fácilmente.
        
        Resumen
        La autenticación en el SecurityContextHolder es temporal y solo dura durante la duración de la solicitud actual.
        Una vez que se envía la respuesta, el contexto de seguridad se limpia.
        En la siguiente solicitud, si se proporciona un token válido, el proceso de autenticación se repetirá, estableciendo nuevamente el contexto de seguridad para esa solicitud.
        Este enfoque permite que las aplicaciones manejen la autenticación de manera eficiente y escalable, manteniendo la seguridad sin necesidad de almacenar el estado de la sesión en el servidor.
    */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        final String token = getTokenFromRequest(request);

        if(token == null) {
            /* SI el token es nulo, no se hara validacion de JWT y continuara cadena de filtros ya sea para registrar 
             * un nuevo usuario o para autenticarlo (y luego de eso se le enviara eel jwt en la respuesta)
             */
            filterChain.doFilter(request, response); 
            return;
        }

        final String username = jwtService.getUsernameFromToken(token);

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if(jwtService.isTokenValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }

    /* El token se obtiene del encabezado del request */
    private String getTokenFromRequest(HttpServletRequest request) {
        final String authHeaderPreffix = "Bearer ";
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(StringUtils.hasText(authHeader) && authHeader.startsWith(authHeaderPreffix)) {
            return authHeader.substring(authHeaderPreffix.length());
        }
        return null;
    }

}
