package com.uniovi.notaneitor.services;

import com.uniovi.notaneitor.entities.User;
import com.uniovi.notaneitor.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfessorsService {

    @Autowired
    private UsersRepository usersRepository;

    public List<User> getProfessors() {
        List<User> professors = new ArrayList<User>();
        usersRepository.findAllProfessors().forEach(professors::add);
        return professors;
    }

    public User getProfessor(Long id) {
        return usersRepository.findProfessorById(id);
    }

    public void addProfessor(User p) {
        //No se para que hace esto
        p.setName(p.getName());
        p.setLastName(p.getLastName());
        p.setDni(p.getDni());
        p.setCategory(p.getCategory());
        Long longValue = p.getId();
        if(longValue.equals(null) || longValue==0){
            p.setId(usersRepository.findLastId()+1);
        }
        // Si el Id es null le asignamos el ultimo + 1 de la lista
        usersRepository.addProfessor(p.getDni(), p.getName(), p.getLastName(), p.getCategory(), p.getId());
    }

    public void deleteProfessor(Long id) {
        usersRepository.deleteById(id);

    }

    public void modifyProfessor(User p){
        usersRepository.updateProfessorById(p.getDni(), p.getName(), p.getLastName(), p.getCategory(), p.getId());
    }
}
