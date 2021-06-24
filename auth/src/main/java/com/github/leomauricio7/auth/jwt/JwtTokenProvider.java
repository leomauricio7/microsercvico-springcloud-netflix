package com.github.leomauricio7.auth.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key}")
    private String secreteKey;

    @Value("${security.jwt.token.expire-length}")
    private Long expire;

    @Qualifier("userService") // colocar o nome do seu qualifier
    @Autowired
    private UserDetailsService userDetailsService;

    // metodo de criptografar a senha
    @PostConstruct // executa no inicio para criptografar a senha
    protected void init(){
        secreteKey = Base64.getEncoder().encodeToString(secreteKey.getBytes());
    }

    // metodo para criar token
    public String createToken(String userName, List<String> roles){
        Claims claims  = Jwts.claims().setSubject(userName); // seta o subject
        claims.put("roles", roles); // seta as roles do user

        Date now = new Date(); // criar uma data
        Date dateExpires = new Date(now.getTime()+expire); // pega sua data e soma com o tempo de expiração

        return  Jwts.builder()
                .setClaims(claims) // seta o claims
                .setIssuedAt(now) // seta a data criada
                .setExpiration(dateExpires) // seta a data de expiração
                .signWith(SignatureAlgorithm.HS256,secreteKey) // seta o algoritimo de criptografia
                .compact();
    }

    // metodo de autenticação do token
    public Authentication getAuthentication(String token){
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUserName(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // metodo de pegar userName a parti do token
    private String getUserName(String token){
        return Jwts.parser().setSigningKey(secreteKey).parseClaimsJws(token).getBody().getSubject();
    }

    // metodo de resolver token
    public String resolveToken(HttpServletRequest httpServletRequest){
        String bearerToken = httpServletRequest.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    // metodo de validar token
    public boolean validaToken(String token){
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(secreteKey).parseClaimsJws(token);
            if(claims.getBody().getExpiration().before(new Date())){
                return false;
            }
            return true;
        }catch (JwtException | IllegalArgumentException e){
            return false;
        }
    }

}
