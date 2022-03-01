package com.uniovi.notaneitor.controllers;

import com.uniovi.notaneitor.services.RolesService;
import com.uniovi.notaneitor.services.SecurityService;
import com.uniovi.notaneitor.validators.SignUpFormValidator;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.uniovi.notaneitor.entities.*;
import com.uniovi.notaneitor.services.UsersService;
import org.springframework.security.core.Authentication;


@Controller
public class UsersController {
    @Autowired
    private RolesService rolesService;

    @Autowired
    private SignUpFormValidator signUpFormValidator;

    @Autowired
    private UsersService usersService;

    @Autowired
    private SecurityService securityService;

    @RequestMapping("/professor/list")
    public String getList(Model model) {
        model.addAttribute("usersList", usersService.getProfessors());
        return "professor/list";
    }

    @RequestMapping(value = "/user/add")
    public String getUser(Model model) {
        model.addAttribute("rolesList", rolesService.getRoles());
        return "user/add";
    }

    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public String setUser(@ModelAttribute User user) {
        usersService.addUser(user);
        return "redirect:/user/list";
    }

    @RequestMapping("/user/details/{id}")
    public String getDetail(Model model, @PathVariable Long id) {
        model.addAttribute("user", usersService.getUser(id));
        return "user/details";
    }

    @RequestMapping("/user/delete/{id}")
    public String delete(@PathVariable Long id) {
        usersService.deleteUser(id);
        return "redirect:/user/list";
    }

    @RequestMapping(value = "/user/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        User user = usersService.getUser(id);
        model.addAttribute("user", user);
        return "user/edit";
    }

    @RequestMapping(value = "/user/edit/{id}", method = RequestMethod.POST)
    public String setEdit(Model model, @PathVariable Long id, @ModelAttribute User user) {
        User originalUser = usersService.getUser(id);
        //modificar solo dni, name y lastname
        originalUser.setName(user.getName());
        originalUser.setLastName(user.getLastName());
        originalUser.setDni((user.getDni()));
        usersService.addUser(originalUser);
        return "redirect:/user/details/"+id;
    }


    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@Validated User user, BindingResult result) {
        signUpFormValidator.validate(user, result);
        if (result.hasErrors()) {
            return "signup";
        }
        user.setRole(rolesService.getRoles()[0]);
        usersService.addUser(user);
        securityService.autoLogin(user.getDni(), user.getPasswordConfirm());
        return "redirect:home";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        return "login";
    }
    @RequestMapping(value = { "/home" }, method = RequestMethod.GET)
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String dni = auth.getName();
        User activeUser = usersService.getUserByDni(dni);
        model.addAttribute("markList", activeUser.getMarks());
        return "home";
    }

    @RequestMapping("/user/list/update")
    public String updateList(Model model){
        model.addAttribute("userList",usersService.getUsers() );
        return "user/list :: tableUsers";
    }












}
