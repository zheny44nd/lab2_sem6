package org.example.lab2.service;

import org.example.lab2.dto.SimpleNameDto;
import org.example.lab2.entity.Discipline;
import org.example.lab2.repository.DisciplineRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DisciplineService {
    private final DisciplineRepository repository;

    public DisciplineService(DisciplineRepository repository) {
        this.repository = repository;
    }

    public List<Discipline> findAll() {
        return repository.findAll();
    }

    public Discipline findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new org.example.lab2.exception.ResourceNotFoundException("Discipline not found"));
    }

    public Discipline create(SimpleNameDto dto) {
        Discipline discipline = new Discipline();
        discipline.setName(dto.getName());
        return repository.save(discipline);
    }

    public Discipline update(Long id, SimpleNameDto dto) {
        Discipline discipline = findById(id);
        discipline.setName(dto.getName());
        return repository.save(discipline);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}

