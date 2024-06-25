package co.com.flypass.driven.adapters.security.util;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.text.ParseException;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  private final RSAKey rsaJWK;

  public JwtUtil(RSAKey rsaJWK) {
    this.rsaJWK = rsaJWK;
  }

  public String generateToken(String username) throws JOSEException {
    var now = new Date();
    var expirationDate = new Date(now.getTime() + 60 * 1000);

    JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
        .subject(username)
        .issuer("flypass")
        .expirationTime(expirationDate)
        .issueTime(now)
        .build();

    SignedJWT signedJWT = new SignedJWT(
        new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(rsaJWK.getKeyID()).build(),
        claimsSet);
    JWSSigner signer = new RSASSASigner(rsaJWK);
    signedJWT.sign(signer);

    return signedJWT.serialize();
  }

  public String extractUsername(String token) throws JOSEException, ParseException {
    JWSVerifier verifier = new RSASSAVerifier(rsaJWK.toPublicJWK());
    SignedJWT signedJWT = SignedJWT.parse(token);
    assert signedJWT.verify(verifier);

    return signedJWT.getJWTClaimsSet().getSubject();
  }
/*
fun generateToken(username: String): String {
        val claims = Jwts.claims().setSubject(username)
        val now = Date()
        val expiryDate = Date(now.time + expirationTime.toLong() * 1000)

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(getSigningKey(), SignatureAlgorithm.HS512)
            .compact()
    }
 */
}
