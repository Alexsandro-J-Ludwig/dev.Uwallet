package dev.annonymoys.dev_uwallet.user;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public long getId(String email, String password) {
        UserModal userModal =  userRepository.findByEmail(email);
        if(userModal != null){
            if(PasswordHash.verifyPassword(userModal.getPassword(), password)){
                return userModal.getId();
            }
        }
        throw new BadCredentialsException("User not found");
    }

    public long signup(UserDTO userDTO) {
        if(userRepository.findByEmail(userDTO.getEmail()) != null){
            throw new IllegalArgumentException("This email has using");
        }

        UserModal newUser = new UserModal(userDTO.getUsername(), userDTO.getEmail(), userDTO.getPassword());
        UserModal saved = userRepository.save(newUser);
        return saved.getId();
    }

    public void delete(long id) {
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
            return;
        }
        throw new EmptyResultDataAccessException("User with id " + id + " not found", 1);
    }

    public void update(long id, @Valid UserUpdateDTO userDTO) {
        Optional<UserModal> userOptional = Optional.ofNullable(userRepository.findById(id));

        if (userOptional.isPresent()) {
            UserModal existingUser = userOptional.get();

            if (userDTO.getUsername() != null && !userDTO.getUsername().isEmpty()) {
                existingUser.setUsername(userDTO.getUsername());
            }
            if (userDTO.getEmail() != null && !userDTO.getEmail().isEmpty()) {
                existingUser.setEmail(userDTO.getEmail());
            }
            if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
                existingUser.setPassword(userDTO.getPassword());
            }
            userRepository.save(existingUser);
        } else {
            throw new RuntimeException("User not found");
        }
    }}

