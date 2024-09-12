package diegobasili.u5_s2_l3.controllers;

import diegobasili.u5_s2_l3.entities.Post;
import diegobasili.u5_s2_l3.entities.PostPayload;
import diegobasili.u5_s2_l3.services.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@RestController
@RequestMapping("/blogPosts")
public class PostsConntroller {
    @Autowired
    private PostsService postsService;

    // 1. GET http://localhost:3001/users (opzionalmente query params)
    @GetMapping
    public Page<Post> findAll(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "id") String sortBy){
        return this.postsService.findAll(page, size, sortBy);
    }

    // 2. POST http://localhost:3001/users (+req.body)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post save(@RequestBody PostPayload body){
        return this.postsService.savePost(body);
    }

    // 3. GET http://localhost:3001/users/{userId}
    @GetMapping("/{userId}")
    public Post findById(@PathVariable UUID userId){
        //throw new RuntimeException("KABOOOOOOOOOOOOOOOOOOOOOOOOOOOM");
        return this.postsService.findById(userId);
    }

    // 4. PUT http://localhost:3001/users/{userId} (+req.body)
    @PutMapping("/{userId}")
    public Post findByIdAndUpdate(@PathVariable UUID userId, @RequestBody PostPayload body){
        return this.postsService.findByIdAndUpdate(userId, body);
    }

    // 5. DELETE http://localhost:3001/users/{userId}
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID userId){
        this.postsService.findByIdAndDelete(userId);
    }
}
