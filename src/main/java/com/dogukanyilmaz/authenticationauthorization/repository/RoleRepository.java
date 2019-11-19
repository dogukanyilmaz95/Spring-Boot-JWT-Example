package com.dogukanyilmaz.authenticationauthorization.repository;

import com.dogukanyilmaz.authenticationauthorization.entity.Role;
import com.dogukanyilmaz.authenticationauthorization.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author dogukanyilmaz
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> getRolesByUsers(User user);
}
