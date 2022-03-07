package com.uniovi.notaneitor.repositories;

import com.uniovi.notaneitor.entities.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface UsersRepository extends CrudRepository<User, Long>{

    @Query("SELECT u FROM User u WHERE u.dni=?1")
    User findByDni(String dni);

    @Query("SELECT u FROM User u WHERE UPPER(u.role) LIKE UPPER('ROLE_PROFESSOR')")
    List<User> findAllProfessors();

    @Query("SELECT p FROM User p WHERE (LOWER(p.id) LIKE LOWER(?1)) AND p.category='ROLE_PROFESSOR'")
    User findProfessorById(Long id);

    @Query("SELECT max(p.id) FROM User p")
    Long findLastId();


    //@Query("SELECT p FROM Professor p")
    //List<Professor> findAllProfessors();

    @Modifying
    @Transactional
    @Query(value="insert into User (dni, name, last_name, category, role, id) values (?1, ?2, ?3, ?4, 'ROLE_PROFESSOR', ?5)",
            nativeQuery = true)
    void addProfessor(String dni, String nombre, String apellidos, String categoria, long id);

    @Modifying
    @Transactional
    @Query("UPDATE User SET dni = ?1, name = ?2, lastName=?3, category=?4 WHERE id = ?5")
    void updateProfessorById(String dni, String nombre, String apellidos, String categoria, Long id);

    @Modifying
    @Transactional
    @Query("DELETE from User where id = ?1")
    void deleteById(Long id);
}