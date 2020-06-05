package com.example.springbootgettutor.controller;

import com.example.springbootgettutor.component.RequestComponent;
import com.example.springbootgettutor.entity.Course;
import com.example.springbootgettutor.entity.Elective;
import com.example.springbootgettutor.entity.Student;
import com.example.springbootgettutor.entity.Tutor;
import com.example.springbootgettutor.service.ClassService;
import com.example.springbootgettutor.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/student/")
public class StudentController {
    @Autowired
    private UserService userService;
    @Autowired
    private ClassService classService;
    @Autowired
    private RequestComponent requestComponent;

    /**
     * @describtion: Student level 1 page -> welcome
     * 1. Display welcome student information
     * 2. Display all mentors
     * 3. Front-end rendering optional or not optional (Tutor)
     * @param:
     * @return:
     */
    @GetMapping("index")
    public Map getIndex(){
        Student student = userService.getStudent(requestComponent.getUid());
        List<Tutor> tutors = userService.listTutors();
        return Map.of(
                "student",student,
                "tutors",tutors
        );
    }

    /**
     * @describtion: Student level 2 page -> Specific tutor course information
     * 1. All subjects offered by the teacher
     * 2. The lowest score in the subject The teacher set
     * 3. Students' scores for each subject
     * 4. Student's overall score ranking
     * 5. Front-end rendering standard and substandard (grade)
     * @param:
     * @return:
     */
    @GetMapping("information/{tid}")
    public Map getInformation(@PathVariable int tid){
        Tutor tutor = userService.getTutorById(tid);
        List<Course> courses = classService.listCourseByTutorID(tid);
        List<Elective> electives = classService.getElectiveByStuIdAndTurId(requestComponent.getUid(),tid);
        return Map.of(
                "courses",courses,
                "electives",electives
        );
    }

    /**
     * @describtion: Student level 3 page ->Fill in the application form
     * 1. Fill in the application information for choosing a tutor
     * @param:
     * @return:
     */
    @GetMapping("Tutor/{tid}")
    public Map getTutor(@PathVariable int tid){
        String massage = "Sorry, your application failed";
        Tutor tutor = userService.getTutorById(tid);
        if(tutor.getQuantity()>=tutor.getRanges()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Sorry, there is no vacancy for the teacher, please contact other teachers immediately.");
        }

        if(!classService.checkQualification(requestComponent.getUid(),tid)){
            Student student = userService.getStudent(requestComponent.getUid());
            student.setTutor(tutor);
            int quan = tutor.getQuantity();
            tutor.setQuantity(quan+1);
            userService.updateTutor(tutor);
            userService.updateStudent(student);
            massage = "Congratulations, your application has been successful";
        }
        return Map.of(
                "massage",massage
        );

    }


}
