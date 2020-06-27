package com.example.springbootgettutor.controller;

import com.example.springbootgettutor.component.RequestComponent;
import com.example.springbootgettutor.controller.vo.CourseGradeVO;
import com.example.springbootgettutor.entity.*;
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
                "tutors", tutors.subList(1, tutors.size())
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
        boolean qualified = false;
        Student student = userService.getStudent(requestComponent.getUid());
        Tutor tutor = userService.getTutorById(tid);
        List<CourseGradeVO> courseGradeVOS = classService.listGradeByCourses(classService.listCourseByTutorID(tid), requestComponent.getUid());
        List<Student> students = classService.RankStudents(tid);
        int rankingIndex = classService.getRankingIndex(students, requestComponent.getUid());
        qualified = classService.getQualification(requestComponent.getUid(),tid);
        return Map.of(
                "courseGradeVOS",courseGradeVOS,
                "students",students,
                "qualified",qualified,
                "tutor",tutor,
                "student",student,
                "rankingIndex",rankingIndex
        );
    }

    /**
     * @describtion: Student level 3 page ->Fill in the application form
     * 1. Fill in the application information for choosing a tutor
     * @param:
     * @return:
     */
    @GetMapping("directions")
    public Map listDirections(){
        return Map.of(
                "directions",classService.listDirections()
        );
    }
    @GetMapping("{sid}/directions")
    public Map getDirection(@PathVariable int sid){
        return Map.of(
                "direction",classService.getDirections(sid)
        );
    }
    @PatchMapping("directions")
    public Map updateStudentDirections(@RequestBody List<Direction> newDirections) {
        Student student = userService.getStudent(requestComponent.getUid());
        List<Direction> oldDirections = student.getDirections();
        oldDirections.forEach(direction -> {
            classService.deleteDirection(direction.getId());
        });
        newDirections.forEach(direction -> {
            direction.setStudent(student);
            classService.addDirection(direction);
        });
        return Map.of(
                "directions",newDirections
        );
    }

    @GetMapping("tutor/{tid}")
    public Map getTutor(@PathVariable int tid){
        String massage = "Sorry, your application failed";
        Tutor tutor = userService.getTutorById(tid);
        if(tutor.getQuantity()>=tutor.getRanges()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Sorry, there is no vacancy for the teacher, please contact other teachers immediately.");
        }
        if(classService.checkQualification(requestComponent.getUid(),tid)){
            int quan = userService.getStudentsByTutorId(tid).size();
            userService.getStudentsByTutorId(tid).forEach(s->log.debug("{}", s.getUser().getName()));
            log.debug("{}", userService.getStudentsByTutorId(tid).size());
            tutor.setQuantity(quan+1);
            userService.updateTutor(tutor);
            Student student = userService.getStudent(requestComponent.getUid());
            student.setTutor(tutor);
            userService.updateStudent(student);
            massage = "Congratulations, your application has been successful";
        }
        Student student = userService.getStudent(requestComponent.getUid());
        return Map.of(
                "student",student
        );

    }


}
