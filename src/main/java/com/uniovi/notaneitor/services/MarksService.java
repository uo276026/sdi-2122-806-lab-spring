package com.uniovi.notaneitor.services;

import com.uniovi.notaneitor.entities.Mark;
import com.uniovi.notaneitor.repositories.MarksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class MarksService {

    @Autowired
    private MarksRepository marksRepository;

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
    public Mark getMark(Long id) {
        //return marksList.stream()
        //        .filter(mark -> mark.getId().equals(id)).findFirst().get();
        return marksRepository.findById(id).get();
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

}
