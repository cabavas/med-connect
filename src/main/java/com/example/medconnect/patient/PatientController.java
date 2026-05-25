package com.example.medconnect.patient;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> findAll() {
        return ResponseEntity.ok(patientService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> findById(@PathVariable Long id) {
      return ResponseEntity.ok(patientService.findById(id));
    }

    @PostMapping
    public ResponseEntity<PatientResponseDTO> create(@Valid @RequestBody PatientRequestDTO request) {
        return ResponseEntity.ok(patientService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> update(@PathVariable Long id, @Valid @RequestBody PatientRequestDTO requestDTO) {
        return ResponseEntity.ok(patientService.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        patientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
