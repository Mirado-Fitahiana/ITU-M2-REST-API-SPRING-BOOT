package travel.booking.travel.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import travel.booking.travel.dto.request.TripRequest;
import travel.booking.travel.entity.Trip;
import travel.booking.travel.service.TripService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @GetMapping
    public ResponseEntity<List<Trip>> findAll(
            @RequestParam(required = false) String destination,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {
        return ResponseEntity.ok(tripService.findWithFilters(destination, minPrice, maxPrice, startDate));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> findById(@PathVariable Long id) {
        return ResponseEntity.ok(tripService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT')")
    public ResponseEntity<Trip> create(@Valid @RequestBody TripRequest request) {
        return ResponseEntity.ok(tripService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT')")
    public ResponseEntity<Trip> update(@PathVariable Long id, @Valid @RequestBody TripRequest request) {
        return ResponseEntity.ok(tripService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tripService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/full")
    public ResponseEntity<EntityModel<Map<String, Object>>> getFullTrip(@PathVariable Long id) {
        Map<String, Object> fullTrip = tripService.getFullTrip(id);
        EntityModel<Map<String, Object>> model = EntityModel.of(fullTrip);
        model.add(linkTo(methodOn(TripController.class).getFullTrip(id)).withSelfRel());
        model.add(linkTo(methodOn(TripController.class).findById(id)).withRel("trip"));
        model.add(linkTo(methodOn(TripController.class).findAll(null, null, null, null)).withRel("allTrips"));
        return ResponseEntity.ok(model);
    }
}
