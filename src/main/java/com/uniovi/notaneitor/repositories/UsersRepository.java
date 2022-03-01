package com.uniovi.notaneitor.repositories;

import com.uniovi.notaneitor.entities.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UsersRepository extends CrudRepository<User, Long>{
    User findByDni(String dni);

    @Query("SELECT u FROM User u WHERE UPPER(u.role) LIKE UPPER('ROLE_PROFESSOR')")
    List<User> findProfessors();
}