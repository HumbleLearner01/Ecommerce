package com.ecommerce.service.user;


import com.ecommerce.config.jwt.JwtUtil;
import com.ecommerce.model.user.User;
import com.ecommerce.model.user.VerificationToken;
import com.ecommerce.repository.user.UserRepository;
import com.ecommerce.repository.user.VerificationTokenRepository;
import com.ecommerce.service.mail.Email;
import com.ecommerce.service.mail.MailService;
import com.ecommerce.helper.exception.CustomException;
import com.ecommerce.helper.exception.ResourceNotFoundException;
import com.ecommerce.helper.payload.user.AuthenticationResponse;
import com.ecommerce.helper.payload.user.LoginRequest;
import com.ecommerce.helper.payload.user.RegisterRequest;
import com.ecommerce.helper.payload.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {
    public static String activationEmail;
    private final UserRepository userRepo;
    private final VerificationTokenRepository tokenRepo;
    private final ModelMapper mapper;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    //create a user and send the verification email
    @Transactional
    public void save(RegisterRequest registerRequest) {
        User user = mapper.map(registerRequest, User.class);
        user.setCreatedAt(Instant.now());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        User savedUser = userRepo.save(user);
        String token = generateVerificationToken(user);
        mailService.sendEmail(new Email(
                "Please Activate Your Account",
                user.getEmail(),
                "Thank you for signing up to spring reddit app!" +
                "please click the url below to activate ur account: " + "http://localhost:8080/api/v1/auth/user/accountVerification/" + token));
        activationEmail = "http://localhost:8080/api/v1/auth/user/accountVerification/" + token;
        log.info(activationEmail);
        mapper.map(savedUser, RegisterRequest.class);
    }

    private String generateVerificationToken(User user) {
        VerificationToken verificationToken = new VerificationToken(user);
        tokenRepo.save(verificationToken);
        return verificationToken.getToken();
    }

    //verify sent email i.e. set the user as enabled
    @Transactional
    public void verifyAccount(String token) {
        VerificationToken verificationToken = tokenRepo.findByToken(token).orElseThrow(() -> new CustomException("Resource not found!"));
        String username = verificationToken.getUser().getUsername();
        User user = userRepo.findUserByUsername(username).orElseThrow(() -> new CustomException("Resource not found!"));
        user.setEnabled(true);
        userRepo.save(user);
    }

    //login
    @Transactional
    public AuthenticationResponse login(LoginRequest loginRequest) {
        String token = null;
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
            token = jwtUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        return new AuthenticationResponse(loginRequest.getUsername(),token);
    }

    //get the current logged-in user
    @Transactional
    public User currentUser() {
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        return userRepo.findUserByUsername(principal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User","Username",principal.getName()));
    }

    @Transactional
    public UserDto currentUserDto() {
        return mapper.map(this.currentUser(), UserDto.class);
    }

    //is logged-in
    @Transactional
    public boolean isLoggedIn(User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.isAuthenticated();
    }

    /*--------------------------------helping methods--------------------------------*/
    //find by id
    @Transactional
    public User findById(Long userId) {
        return userRepo.findById(String.valueOf(userId))
                .orElseThrow(()->new ResourceNotFoundException("User","UserID",userId.toString()));
    }
}