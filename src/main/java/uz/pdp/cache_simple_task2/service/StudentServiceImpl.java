package uz.pdp.cache_simple_task2.service;

import lombok.SneakyThrows;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.cache_simple_task2.dto.StudentCreateDTO;
import uz.pdp.cache_simple_task2.dto.StudentUpdateDTO;
import uz.pdp.cache_simple_task2.entity.Student;
import uz.pdp.cache_simple_task2.mapper.StudentMapper;
import uz.pdp.cache_simple_task2.repository.StudentRepository;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper, CacheManager cacheManager) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    @Override
    @Transactional
    public Student createStudent(@NonNull StudentCreateDTO studentCreateDTO) {
        Student student = studentMapper.toEntity(studentCreateDTO);
        return studentRepository.save(student);
    }

    @Override
    @SneakyThrows
    @Cacheable(value = "students", key = "#id")
    public Student getStudent(@NonNull Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found: " + id));
        TimeUnit.SECONDS.sleep(1);
        return student;
    }

    @Override
    @Cacheable(value = "students", key = "#root.methodName")
    public List<Student> getStudentAll() {
        return studentRepository.findAll();
    }


    @Override
    @CachePut(value = "students", key = "#studentUpdateDTO.id")
    public Student updateStudent(@NonNull StudentUpdateDTO studentUpdateDTO) {
        Student student = getStudent(studentUpdateDTO.getId());
        if (studentUpdateDTO.getName() != null) {
            student.setName(studentUpdateDTO.getName());
        }
        student.setAge(studentUpdateDTO.getAge());
        return studentRepository.save(student);
    }

    @Override
    @CacheEvict(value = "students", key = "#id")
    public void deleteStudent(@NonNull Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found: " + id));
        studentRepository.deleteById(student.getId());
    }
}
