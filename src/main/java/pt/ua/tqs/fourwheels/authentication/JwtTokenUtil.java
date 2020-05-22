package pt.ua.tqs.fourwheels.authentication;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import static java.util.Base64.getEncoder;

@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = 1L;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private String expirationTime;

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(getEncoder().encodeToString(secret.getBytes())).parseClaimsJws(token).getBody();
    }

//    public Map<String, Object> getCredentialsFromToken(String token) {
//        Claims ca = getAllClaimsFromToken(token);
//        Map<String, Object> credentials = new HashMap<>();
//        credentials.put("email", ca.get("email"));
//        credentials.put("name", ca.get("name"));
//        credentials.put("home", ca.get("home"));
//        return credentials;
//    }
    public String getUsernameFromToken(String token) {
        Claims ca = getAllClaimsFromToken(token);
        return ca.get("email").toString();
    }

    public Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDetails user) {
        Map<String, Object> claims = new HashMap<>();
        Map<String, Object> headers = new HashMap<>();
        claims.put("email", user.getUsername());
        headers.put("typ", "JWT");
        return doGenerateToken(claims, headers);
    }

    private String doGenerateToken(Map<String, Object> claims, Map<String, Object> headers) {
        Long expirationTimeLong = Long.parseLong(expirationTime); //in second

        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong * 1000);

        SignatureAlgorithm hs512 = SignatureAlgorithm.HS512;
        String encoder = getEncoder().encodeToString(secret.getBytes());

        return Jwts.builder()
                .setHeader(headers)
                .setClaims(claims)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(hs512, encoder)
                .compact();
    }
    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}