package diegobasili.u5_s2_l3.controllers;

import diegobasili.u5_s2_l3.entities.Post;
import diegobasili.u5_s2_l3.entities.PostPayload;
import diegobasili.u5_s2_l3.exceptions.BadRequestException;
import diegobasili.u5_s2_l3.payloads.PostDTO;
import diegobasili.u5_s2_l3.payloads.PostRespDTO;
import diegobasili.u5_s2_l3.services.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public PostRespDTO save(@RequestBody @Validated PostDTO body, BindingResult validationResult){
        if(validationResult.hasErrors())  {
            // Se ci sono stati errori lanciamo un'eccezione custom
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));

            throw new BadRequestException("Ci sono stati errori nel payload. " + messages);
        } else {
            // Se non ci sono stati salviamo l'utente

            return new PostRespDTO(this.postsService.savePost(body).getId());
        }
    }

    // 3. GET http://localhost:3001/users/{userId}
    @GetMapping("/{userId}")
    public Post findById(@PathVariable UUID userId){
        //throw new RuntimeException("KABOOOOOOOOOOOOOOOOOOOOOOOOOOOM");
        return this.postsService.findById(userId);
    }

    // 4. PUT http://localhost:3001/users/{userId} (+req.body)
    @PutMapping("/{userId}")
    public Post findByIdAndUpdate(@PathVariable UUID userId, @RequestBody PostDTO body, BindingResult validationResult){
        if(validationResult.hasErrors())  {
            // Se ci sono stati errori lanciamo un'eccezione custom
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));

            throw new BadRequestException("Ci sono stati errori nel payload. " + messages);
        } else {
            // Se non ci sono stati salviamo l'utente

            return this.postsService.findByIdAndUpdate(userId, body);
        }

    }

    // 5. DELETE http://localhost:3001/users/{userId}
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID userId){
        this.postsService.findByIdAndDelete(userId);
    }

    @PutMapping("/{postId}/cover")
    public Post uploadAvatar(@PathVariable UUID postId, @RequestParam("cover") MultipartFile image) throws IOException {
        // "avatar" deve corrispondere ESATTAMENTE come il campo del FormData che ci invia il Frontend
        // Se non corrisponde non troverò il file
        return this.postsService.uploadImage(postId, image);
    }
}
