package com.example.medconnect.doctor;

import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public ResponseEntity<List<DoctorResponseDTO>> findAll() {
        return ResponseEntity.ok(doctorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.findById(id));
    }

    @PostMapping
    public ResponseEntity<DoctorResponseDTO> create(@Valid @RequestBody DoctorRequestDTO request) {
        return ResponseEntity.ok(doctorService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponseDTO> update(@PathVariable Long id, @Valid @RequestBody DoctorRequestDTO request) {
        return ResponseEntity.ok(doctorService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        doctorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
