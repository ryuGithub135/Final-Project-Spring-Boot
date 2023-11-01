package com.bankSultra.finalproject.controller.reservation;

import com.bankSultra.finalproject.dto.UpdateUserDto;
import com.bankSultra.finalproject.dto.UserResponseDto;
import com.bankSultra.finalproject.dto.WebResponse;
import com.bankSultra.finalproject.model.UserList;
import com.bankSultra.finalproject.security.beans.LoginRequest;
import com.bankSultra.finalproject.security.jwt.JwtResponse;
import com.bankSultra.finalproject.security.jwt.JwtTokenProvider;
import com.bankSultra.finalproject.services.UserService;
import com.bankSultra.finalproject.services.serviceimp.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api(value = "Fake Controller", description = "Operations pertaining to user login and logout in the BRS application")
@RequestMapping("/api")
public class FakeController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider tokenProvider;
    @Autowired
    UserService userService;

    @Autowired
    UserServiceImpl userServiceimpl;






    @ApiOperation(value = "Login", response = Iterable.class)
    @PostMapping("/auth")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserList userList = userService.findByemail(loginRequest.getEmail()).orElse(null);

        String jwt = tokenProvider.generateToken(authentication , userList);
        return ResponseEntity.ok(new JwtResponse(jwt));

    }

    @PatchMapping(
                path = "/api/users/current",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UserResponseDto> update(UserList user, @RequestBody UpdateUserDto request) {
        UserResponseDto userResponse = userServiceimpl.update(user, request);
        return WebResponse.<UserResponseDto>builder().data(userResponse).build();
    }

    @ApiOperation(value = "Logout", response = Iterable.class)
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {

        return  new ResponseEntity<>("lOGOUT!!", HttpStatus.OK);

    }



//    @DeleteMapping("/auth")
//    public ResponseEntity<?> logout() {
//        // Invalidate the user's JWT token
//        tokenProvider.invalidateToken(SecurityContextHolder.getContext().getAuthentication());
//
//        // Remove the user's authentication context
//        SecurityContextHolder.clearContext();
//
//        // Return a success response
//        return ResponseEntity.ok().build();
//    }


}
