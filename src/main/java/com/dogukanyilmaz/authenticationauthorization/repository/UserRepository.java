package com.dogukanyilmaz.authenticationauthorization.repository;

import com.dogukanyilmaz.authenticationauthorization.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author dogukanyilmaz
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsernameAndPassword(String username, String password);
}
