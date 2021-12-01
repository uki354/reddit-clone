package com.example.redditclone.service;

import com.example.redditclone.config.AppConfig;
import com.example.redditclone.Model.NotificationEmail;
import com.example.redditclone.Model.User;
import com.example.redditclone.Model.VerificationToken;
import com.example.redditclone.dto.LoginRequest;
import com.example.redditclone.dto.RegisterRequest;
import com.example.redditclone.exception.RedditCloneException;
import com.example.redditclone.repository.UserRepository;
import com.example.redditclone.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.*;

@Service
@Transactional
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final VerificationTokenRepository tokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AppConfig appConfig;


    public void signUp(RegisterRequest registerRequest){
        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setCreatedAt(Date.from(Instant.now()));
        user.setPassword(bCryptPasswordEncoder.encode(registerRequest.getPassword()));
        user.setEnabled(false);
        user.setRoles(new ArrayList<>());
        user.getRoles().add(roleService.getUserRole());

        System.out.println("where's problem?");
        userRepository.save(user);
        
        String token = generateVerificationToken(user);
        mailService.sendEmail(new NotificationEmail("Activation for Reddit Clone", user.getEmail(),
                            "Thank you for using our platform, please click on url to activate you account: "
                                + appConfig.getUrl() + "/api/auth/accountVerification/" +  token));


    }



    private String generateVerificationToken(User user) {
       String verificationToken =  UUID.randomUUID().toString();
       VerificationToken token  = new VerificationToken();
       token.setToken(verificationToken);
       token.setUser(user);
       tokenRepository.save(token);
       return verificationToken;

    }


    public void verifyAccount(String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RedditCloneException("Invalid token"));
        fetchUserAndEnable(verificationToken);
    }

    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RedditCloneException("User not found "  + username));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public void login(LoginRequest loginRequest) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));


    }

    public User getCurrentUser(){
//        org.springframework.security.core.userdetails.User user =
//                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        System.out.println(user.getUsername());
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();
        return userRepository.findByUsername(username).orElseThrow(()->new RedditCloneException("User not found "));
    }
}
