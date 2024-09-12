package diegobasili.u5_s2_l3.services;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import diegobasili.u5_s2_l3.entities.Author;
import diegobasili.u5_s2_l3.entities.Post;
import diegobasili.u5_s2_l3.exceptions.BadRequestException;
import diegobasili.u5_s2_l3.exceptions.NotFoundException;
import diegobasili.u5_s2_l3.payloads.PostDTO;
import diegobasili.u5_s2_l3.repositories.AuthorRepository;
import diegobasili.u5_s2_l3.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class PostsService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private Cloudinary cloudinary;

    public Page<Post> findAll(int page, int size, String sortBy) {
        if (page>20) page=20;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.postRepository.findAll(pageable);
    }

    public Post savePost(PostDTO body) {
        // 1. Verifico che l'email non sia già stata utilizzata
        if (this.postRepository.existsByTitle(body.title())&&this.postRepository.existsByContent(body.content())) {
            // 1.1 Se lo è triggero un errore (400 Bad Request)
            throw new BadRequestException("Questo libro: " + body.title() + " è già presente!");
        }
        Author author = authorRepository.findById(body.authorId()).orElseThrow(() -> new NotFoundException(body.authorId()));
        String cover = "https://ui-avatars.com/api/?name="+body.title()+"+"+body.category();
        Post newPost = new Post(body.category(), body.title(), cover, body.timeForRead(), body.content(), author);
        // 3. Salvo lo User
        return this.postRepository.save(newPost);
    }

    public Post findById(UUID postId) {
        return this.postRepository.findById(postId).orElseThrow(()-> new NotFoundException(postId));
    }

    public Post findByIdAndUpdate(UUID postId, PostDTO updateBody) {
        // 1. Verifico che l'email non sia già stata utilizzata
        if (this.postRepository.existsByTitle(updateBody.title())&&this.postRepository.existsByContent(updateBody.content())) {
            // 1.1 Se lo è triggero un errore (400 Bad Request)
            throw new BadRequestException("Questo libro: " + updateBody.title() + " è già presente!");
        }
        Post found = findById(postId);
            found.setCategory(updateBody.category());
            found.setTitle(updateBody.title());
            found.setTimeForRead(updateBody.timeForRead());
        Author author = authorRepository.findById(updateBody.authorId()).orElseThrow(() -> new NotFoundException(updateBody.authorId()));
            found.setAuthor(author);
            return found;
    }

    public void findByIdAndDelete(UUID postId) {
        Post found = findById(postId);
        this.postRepository.delete(found);
    }

    public Post uploadImage(UUID postId, MultipartFile file) throws IOException {
        Post post = findById(postId);
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        post.setCover(url);
        postRepository.save(post);
       return post;
    }
}
