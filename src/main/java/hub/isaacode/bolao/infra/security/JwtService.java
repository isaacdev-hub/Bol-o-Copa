package hub.isaacode.bolao.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import hub.isaacode.bolao.domain.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.springframework.security.config.Elements.JWT;

@Service
public class JwtService {

    private static final String ISSUER = "bolao-copa";

    private final Algorithm algorithm;
    private final long expirationHours;

    public JwtService(@Value("${jwt.secret}") String secret,
                      @Value("${jwt.expiration-hours}") long expirationHours) {
        this.algorithm = Algorithm.HMAC256(secret);
        this.expirationHours = expirationHours;
    }

    public String generateToken(User user) {
        Instant now = Instant.now();
        return JWT.create()
                .withIssuer(ISSUER)
                .withSubject(user.getId().toString())
                .withClaim("role", user.getRole().name())
                .withIssuedAt(now)
                .withExpiresAt(now.plus(expirationHours, ChronoUnit.HOURS))
                .sign(algorithm);
    }

    public Optional<String> validateAndGetSubject(String token) {
        try {
            return Optional.of(
                    JWT.require(algorithm)
                            .withIssuer(ISSUER)
                            .build()
                            .verify(token)
                            .getSubject());
        } catch (JWTVerificationException e) {
            return Optional.empty();
        }
    }
}