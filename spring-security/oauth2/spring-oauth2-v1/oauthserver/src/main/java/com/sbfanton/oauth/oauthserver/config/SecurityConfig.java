package com.sbfanton.oauth.oauthserver.config;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	
	/*
	 *  ¿Qué es OIDC (OpenID Connect)?
		OIDC (OpenID Connect) es una capa de identidad construida sobre OAuth2. 
		Mientras que OAuth2 se enfoca en autorización (es decir, "¿puede esta app 
		acceder a este recurso?"), OIDC agrega autenticación: "¿quién es el usuario 
		que está autenticándose?".
		
		Con OIDC puedes, por ejemplo:
		
		Autenticar usuarios y obtener su identidad.
		
		Recibir información estándar sobre el usuario (como nombre, email, etc.).
		
		Usar tokens JWT firmados por el servidor de autorización.
		
		OIDC define varios endpoints (como /userinfo, /id_token) y scopes (openid, 
		profile, etc.).
	 * */
	@Bean 
	@Order(1)
	/*
	 * Esto define un SecurityFilterChain dedicado al servidor de autorización. 
	 * Spring Security permite tener múltiples cadenas de filtros, cada una con su 
	 * propio comportamiento.
	 * */
	public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
			throws Exception {
		
		/*
		 * Esto instancia un configurador específico para servidores de 
		 * autorización (OAuth2AuthorizationServerConfigurer), que expone configuraciones para todos los endpoints de OAuth2/OIDC como:

			/oauth2/authorize
			
			/oauth2/token
			
			/oauth2/jwks
			
			/userinfo
			
			Etc.
		 * */
		OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
				OAuth2AuthorizationServerConfigurer.authorizationServer();

		http
		/*
		 * Esto restringe esta cadena de filtros únicamente a las rutas del servidor de 
		 * autorización (es decir, no a toda la aplicación).
		   Por ejemplo, si accedes a /login, esta cadena no se aplica; solo se aplica para los 
		   endpoints de OAuth2/OIDC.
		 * */
			.securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
			
			/*
			 * Aplica el configurador al HttpSecurity.
			   authorizationServer.oidc(...) habilita soporte para OpenID Connect. 
			   Esto activa los endpoints relacionados con identidad, como:
			   - /userinfo
			   - Generación de id_token junto con el access_token
			   - Scopes como openid, profile
			 * */
			.with(authorizationServerConfigurer, (authorizationServer) ->
				authorizationServer
					.oidc(Customizer.withDefaults())
			)
			/*
			 * Esto indica que cualquier solicitud a estos endpoints debe provenir de un 
			 * usuario autenticado. Es decir, si el usuario no ha iniciado sesión, 
			 * será redirigido al login.
			 * */
			.authorizeHttpRequests((authorize) ->
				authorize
					.anyRequest().authenticated()
			)
			/*
			 * Esto configura que si alguien intenta acceder a estos endpoints desde un navegador (HTML), 
			 * y no está autenticado, será redirigido a /login.
			   LoginUrlAuthenticationEntryPoint("/login") → redirige a /login si hace falta autenticación.
			   MediaTypeRequestMatcher(MediaType.TEXT_HTML) → aplica solo si la solicitud acepta text/html (navegador, no clientes API).
			 * */
			.exceptionHandling((exceptions) -> exceptions
				.defaultAuthenticationEntryPointFor(
					new LoginUrlAuthenticationEntryPoint("/login"),
					new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
				)
			);

		return http.build();
	}

	@Bean 
	@Order(2)
	/*
	 * Configura la seguridad general para cualquier solicitud que no sea parte del 
	 * Authorization Server.
	Componentes:
	.anyRequest().authenticated(): Todas las peticiones requieren autenticación.
	.formLogin(...): Habilita login con formulario por defecto (/login).
	.csrf().disable(): Desactiva CSRF (no recomendado en producción sin precauciones, 
	pero útil para pruebas con OAuth).
	@Order(2): Este SecurityFilterChain se aplica después del que se usa para los 
	endpoints del Authorization Server.
	 * */
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
			throws Exception {
		http
			.authorizeHttpRequests((authorize) -> authorize
				.anyRequest().authenticated()
			)
			.csrf(csrf -> csrf.disable())
			.formLogin(Customizer.withDefaults());

		return http.build();
	}

	/*
	 * Define un usuario en memoria para autenticación de usuarios finales que se loguean.
	   Se usa cuando alguien accede a /login o al Authorization Server y se requiere 
	   autenticación del usuario.
	   La contraseña no está encriptada ({noop}password); solo para pruebas.
	 * */
	@Bean 
	public UserDetailsService userDetailsService() {
		UserDetails userDetails = User.builder()
				.username("iva")
				.password("{noop}123456")
				.roles("USER")
				.build();

		return new InMemoryUserDetailsManager(userDetails);
	}

	/*
	 * Define un cliente registrado (como una app web o móvil) que se puede autenticar y 
	 * usar el servidor de autorización para, 
	 * valga la redundancia, pedir autorizacion para el acceso a recursos protegidos.

		Campos importantes:
		- clientId: Identificador del cliente.
		- clientSecret: Secreto (con {noop} para evitar codificación).
		- authorizationGrantType(...): Soporta los flujos authorization_code y refresh_token.
		- redirectUri: Adónde se redirige después del login.
		- scope: Define qué datos puede pedir (openid, profile, etc.).
		
		
		¿Qué es redirectUri?
		
		En el flujo Authorization Code de OAuth2/OIDC, el proceso es así:
		- El cliente (por ejemplo, una aplicación web) redirige al usuario al 
		servidor de autorización.
		- El usuario inicia sesión y autoriza los permisos.
		- El servidor de autorización redirige de vuelta al cliente a una URI 
		previamente registrada (el redirectUri) con un code en los parámetros de la URL.
		- El cliente intercambia ese code por un token de acceso (y opcionalmente un 
		id_token o refresh_token).
		
		¿Qué hace https://oauthdebugger.com/debug?
		
		Este es un servicio externo de debugging de OAuth2/OIDC. Su propósito es ayudarte a probar flujos OAuth2 sin tener que tener tu propio cliente completo implementado.
		Cuando usás esa URL como redirectUri:
		Después de autenticarte con tu servidor de autorización, serás redirigido a https://oauthdebugger.com/debug?code=...&state=...
		Ahí verás el código de autorización y otros parámetros que podrías usar para probar la obtención del token.
		No es un cliente real, sino una herramienta para desarrolladores.
	 * */
	@Bean 
	public RegisteredClientRepository registeredClientRepository() {
		RegisteredClient oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
				.clientId("oidc-client")
				.clientSecret("{noop}123456789")
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
				.redirectUri("https://oauthdebugger.com/debug")
				.postLogoutRedirectUri("http://127.0.0.1:9000/")
				.scope(OidcScopes.OPENID)
				.scope(OidcScopes.PROFILE)
				.scope("read")
				.scope("write")
				.build();
		
		RegisteredClient oidcClient2 = RegisteredClient.withId(UUID.randomUUID().toString())
				.clientId("oauth-client")
				.clientSecret("{noop}12345678910")
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
				//.redirectUri("http://127.0.0.1:8080/login/oauth2/code/oauth-client")
				.redirectUri("http://127.0.0.1:8080/authorized")
				.postLogoutRedirectUri("http://127.0.0.1:8080/logout")
				.scope(OidcScopes.OPENID)
				.scope(OidcScopes.PROFILE)
				.scope("read")
				.scope("write")
				.build();

		return new InMemoryRegisteredClientRepository(oidcClient, oidcClient2);
	}

	/*
	 * Genera una clave RSA pública/privada para firmar los tokens JWT emitidos por el 
	 * servidor de autorización.
	   Se publica en el endpoint /oauth2/jwks para que los clientes puedan verificar la 
	   firma del token.
	 * */
	@Bean 
	public JWKSource<SecurityContext> jwkSource() {
		KeyPair keyPair = generateRsaKey();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		RSAKey rsaKey = new RSAKey.Builder(publicKey)
				.privateKey(privateKey)
				.keyID(UUID.randomUUID().toString())
				.build();
		JWKSet jwkSet = new JWKSet(rsaKey);
		return new ImmutableJWKSet<>(jwkSet);
	}

	/*
	 * Genera un par de claves RSA de 4096 bits.
	   En producción deberías usar una clave persistente o un JKS/keystore externo.
	 * */
	private static KeyPair generateRsaKey() { 
		KeyPair keyPair;
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(4096);
			keyPair = keyPairGenerator.generateKeyPair();
		}
		catch (Exception ex) {
			throw new IllegalStateException(ex);
		}
		return keyPair;
	}

	/**
	 * Configura el JwtDecoder para validar los JWT emitidos por el servidor de 
	 * autorización usando la clave RSA generada.
	 */
	@Bean 
	public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
		return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
	}

	/*
	 * Proporciona configuraciones básicas como:
		Endpoints (/oauth2/token, /oauth2/authorize, etc.)
		Issuer URL, si se necesita.
		Al usar .build(), se usan los valores por defecto. Puedes personalizarlos si necesitas.
	 * */
	@Bean 
	public AuthorizationServerSettings authorizationServerSettings() {
		return AuthorizationServerSettings.builder().build();
	}
	
	/**
	 * Conclusión
		Componente	¿Para qué sirve?
		authorizationServerSecurityFilterChain	-> Seguridad para endpoints del Authorization Server
		defaultSecurityFilterChain	-> Seguridad general y login form
		userDetailsService	-> Autenticación de usuarios
		registeredClientRepository	-> Registro de aplicaciones clientes
		jwkSource + generateRsaKey	-> Generación y publicación de claves RSA
		jwtDecoder	-> Validación de tokens JWT
		authorizationServerSettings	-> Configuración general del Authorization Server
	 */

}