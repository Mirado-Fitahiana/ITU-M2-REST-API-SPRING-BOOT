package travel.booking.travel.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import travel.booking.travel.dto.request.DestinationRequest;
import travel.booking.travel.entity.Destination;
import travel.booking.travel.service.DestinationService;

import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/destinations")
@RequiredArgsConstructor
public class DestinationController {

    private final DestinationService destinationService;

    @GetMapping
    public ResponseEntity<List<Destination>> findAll() {
        return ResponseEntity.ok(destinationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Destination> findById(@PathVariable Long id) {
        return ResponseEntity.ok(destinationService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Destination> create(@Valid @RequestBody DestinationRequest request) {
        return ResponseEntity.ok(destinationService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Destination> update(@PathVariable Long id, @Valid @RequestBody DestinationRequest request) {
        return ResponseEntity.ok(destinationService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        destinationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/stats")
    public ResponseEntity<EntityModel<Map<String, Object>>> getStats(@PathVariable Long id) {
        Map<String, Object> stats = destinationService.getStats(id);
        EntityModel<Map<String, Object>> model = EntityModel.of(stats);
        model.add(linkTo(methodOn(DestinationController.class).getStats(id)).withSelfRel());
        model.add(linkTo(methodOn(DestinationController.class).findById(id)).withRel("destination"));
        model.add(linkTo(methodOn(DestinationController.class).findAll()).withRel("allDestinations"));
        return ResponseEntity.ok(model);
    }
}
