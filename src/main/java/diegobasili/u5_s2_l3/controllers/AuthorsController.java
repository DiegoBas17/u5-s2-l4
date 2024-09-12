package diegobasili.u5_s2_l3.controllers;

import diegobasili.u5_s2_l3.entities.Author;
import diegobasili.u5_s2_l3.services.AuthorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/authors")
public class AuthorsController {
    @Autowired
    private AuthorsService authorsService;

    // 1. GET http://localhost:3001/users (opzionalmente query params)
    @GetMapping
    public Page<Author> findAll(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "id") String sortBy){
        return this.authorsService.findAll(page, size, sortBy);
    }

    // 2. POST http://localhost:3001/users (+req.body)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Author save(@RequestBody Author body){
        return this.authorsService.saveAuthor(body);
    }

    // 3. GET http://localhost:3001/users/{userId}
    @GetMapping("/{userId}")
    public Author findById(@PathVariable UUID userId){
        //throw new RuntimeException("KABOOOOOOOOOOOOOOOOOOOOOOOOOOOM");
        return this.authorsService.findById(userId);
    }

    // 4. PUT http://localhost:3001/users/{userId} (+req.body)
    @PutMapping("/{userId}")
    public Author findByIdAndUpdate(@PathVariable UUID userId, @RequestBody Author body){
        return this.authorsService.findByIdAndUpdate(userId, body);
    }

    // 5. DELETE http://localhost:3001/users/{userId}
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID userId){
        this.authorsService.findByIdAndDelete(userId);
    }
}
