package org.example.lab2.service;

import org.example.lab2.dto.SimpleNameDto;
import org.example.lab2.entity.Lector;
import org.example.lab2.repository.LectorRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LectorService {
    private final LectorRepository repository;

    public LectorService(LectorRepository repository) {
        this.repository = repository;
    }

    public List<Lector> findAll(int page, int size) {
        return repository.findAll(PageRequest.of(page, size)).getContent();
    }

    public Lector findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new org.example.lab2.exception.ResourceNotFoundException("Lector not found"));
    }

    public Lector create(SimpleNameDto dto) {
        Lector lector = new Lector();
        lector.setFullName(dto.getName());
        return repository.save(lector);
    }

    public Lector update(Long id, SimpleNameDto dto) {
        Lector lector = findById(id);
        lector.setFullName(dto.getName());
        return repository.save(lector);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}

