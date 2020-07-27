package engine.controller;

import engine.model.User;
import engine.repository.UserRepository;
import engine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Validated
public class UserController {

    private UserRepository userRepository;

    private UserService userService;

    private PasswordEncoder encoder;

    public UserController(UserRepository userRepository, UserService userService, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.encoder = encoder;
    }

    @PostMapping(value = "/api/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user) {
        User byEmail = userRepository.findByEmail(user.getEmail());
        if (byEmail == null) {
            String password = encoder.encode(user.getPassword());
            user.setPassword(password);
            userService.registerUser(user);
            return ResponseEntity.ok().build();
        } else
            return ResponseEntity.badRequest().build();
    }
}
