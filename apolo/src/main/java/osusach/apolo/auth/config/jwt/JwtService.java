package osusach.apolo.auth.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    // The secret key used to sign the JWT token
    // This key should be kept secret and should not be shared
    // Currently in the codebase, but should be stored in a secure location
    private static final String SECRET_KEY = "1HNPx5aGhXD7GkyBZkiX+rq0N45vYW5QVzeNQgUSm3CnNYtFCwvh8ze8W3OKUdhw";

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // Set the expiration time of the token to 10 hours
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(getSingKey(), io.jsonwebtoken.SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsername(String token) {
        return getClaims(token, Claims::getSubject);
    }

    public <T> T getClaims(String jwt, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(jwt);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaims(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(getSingKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    private Key getSingKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateToken(String jwt, UserDetails userDetails) {
        final String username = getUsername(jwt);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(jwt);
    }

    private boolean isTokenExpired(String jwt) {
        final Date expiration = getTokenExpirationDate(jwt);
        return expiration.before(new Date());
    }

    private Date getTokenExpirationDate(String jwt) {
        return getClaims(jwt, Claims::getExpiration);
    }

}