package com.medcallapi.controller.admin;

import com.medcallapi.entity.UserEntity;
import com.medcallapi.service.admin.AdminUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/admin/users")
@CrossOrigin
public class AdminUserController {

    private AdminUserService userService;

    @GetMapping(path = "")
    public List<UserEntity> index() {
        return userService.index();
    }

    @DeleteMapping(path = "{username}")
    public ResponseEntity<Void> destroy(@PathVariable String username) {
        return userService.destroy(username);
    }

}
