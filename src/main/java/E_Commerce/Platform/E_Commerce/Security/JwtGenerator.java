package E_Commerce.Platform.E_Commerce.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;


import java.util.Date;

@Component
public class JwtGenerator {
   public String generateToken(Authentication authentication){
       String userName = authentication.getName();
       Date currentDate = new Date();
       Date expirationDate = new Date(currentDate.getTime() + SecurityConstants.EXPIRATION_TIME);
       String token = Jwts.builder()
               .setSubject(userName)
               .setIssuedAt(currentDate)
               .setExpiration(expirationDate)
               .signWith(SignatureAlgorithm.HS512,SecurityConstants.JWT_SECRET_KEY)
               .compact();
       return token;
   }
   public String getUserNameFromToken(String token){
       Claims claims = Jwts.parser()
               .setSigningKey(SecurityConstants.JWT_SECRET_KEY)
               .parseClaimsJws(token)
               .getBody();
       return claims.getSubject();
   }
   public  boolean validateToken(String token){
       try {
           Jwts.parser().setSigningKey(SecurityConstants.JWT_SECRET_KEY).parseClaimsJws(token);
           return true;
       }
       catch (Exception exception){
           throw new AuthenticationCredentialsNotFoundException("Jwt is expired or incorrect");
       }

    }

}
