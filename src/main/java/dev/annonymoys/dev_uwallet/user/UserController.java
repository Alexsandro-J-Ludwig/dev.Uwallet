package dev.annonymoys.dev_uwallet.user;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    @ResponseBody
    public ResponseEntity<Long> getId(@RequestParam String email, @RequestParam String password) {
        try{
            long userId = userService.getId(email, password);
            return ResponseEntity.ok(userId);
        } catch(BadCredentialsException e){
            throw e;
        }
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Long> signup(@Valid @RequestBody UserDTO userDTO) {
        try{
            long newUser = userService.signup(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void>  delete(@PathVariable long id) {
        try{
            userService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<UserModal> update(@PathVariable long id, @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        try{
            userService.update(id, userUpdateDTO);
            UserModal updateUser = userRepository.findById(id);

            return ResponseEntity.ok(updateUser);
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
}
