package org.example.lab2.service;

import org.example.lab2.dto.StudentDto;
import org.example.lab2.entity.Student;
import org.example.lab2.entity.StudentGroup;
import org.example.lab2.repository.StudentGroupRepository;
import org.example.lab2.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentService {
    private final StudentRepository repository;
    private final StudentGroupRepository groupRepository;

    public StudentService(StudentRepository repository, StudentGroupRepository groupRepository) {
        this.repository = repository;
        this.groupRepository = groupRepository;
    }

    public List<Student> findByGroupId(Long groupId) {
        return repository.findByGroupId(groupId);
    }

    public Student findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new org.example.lab2.exception.ResourceNotFoundException("Student not found"));
    }

    public Student create(StudentDto dto) {
        StudentGroup group = groupRepository.findById(dto.getGroupId())
                .orElseThrow(() -> new org.example.lab2.exception.ResourceNotFoundException("Group not found"));
        Student student = new Student();
        student.setFullName(dto.getFullName());
        student.setGroup(group);
        return repository.save(student);
    }

    public Student update(Long id, StudentDto dto) {
        Student student = findById(id);
        StudentGroup group = groupRepository.findById(dto.getGroupId())
                .orElseThrow(() -> new org.example.lab2.exception.ResourceNotFoundException("Group not found"));
        student.setFullName(dto.getFullName());
        student.setGroup(group);
        return repository.save(student);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}

