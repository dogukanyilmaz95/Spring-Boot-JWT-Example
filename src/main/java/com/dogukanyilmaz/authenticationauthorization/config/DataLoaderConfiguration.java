package com.dogukanyilmaz.authenticationauthorization.config;

import com.dogukanyilmaz.authenticationauthorization.entity.Role;
import com.dogukanyilmaz.authenticationauthorization.entity.User;
import com.dogukanyilmaz.authenticationauthorization.repository.RoleRepository;
import com.dogukanyilmaz.authenticationauthorization.repository.UserRepository;
import com.github.matek2305.dataloader.DataLoader;
import com.github.matek2305.dataloader.annotations.EnableDataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dogukanyilmaz
 */
@Configuration
@EnableDataLoader
public class DataLoaderConfiguration implements DataLoader {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public DataLoaderConfiguration(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void load() {
        if (userRepository.findAll().isEmpty()) {
            User user = new User();
            user.setUsername("admin");
            user.setName("admin");
            user.setPassword("8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92"); //123456
            userRepository.save(user);
            User user2 = new User();
            user2.setUsername("user");
            user2.setName("user");
            user2.setPassword("8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92"); //123456
            userRepository.save(user2);

            List<User> users = new ArrayList<>();
            users.add(user);
            Role role = new Role();
            role.setName("ADMIN");
            role.setCode("ADMIN");
            role.setUsers(users);
            roleRepository.save(role);
            users = new ArrayList<>();
            users.add(user2);
            Role role2 = new Role();
            role2.setName("USER");
            role2.setCode("USER");
            role2.setUsers(users);
            roleRepository.save(role2);
        }
    }
}
