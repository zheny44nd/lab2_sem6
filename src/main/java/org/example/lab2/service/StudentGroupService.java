package org.example.lab2.service;

import org.example.lab2.dto.SimpleNameDto;
import org.example.lab2.entity.StudentGroup;
import org.example.lab2.repository.StudentGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentGroupService {
    private final StudentGroupRepository repository;

    public StudentGroupService(StudentGroupRepository repository) {
        this.repository = repository;
    }

    public List<StudentGroup> findAll() {
        return repository.findAll();
    }

    public StudentGroup findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new org.example.lab2.exception.ResourceNotFoundException("Group not found"));
    }

    public StudentGroup create(SimpleNameDto dto) {
        StudentGroup group = new StudentGroup();
        group.setName(dto.getName());
        return repository.save(group);
    }

    public StudentGroup update(Long id, SimpleNameDto dto) {
        StudentGroup group = findById(id);
        group.setName(dto.getName());
        return repository.save(group);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}

