package com.uniovi.notaneitor.services;

import java.util.*;
import javax.annotation.PostConstruct;
import com.uniovi.notaneitor.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uniovi.notaneitor.entities.*;
import com.uniovi.notaneitor.services.UsersService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostConstruct
    public void init() {
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<User>();
        usersRepository.findAll().forEach(users::add);
        return users;
    }

    public User getUser(Long id) {
        return usersRepository.findById(id).get();
    }

    public void addUser(User user) {
        user.setName(user.getName());
        user.setLastName(user.getLastName());
        user.setDni(user.getDni());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }

    public User getUserByDni(String dni) {
        return usersRepository.findByDni(dni);
    }

    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }

    public List<User> getProfessors() {
        List<User> professors = new ArrayList<User>();
        usersRepository.findProfessors().forEach(professors::add);
        return professors;
    }

    public User getProfessor(Long id) {
        return usersRepository.findById(id).get();
    }

    public void addProfessor(User p) {
        // Si el Id es null le asignamos el ultimo + 1 de la lista
        usersRepository.save(p);
    }
    public void deleteProfessor(Long id) {
        usersRepository.deleteById(id);

    }
}
