package com.medcallapi.controller;

import com.medcallapi.request.UserRequest;
import com.medcallapi.response.UserResponse;
import com.medcallapi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping(path = "/{username}")
    public ResponseEntity<UserResponse> show(@PathVariable String username) {
        return userService.show(username);
    }

    @PutMapping(path = "/{username}/infos")
    public ResponseEntity<UserResponse> update(
            @PathVariable String username,
            @RequestBody UserRequest userRequest
    ) { return userService.updateInfos(username, userRequest); }

    @PatchMapping(path = "/{username}/password")
    public ResponseEntity<UserResponse> update(
            @PathVariable String username,
            @RequestBody String password
    ) { return userService.updatePassword(username, password); }

}
