package uz.pdp.cache_simple_task2.service;

import org.springframework.lang.NonNull;
import uz.pdp.cache_simple_task2.dto.StudentCreateDTO;
import uz.pdp.cache_simple_task2.dto.StudentUpdateDTO;
import uz.pdp.cache_simple_task2.entity.Student;

import java.util.List;

public interface StudentService {
    Student createStudent(@NonNull StudentCreateDTO studentCreateDTO);
    Student getStudent(@NonNull Long id);
    List<Student> getStudentAll();
    Student updateStudent(@NonNull StudentUpdateDTO studentUpdateDTO);
    void deleteStudent(@NonNull Long id);
}
