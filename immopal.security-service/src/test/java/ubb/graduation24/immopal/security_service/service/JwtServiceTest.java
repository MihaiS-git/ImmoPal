package ubb.graduation24.immopal.security_service.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.Key;
import java.util.*;

public class JwtServiceTest {

    private JwtService jwtService;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        jwtService = new JwtService();
        setSecretKey("VW5pdmVyc2l0YXRlYSBCYWJlcy1Cb2x5YWkgZmFjdWx0YXRlYSBkZSBNYXRlbWF0aWNhIHNpIEluZm9ybWF0aWNhIEdyYWR1YXRpb24yNA==");
    }

    private void setSecretKey(String key) throws NoSuchFieldException, IllegalAccessException {
        Field secretKeyField = JwtService.class.getDeclaredField("secretKey");
        secretKeyField.setAccessible(true);
        secretKeyField.set(jwtService, key);
    }

    @Test
    void generateToken_ShouldReturnToken() {
        when(userDetails.getUsername()).thenReturn("test@example.com");

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        doReturn(authorities).when(userDetails).getAuthorities();

        String token = jwtService.generateToken(userDetails);
        assertNotNull(token);
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    void extractUsername_ShouldReturnUsername() {
        // Arrange
        when(userDetails.getUsername()).thenReturn("test@example.com");

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        doReturn(authorities).when(userDetails).getAuthorities();

        // Act
        String token = jwtService.generateToken(userDetails);

        // Assert
        assertNotNull(token, "Token should be generated.");
        String username = jwtService.extractUsername(token);
        assertEquals("test@example.com", username, "Extracted username should match the expected username.");
    }

    @Test
    void generateToken_ShouldReturnValidTokenFormat() {
        // Arrange
        when(userDetails.getUsername()).thenReturn("test@example.com");

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        doReturn(authorities).when(userDetails).getAuthorities();

        // Act
        String token = jwtService.generateToken(userDetails);

        // Assert
        assertNotNull(token);
        assertTrue(token.split("\\.").length == 3, "Token should have three parts separated by dots.");

        // Check if token is valid (format)
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }


    @Test
    void isTokenExpired_ShouldReturnFalseForValidToken() {
        // Arrange
        when(userDetails.getUsername()).thenReturn("test@example.com");

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        doReturn(authorities).when(userDetails).getAuthorities();

        // Act
        String token = jwtService.generateToken(userDetails);

        // Assert
        assertTrue(jwtService.isTokenValid(token, userDetails), "Token should be valid initially.");
    }

    @Test
    void isTokenExpired_ShouldReturnFalseAfterExpiration() throws Exception {
        // Arrange
        when(userDetails.getUsername()).thenReturn("test@example.com");

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        doReturn(authorities).when(userDetails).getAuthorities();

        // Generate a token with a short expiration time
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", "ROLE_USER");
        String token = Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 2000)) // 2 seconds expiration
                .signWith(getSignInKeyUsingReflection(), SignatureAlgorithm.HS256)
                .compact();

        // Wait for the token to expire
        Thread.sleep(3000);

        // Act & Assert
        try {
            assertFalse(jwtService.isTokenValid(token, userDetails), "Token should be invalid after expiration.");
        } catch (ExpiredJwtException e) {
            System.out.println("Expected exception caught: " + e.getMessage());
        }
    }


    @Test
    void generateToken_ShouldReturnValidToken() {
        when(userDetails.getUsername()).thenReturn("test@example.com");

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        doReturn(authorities).when(userDetails).getAuthorities();

        String token = jwtService.generateToken(userDetails);
        assertNotNull(token);
        assertTrue(jwtService.isTokenValid(token, userDetails), "Token should be valid initially.");
    }

    private Key getSignInKeyUsingReflection() throws Exception {
        Method method = JwtService.class.getDeclaredMethod("getSignInKey");
        method.setAccessible(true);
        return (Key) method.invoke(jwtService);
    }


    @Test
    void extractExpiration_ShouldReturnExpirationDate() throws Exception {
        when(userDetails.getUsername()).thenReturn("test@example.com");

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        doReturn(authorities).when(userDetails).getAuthorities();

        String token = jwtService.generateToken(userDetails);
        Date expiration = extractExpirationUsingReflection(token);
        assertNotNull(expiration);
    }

    private Date extractExpirationUsingReflection(String token) throws Exception {
        Method method = JwtService.class.getDeclaredMethod("extractExpiration", String.class);
        method.setAccessible(true);
        return (Date) method.invoke(jwtService, token);
    }
}