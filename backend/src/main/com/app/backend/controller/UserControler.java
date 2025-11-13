package com.app.backend.controller;

import com.app.backend.model.User;
import com.app.backend.servce.UserService;
import com.app.backend.dto.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.Http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping
    @PreAuthorize("hasRole('ADMIN','COORDINADOR')")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN','COORDINADOR')")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
}

@PostMapping
    @PreAuthorize("hasRole('ADMIN','COORDINADOR')")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.create(user));
}

@PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN','COORDINADOR')")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        try{
            User existingUser = userService.findById(id);
            if(existingUser.getRole().equals("ADMIN")){
                User updatedUser = userService.update(existingUser);
                return ResponseEntity.status(403).body(null);
            }
            User updatedUser = userService.update(existingUser);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);

        }
}

@DeleteMapping(value ="/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> deleteUser(@PathVariable Long id, @RequestBody User user) {
        userService.delete(id);
        return ResponseEntity.ok(new MessageResponse("Usuario eliminado con éxito"));
    }
}