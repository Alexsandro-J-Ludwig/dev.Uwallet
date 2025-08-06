package dev.annonymoys.dev_uwallet.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModal,Long> {
    UserModal findById(long id);
    UserModal findByEmail(String email);
}
