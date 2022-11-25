package kz.springboot.springbootdemo.services.impl;

import kz.springboot.springbootdemo.entities.Roles;
import kz.springboot.springbootdemo.entities.Users;
import kz.springboot.springbootdemo.repositories.RoleRepository;
import kz.springboot.springbootdemo.repositories.UserRepository;
import kz.springboot.springbootdemo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Users myUser = userRepository.findByEmail(s);

        if(myUser != null){
            User secUser = new User(myUser.getEmail(), myUser.getPassword(), myUser.getRoles());
            return secUser;
        }
        throw new UsernameNotFoundException("User not found");
    }

    @Override
    public Users getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<Roles> findAllById(Users users) {
        return userRepository.findAllById(users);
    }

    @Override
    public List<Roles> findAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Users getUser(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public void save(Users user) {
        userRepository.save(user);
    }

    @Override
    public void delete(Users user) {
        userRepository.delete(user);
    }

    @Override
    public Roles getRole(Long id) {
        return roleRepository.getOne(id);
    }

    @Override
    public Roles saveRole(Roles role) {
        return roleRepository.save(role);
    }

    @Override
    public Roles addRole(Roles role) {
        return roleRepository.save(role);
    }

    @Override
    public void deleteRole(Roles role) {
        roleRepository.delete(role);
    }
}
