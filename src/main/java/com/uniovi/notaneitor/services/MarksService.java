package com.uniovi.notaneitor.services;

import com.uniovi.notaneitor.entities.Mark;
import com.uniovi.notaneitor.repositories.MarksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class MarksService {

    @Autowired
    private MarksRepository marksRepository;

    /* Example of Constructor-Based Dependency Injection*/
    private final HttpSession httpSession;

    public MarksService(HttpSession httpSession) {
        this.httpSession = httpSession;
    }


    //private List<Mark> marksList = new LinkedList<>();
    //@PostConstruct
    //public void init() {
    //    marksList.add(new Mark(1L, "Ejercicio 1", 10.0));
    //    marksList.add(new Mark(2L, "Ejercicio 2", 9.0));
    //}

    public List<Mark> getMarks() {
        //return marksList;
        List<Mark> marks = new ArrayList<Mark>();
        marksRepository.findAll().forEach(marks::add);
        return marks;
    }

    public Mark getMark(Long id){
        Set<Mark> consultedList = (Set<Mark>) httpSession.getAttribute("consultedList");
        if ( consultedList == null ) {
            consultedList = new HashSet<Mark>();
        }
        Mark obtainedMark = marksRepository.findById(id).get();
        consultedList.add(obtainedMark);
        httpSession.setAttribute("consultedList", consultedList);
        return obtainedMark;
        //return marksRepository.findById(id).get();
    }

    public void addMark(Mark mark) {
        // Si en Id es null le asignamos el ultimo + 1 de la lista
        marksRepository.save(mark);
        //if (mark.getId() == null) {
        //    mark.setId(marksList.get(marksList.size() - 1).getId() + 1);
        //}
        //marksList.add(mark);
    }
    public void deleteMark(Long id) {
       //marksList.removeIf(mark -> mark.getId().equals(id));
        marksRepository.deleteById(id);

    }
    public void setMarkResend(boolean revised, Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String dni = auth.getName();
        Mark mark = marksRepository.findById(id).get();
        if(mark.getUser().getDni().equals(dni) ) {
            marksRepository.updateResend(revised, id);
        }

    }




}
