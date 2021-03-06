package com.example.springbootgettutor.service;

import com.example.springbootgettutor.controller.vo.CourseGradeVO;
import com.example.springbootgettutor.entity.*;
import com.example.springbootgettutor.repository.CourseRepository;
import com.example.springbootgettutor.repository.DirectionRepository;
import com.example.springbootgettutor.repository.ElectiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class ClassService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ElectiveRepository electiveRepository;
    @Autowired
    private DirectionRepository directionRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ClassService classService;

    //---------"Course's CURD "-----------
    public Course addCourse(Course course){
        courseRepository.save(course);
        return course;
    }
    public Course addCourseWithTutor(Course course,int id){
        Tutor tutor = userService.getTutorById(id);
        course.setTutor(tutor);
        courseRepository.save(course);
        return course;
    }


    public void deleteCourse(int id){
        courseRepository.deleteById(id);
    }

    public Course updateCourse(Course course){
        courseRepository.save(course);
        return course;
    }


    public Course getCourse(String name,int tid){
        List<Course> courses = classService.listCourseByTutorID(tid);
        Course course = null;
        for (Course c : courses) {
            if (c.getName().equals(name)) {
                course = c;
            }
        }
        return course;

    }
    public Course getCourse(int id){
        return courseRepository.findById(id).orElse(null);
    }
    public Course getCourse(String name){
        return courseRepository.findByName(name).orElse(null);
    }
    public List<Course> listCourses(){
        return courseRepository.findAll();
    }
    public List<Course> listCourseByTutorID(int id){
        return courseRepository.getCourseByTutor(id).orElse(List.of());
    }


    //---------"Elective's CURD "-----------

    public Elective addElective(Elective elective) {
        electiveRepository.save(elective);
        return elective;
    }

    public void deleteElective(int id) {
        electiveRepository.deleteById(id);
    }

    public Elective getElective(int id) {
        return electiveRepository.findById(id)
                .orElse(null);
    }

    public List<Elective> getElectiveByCourseId(int id) {
        return electiveRepository.getElectivesCourseId(id).orElse(List.of());
    }

    public List<Elective> getElectiveByStudentId(int id) {
        return electiveRepository.getElectivesStudentId(id)
                .orElse(List.of());
    }

    public Elective getElectiveByStudentIdAndCourseId(int sid,int cid) {
        return electiveRepository.getElectivesByStudentIdAndCourseId(sid, cid)
                .orElse(null);
    }

    public List<Elective> getElectiveByStuIdAndTurId(int sid,int tid) {
        List<Elective> electives = new ArrayList<>();
        List<Course> courses = classService.listCourseByTutorID(tid);
        for(Course c:courses){
            Elective elective = classService.getElectiveByStudentIdAndCourseId(sid, c.getId());
            if(elective!=null){
                electives.add(elective);
            }
        }
        return electives;
    }

    public Elective updateElective(Elective elective) {
        electiveRepository.save(elective);
        return elective;
    }


    //---------"Direction's CURD "-----------
    public Direction addDirection(Direction direction) {
        directionRepository.save(direction);
        return direction;
    }

    public Direction updateDirection(Direction direction) {
        directionRepository.save(direction);
        return direction;
    }

    public void deleteDirection(int did) {
        directionRepository.deleteById(did);
    }

    public List<Direction> listDirections() {
        return directionRepository.findAll();
    }

    public Direction getDirection(int id){
        return directionRepository.findById(id).orElse(null);
    }


    public List<Direction> getDirections(int sid){
        return directionRepository.getDirectionByStudentId(sid).orElse(List.of());
    }


    //---------"Calculate the overall Grade ranking"-----------
    public boolean checkSettings(int tid){
        List<Course> courses = classService.listCourseByTutorID(tid);
        if (courses == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"You have not added any courses！");
        }
        courses.forEach(c->{
            if (c.getWeight()==0){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"You have a course that has not yet been weighted!");
            }
        });
        return true;
    }
    public float calculateWeightedGrade(int sid,int tid){
        checkSettings(tid);
        float WeightedGrade = 0;
        List<Elective> electives = classService.getElectiveByStuIdAndTurId(sid, tid);
        for(Elective ele :electives) {
            WeightedGrade += ele.getGrade() * ele.getCourse().getWeight();
        }
        return WeightedGrade;
    }

    public List<Student> RankStudents(int tid){
        List<Student> students = userService.listStudents();
        Map<Student, Float> studentGradeMap = new HashMap<>();
        students.forEach(s ->{
            float WeightedGrade = calculateWeightedGrade(s.getId(), tid);
            studentGradeMap.put(s, WeightedGrade);
            userService.updateStudentWeightGrade(s.getId(),WeightedGrade);

        });
        return studentGradeMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public List<Student> SelectStudents(int tid){
        Tutor tutor = userService.getTutorById(tid);
        List<Student> students = RankStudents(tid);
        log.debug("students.size():{} / tutor.getReservedRange():{} ", students.size(),tutor.getReservedRange());
        int ran = students.size() < tutor.getReservedRange() ? students.size() : tutor.getReservedRange();
        return students.subList(0, ran);

    }
    public boolean getQualification(int sid,int tid){
        boolean qualification = true;
        Student student = userService.getStudent(sid);
        List<Elective> electives = classService.getElectiveByStudentId(sid);
        Tutor tutor =  student.getTutor();
        // Determine if each course is above the LowestMark
        for (Elective elective : electives) {
            Course course = elective.getCourse();
            if (elective.getGrade() < course.getLowestMark()) {
                qualification = false;
            }
        }

        //Judge whether the students' comprehensive grades reach the standard
        List<Student> students = classService.SelectStudents(tid);
        if (!students.contains(student)) {
            qualification = false;
        }

        if(tutor!=null){
            qualification = false;
        }
        return qualification;
    }

    public boolean checkQualification(int sid,int tid){
        Student student = userService.getStudent(sid);
        List<Elective> electives = classService.getElectiveByStudentId(sid);
        Tutor tutor =  student.getTutor();
        // Determine if each course is above the LowestMark
        for (Elective elective : electives) {
            Course course = elective.getCourse();
            if (elective.getGrade() < course.getLowestMark()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Your " + course.getName() + " course grade is not up to standard, " +
                                "the teacher's standard score is " + course.getLowestMark() +
                                ". Please contact other teachers as soon as possible");
            }
        }

        //Judge whether the students' comprehensive grades reach the standard
        List<Student> students = classService.SelectStudents(tid);
        if (!students.contains(student)) {
            throw new  ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Your comprehensive score is not up to standard. Please contact other teachers as soon as possible");
        }

        if(tutor!=null){
            throw new  ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "You've already chosen a tutor, your tutor is " + tutor.getUser().getName() + ".");
        }

        return true;
    }

    //the grade of a student's assigned course set
    public List<CourseGradeVO> listGradeByCourses(List<Course> courses, int sid) {
        List<CourseGradeVO> courseGradeVOs = new ArrayList<>();
        courses.forEach(c -> {
                    CourseGradeVO cGVO = new CourseGradeVO();
                    Elective e = classService.getElectiveByStudentIdAndCourseId(sid, c.getId());
                    if(e !=null){
                        cGVO.setGrade(e.getGrade());
                    }
                    else {
                        cGVO.setGrade(0);
                    }
                    cGVO.setCourse(c);
                    cGVO.setSid(sid);
                    courseGradeVOs.add(cGVO);
                }
        );
        return courseGradeVOs;
    }

    public int getRankingIndex(List<Student> students,int sid){
        int rankIndex = 0;
        for (int i = 0; i < students.size(); i++) {
            if(students.get(i).getId() == sid)
                rankIndex = i+1;
        }
        return rankIndex;
    }

}
