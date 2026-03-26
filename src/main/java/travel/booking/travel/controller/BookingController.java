package travel.booking.travel.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import travel.booking.travel.dto.request.BookingRequest;
import travel.booking.travel.entity.Booking;
import travel.booking.travel.entity.BookingStatus;
import travel.booking.travel.service.BookingService;

import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT')")
    public ResponseEntity<List<Booking>> findAll(
            @RequestParam(required = false) BookingStatus status,
            @RequestParam(required = false) Long userId) {
        return ResponseEntity.ok(bookingService.findWithFilters(status, userId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('CUSTOMER')")
    public ResponseEntity<Booking> findById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    public ResponseEntity<Booking> create(@Valid @RequestBody BookingRequest request) {
        return ResponseEntity.ok(bookingService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT')")
    public ResponseEntity<Booking> update(@PathVariable Long id, @Valid @RequestBody BookingRequest request) {
        return ResponseEntity.ok(bookingService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookingService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/details")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('CUSTOMER')")
    public ResponseEntity<EntityModel<Map<String, Object>>> getBookingDetails(@PathVariable Long id) {
        Map<String, Object> details = bookingService.getBookingDetails(id);
        EntityModel<Map<String, Object>> model = EntityModel.of(details);
        model.add(linkTo(methodOn(BookingController.class).getBookingDetails(id)).withSelfRel());
        model.add(linkTo(methodOn(BookingController.class).findById(id)).withRel("booking"));
        return ResponseEntity.ok(model);
    }
}
