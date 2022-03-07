package com.uniovi.notaneitor.controllers;

import com.uniovi.notaneitor.entities.User;
import com.uniovi.notaneitor.services.ProfessorsService;
import com.uniovi.notaneitor.validators.ProfessorAddValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProfessorsController {

    @Autowired
    private ProfessorAddValidator professorAddValidator;

    @Autowired //Inyectar el servicio
    private ProfessorsService professorService;

    @RequestMapping("/professor/list")
    public String getList(Model model) {
        model.addAttribute("professorList", professorService.getProfessors());
        return "professor/list";
    }

    @RequestMapping(value = "/professor/add", method = RequestMethod.POST)
    public String setProfessor(@Validated User user, BindingResult result) {
        professorAddValidator.validate(user, result);
        if(result.hasErrors()){
            return "/professor/add";
        } else {
            professorService.addProfessor(user);
            return "redirect:/professor/list";
        }
    }

    @RequestMapping(value = "/professor/add", method =RequestMethod.GET)
    public String getProfessor(Model model) {
        model.addAttribute("user", new User());
        return "professor/add";
    }

    @RequestMapping("/professor/details/{id}")
    public String getDetail(Model model, @PathVariable Long id) {
        model.addAttribute("professor", professorService.getProfessor(id));
        return "professor/details";
    }

    @RequestMapping("/professor/delete/{id}")
    public String deleteProfessor(@PathVariable long id){
        professorService.deleteProfessor(id);
        return "redirect:/professor/list";
    }



    @RequestMapping(value = "/professor/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        model.addAttribute("professor", professorService.getProfessor(id));
        return "professor/edit";
    }

    @RequestMapping(value="/professor/edit/{id}", method=RequestMethod.POST)
    public String setEdit(@ModelAttribute User professor, @PathVariable Long id){
        professor.setId(id);
        professorService.modifyProfessor(professor);
        return "redirect:/professor/details/"+id;
    }



}
