package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tourists")
public class TouristController {

    @Autowired
    private TouristRepository repo;

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestHeader(value = "X-REQUEST-TOKEN",
                           required = false) String token,
            @RequestBody Tourist t) {

        if (!"HOTEL123".equals(token)) {
            return ResponseEntity.status(401)
                    .body("Invalid Token");
        }

        Tourist old = repo.findById(id).orElse(null);

        if (old == null) {
            return ResponseEntity.status(404)
                    .body("Tourist Not Found");
        }

        old.setName(t.getName());
        old.setContact(t.getContact());
        old.setVisitedDestination(t.getVisitedDestination());

        return ResponseEntity.ok(repo.save(old));
    }
}