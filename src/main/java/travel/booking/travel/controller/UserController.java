package travel.booking.travel.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import travel.booking.travel.dto.request.RegisterRequest;
import travel.booking.travel.entity.User;
import travel.booking.travel.service.PaymentService;
import travel.booking.travel.service.UserService;

import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PaymentService paymentService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.username")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> create(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> update(@PathVariable Long id, @Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/dashboard")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    public ResponseEntity<EntityModel<Map<String, Object>>> getDashboard(@PathVariable Long id) {
        Map<String, Object> dashboard = userService.getDashboard(id);
        EntityModel<Map<String, Object>> model = EntityModel.of(dashboard);
        model.add(linkTo(methodOn(UserController.class).getDashboard(id)).withSelfRel());
        model.add(linkTo(methodOn(UserController.class).findById(id)).withRel("user"));
        model.add(linkTo(methodOn(UserController.class).getPaymentSummary(id)).withRel("paymentSummary"));
        return ResponseEntity.ok(model);
    }

    @GetMapping("/{id}/payments/summary")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    public ResponseEntity<EntityModel<Map<String, Object>>> getPaymentSummary(@PathVariable Long id) {
        Map<String, Object> summary = paymentService.getUserPaymentSummary(id);
        EntityModel<Map<String, Object>> model = EntityModel.of(summary);
        model.add(linkTo(methodOn(UserController.class).getPaymentSummary(id)).withSelfRel());
        model.add(linkTo(methodOn(UserController.class).findById(id)).withRel("user"));
        return ResponseEntity.ok(model);
    }
}
