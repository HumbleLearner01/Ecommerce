package com.ecommerce.controller.user;

import com.ecommerce.service.user.UserService;
import com.ecommerce.helper.payload.ApiResponse;
import com.ecommerce.helper.payload.user.AuthenticationResponse;
import com.ecommerce.helper.payload.user.LoginRequest;
import com.ecommerce.helper.payload.user.RegisterRequest;
import com.ecommerce.helper.payload.user.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth/user")
@AllArgsConstructor
@CrossOrigin("*")
public class AuthenticationController {
    private final UserService userService;

    //create user
    @PostMapping("/signup")
    public ResponseEntity<String> save(@Valid @RequestBody RegisterRequest registerRequest) {
        userService.save(registerRequest);
        return new ResponseEntity<>(
                "User Registration Successful! ::: " + UserService.activationEmail,
                HttpStatus.CREATED);
    }

    //activate user
    @RequestMapping(value = "/accountVerification/{token}", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<ApiResponse> verifyAccount(@PathVariable String token) {
        userService.verifyAccount(token);
        return new ResponseEntity<>(new ApiResponse("User Activated Successfully!", true), HttpStatus.OK);
    }

    //login user
    @PostMapping({"/login", "/generate-token"})
    public AuthenticationResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    @GetMapping("/current-user")
    public ResponseEntity<UserDto> currentUser() {
        return new ResponseEntity<>(userService.currentUserDto(), HttpStatus.OK);
    }
}