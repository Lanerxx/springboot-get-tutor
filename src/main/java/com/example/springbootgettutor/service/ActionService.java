package com.example.springbootgettutor.service;

import com.example.springbootgettutor.entity.Course;
import com.example.springbootgettutor.entity.Elective;
import com.example.springbootgettutor.entity.Student;
import com.example.springbootgettutor.entity.Tutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ActionService {
    @Autowired
    private InformationService informationService;
    @Autowired
    private ActionService actionService;


    /**
     * @describtion: Ranking students according to their grades
     * @param: [tutor]
     * @return: java.util.Map<entity.Student,java.lang.Float>
     */
    public Map<Student, Float> rankStudents(Tutor tutor){
        List<Elective> electives = new ArrayList<Elective>();
        List<Student> students = informationService.getAllStudents();
        List<Course> courses = informationService.getCourseByTutor(tutor.getId());

        for (Course c :courses){
            //Selecting electives according the tutor taught courses
            Elective elective = (Elective) informationService.getElectivesByCourse(c.getId());
            //Conversion of common to weighted grades
            float weightedGrade = elective.getGrade() * c.getIntentionWeight();
            elective.setGrade(weightedGrade);
            electives.add(elective);
        }

        //Initializing students and their corresponding total weighted grates
        Map<Student, Float> mapWeightedStudent = new HashMap<Student, Float>();
        for(Student s:students){
            mapWeightedStudent.put(s, (float) 0);
        }

        //Calculating students and their corresponding total weighted grates
        for (Elective e:electives){
            float weightedGrade = mapWeightedStudent.get(e.getStudent()) + e.getGrade();
            mapWeightedStudent.put(e.getStudent(),weightedGrade);
        }

        //Ranking students according to their corresponding total weighted grates
        Map<Student, Float> sortedStudent = actionService.mapSortedByValue(mapWeightedStudent,false);
        return sortedStudent;
    }

    /**
     * @describtion: Screening students according to the maximum number of people the tutor can direct
     * @param: [tutor]
     * @return: java.util.Map<entity.Student,java.lang.Float>
     */

    public Map<Student, Float> screenStudents(Map<Student, Float> sortedStudent,Tutor tutor){
        int sumCount = tutor.getSumCount();
        List<Map.Entry<Student,Float>> screenStudents = new ArrayList<Map.Entry<Student, Float>>(sortedStudent.entrySet());
        for(Map.Entry<Student,Float> set:screenStudents.subList(0, sumCount)){
            sortedStudent.put(set.getKey(), set.getValue());
        }
        return  screenStudents
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    /**
     * @describtion: Sorting in Stream form and convert to map sets
     * @param: [paramMap, isDesc]
     * @return: java.util.Map<entity.Student,java.lang.Float>
     */
    public static Map<Student, Float> mapSortedByValue(Map<Student, Float> paramMap, boolean isDesc) {
        if (isDesc) {
            return paramMap
                    .entrySet()
                    .stream()
                    .sorted(Map.Entry.<Student, Float>comparingByValue().reversed())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> x, LinkedHashMap::new));
        } else {
            return paramMap
                    .entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> x, LinkedHashMap::new));
        }
    }

}
