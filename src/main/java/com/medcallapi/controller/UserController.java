package com.medcallapi.controller;

import com.medcallapi.entity.UserEntity;
import com.medcallapi.request.PasswordRequest;
import com.medcallapi.request.UserRequest;
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
    public ResponseEntity<UserEntity> show(@PathVariable String username) {
        return userService.show(username);
    }

    @PutMapping(path = "/{username}/infos")
    public ResponseEntity<UserEntity> update(
            @PathVariable String username,
            @RequestBody UserRequest userRequest
    ) { return userService.updateInfos(username, userRequest); }

    @PatchMapping(path = "/{username}/password")
    public ResponseEntity<Void> update(
            @PathVariable String username,
            @RequestBody PasswordRequest passwordRequest
    ) { return userService.updatePassword(username, passwordRequest); }

}
