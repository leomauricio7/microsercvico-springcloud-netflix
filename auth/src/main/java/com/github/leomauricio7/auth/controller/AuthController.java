package com.github.leomauricio7.auth.controller;

import com.github.leomauricio7.auth.data.dto.UserDTO;
import com.github.leomauricio7.auth.jwt.JwtTokenProvider;
import com.github.leomauricio7.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    @RequestMapping("/testSecurity")
    public String teste(){
        return "testado";
    }

    @PostMapping(
            produces = {"application/json", "application/xml", "application/x-yaml"},
            consumes = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO){
        try {

            //pegando login e senha
            var userName = userDTO.getUserName();
            var password = userDTO.getPassword();

            // realizar a autenticaco do user
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));

            // vai no nanco validar user
            var user = userRepository.findByUserName(userName);

            // cria o var de guardar o token
            var token = "";

            // validar se retornou algum user
            if(user != null){
                // sera o token
                token = jwtTokenProvider.createToken(userName, user.getRoles());
            } else {
                // lanca excecao caso de erro
                throw  new UsernameNotFoundException("UserName not found.");
            }

            // mapeando o retorno
            Map<Object, Object> objectMap =  new HashMap<>();
            objectMap.put("username", userName);
            objectMap.put("token", token);

            return ResponseEntity.ok(objectMap);

        } catch (AuthenticationException e){
            throw  new BadCredentialsException("Invalid Credentials.");
        }
    }
}
