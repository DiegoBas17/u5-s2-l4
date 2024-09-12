package diegobasili.u5_s2_l3.services;

import diegobasili.u5_s2_l3.entities.Author;
import diegobasili.u5_s2_l3.exceptions.BadRequestException;
import diegobasili.u5_s2_l3.exceptions.NotFoundException;
import diegobasili.u5_s2_l3.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class AuthorsService {
   @Autowired
   private AuthorRepository authorRepository;

    public Page<Author> findAll(int page, int size, String sortBy) {
        if (page>20) page=20;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.authorRepository.findAll(pageable);
    }

    public Author saveAuthor(Author body) {
        // 1. Verifico che l'email non sia già stata utilizzata
        if (this.authorRepository.existsByEmail(body.getEmail())) {
            // 1.1 Se lo è triggero un errore (400 Bad Request)
            throw new BadRequestException("L'email " + body.getEmail() + " è già in uso!");
        } else {
            // 2. Se tutto è ok procedo con l'aggiungere campi 'server-generated' (nel nostro caso avatarURL)
            body.setAvatar("https://ui-avatars.com/api/?name="+body.getName()+"+"+body.getSurname());
        }
        // 3. Salvo lo User
        return this.authorRepository.save(body);
    }

    public Author findById(UUID authorId) {
        return this.authorRepository.findById(authorId).orElseThrow(()-> new NotFoundException(authorId));
    }

    public Author findByIdAndUpdate(UUID authorId, Author updateBody) {
        // 1. Verifico che l'email non sia già stata utilizzata
        if (this.authorRepository.existsByEmail(updateBody.getEmail())) {
            // 1.1 Se lo è triggero un errore (400 Bad Request)
            throw new BadRequestException("L'email " + updateBody.getEmail() + " è già in uso!");
        } else {
            Author found = findById(authorId);
            found.setName(updateBody.getName());
            found.setSurname(updateBody.getSurname());
            found.setEmail(updateBody.getEmail());
            found.setDateOfBirth(updateBody.getDateOfBirth());
            found.setAvatar("https://ui-avatars.com/api/?name="+updateBody.getName()+"+"+updateBody.getSurname());
            return found;
        }
    }

    public void findByIdAndDelete(UUID authorId) {
        Author found = findById(authorId);
        this.authorRepository.delete(found);
    }
}
