package diegobasili.u5_s2_l3.services;

import diegobasili.u5_s2_l3.entities.Author;
import diegobasili.u5_s2_l3.entities.Post;
import diegobasili.u5_s2_l3.entities.PostPayload;
import diegobasili.u5_s2_l3.exceptions.BadRequestException;
import diegobasili.u5_s2_l3.exceptions.NotFoundException;
import diegobasili.u5_s2_l3.repositories.AuthorRepository;
import diegobasili.u5_s2_l3.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PostsService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private AuthorRepository authorRepository;

    public Page<Post> findAll(int page, int size, String sortBy) {
        if (page>20) page=20;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.postRepository.findAll(pageable);
    }

    public Post savePost(PostPayload body) {
        // 1. Verifico che l'email non sia già stata utilizzata
        if (this.postRepository.existsByTitle(body.getTitle())&&this.postRepository.existsByContent(body.getContent())) {
            // 1.1 Se lo è triggero un errore (400 Bad Request)
            throw new BadRequestException("Questo libro: " + body.getTitle() + " è già presente!");
        }
        Author author = authorRepository.findById(body.getAuthor_id()).orElseThrow(() -> new NotFoundException(body.getAuthor_id()));
        Post newPost = new Post(body.getCategory(), body.getTitle(), body.getCover(), body.getTimeForRead(), body.getContent(), author);
        // 3. Salvo lo User
        return this.postRepository.save(newPost);
    }

    public Post findById(UUID postId) {
        return this.postRepository.findById(postId).orElseThrow(()-> new NotFoundException(postId));
    }

    public Post findByIdAndUpdate(UUID postId, PostPayload updateBody) {
        // 1. Verifico che l'email non sia già stata utilizzata
        if (this.postRepository.existsByTitle(updateBody.getTitle())&&this.postRepository.existsByContent(updateBody.getContent())) {
            // 1.1 Se lo è triggero un errore (400 Bad Request)
            throw new BadRequestException("Questo libro: " + updateBody.getTitle() + " è già presente!");
        }
        Post found = findById(postId);
            found.setCategory(updateBody.getCategory());
            found.setTitle(updateBody.getTitle());
            found.setCover(updateBody.getCover());
            found.setTimeForRead(updateBody.getTimeForRead());
        Author author = authorRepository.findById(updateBody.getAuthor_id()).orElseThrow(() -> new NotFoundException(updateBody.getAuthor_id()));
            found.setAuthor(author);
            return found;
    }

    public void findByIdAndDelete(UUID postId) {
        Post found = findById(postId);
        this.postRepository.delete(found);
    }
}
