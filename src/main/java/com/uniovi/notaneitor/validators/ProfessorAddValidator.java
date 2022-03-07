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
        User user = (User) target;
        //El DNI debe tener exactamente 9 caracteres
        if (user.getDni().length() != 9) {
            errors.rejectValue("dni", "Error.dni.length");
        } else {
            //El DNI no termina en letra
            if (!Character.isLetter(user.getDni().charAt(8))) {
                errors.rejectValue("dni", "Error.dni.letter");
            } else{
                //El DNI está repetido
                User user2 = usersService.getUserByDni(user.getDni());
                if(user2!=null){
                    errors.rejectValue("dni", "Error.dni.repeated");
                }
            }
        }


    }




}
