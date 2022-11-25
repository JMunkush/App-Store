package kz.springboot.springbootdemo.services;


import kz.springboot.springbootdemo.entities.Roles;
import kz.springboot.springbootdemo.entities.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    Users getUserByEmail(String email);
    List<Users> getAllUsers();
    List<Roles> findAllById(Users users);
    List<Roles> findAllRoles();
    Users getUser(Long id);
    void save(Users user);
    void delete(Users user);
    Roles getRole(Long id);
    Roles saveRole(Roles role);
    Roles addRole(Roles role);
    void deleteRole(Roles role);
}
