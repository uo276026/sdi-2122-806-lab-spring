package com.uniovi.notaneitor.validators;

import com.uniovi.notaneitor.entities.User;
import com.uniovi.notaneitor.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.*;

@Component
public class ProfessorAddValidator implements Validator {
    @Autowired
    private UsersService usersService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User p = (User) target;
        if (p.getDni().length() != 9) {
            errors.rejectValue("dni", "Error.dni.length");
        }
        if (!Character.isLetter(p.getDni().charAt(9))) {
            errors.rejectValue("dni", "Error.dni.letter");
        }
    }




}
