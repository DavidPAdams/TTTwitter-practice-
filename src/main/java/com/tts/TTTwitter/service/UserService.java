package com.tts.TTTwitter.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tts.TTTwitter.model.Role;
import com.tts.TTTwitter.model.User;
import com.tts.TTTwitter.repository.RoleRepository;
import com.tts.TTTwitter.repository.UserRepository;

@Service
public class UserService {
  private UserRepository userRepository;
  private RoleRepository roleRepository;
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  
  @Autowired
  public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }
  
  public User findByUsernameContainsIgnoreCase(String username) {
    return userRepository.findByUsernameContainsIgnoreCase(username);
  }
  
  public List<User> findAllByFirstNameContainsIgnoreCase(String firstName){
    return (List<User>) userRepository.findAllByFirstNameContainsIgnoreCase(firstName);
  }
  
  public List<User> findAllByLastNameContainsIgnoreCase(String lastName){
    return (List<User>) userRepository.findAllByLastNameContainsIgnoreCase(lastName);
  }
  
  public List<User> findAll(){
    return (List<User>) userRepository.findAll();
  }
  
  public Optional<User> findById(Long id) {
    return userRepository.findById(id);
  }
  
  public void save(User user) {
    userRepository.save(user);
  }
  
  public User saveNewUser(User user) {
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    user.setActive(1);
    Role userRole = roleRepository.findByRole("User");
    user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
    return userRepository.save(user);
  }
  
  public User getLoggedInUser() {
    String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
    return findByUsernameContainsIgnoreCase(loggedInUsername);
  }
}
