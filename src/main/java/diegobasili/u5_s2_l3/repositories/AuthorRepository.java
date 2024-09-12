package diegobasili.u5_s2_l3.repositories;

import diegobasili.u5_s2_l3.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID> {
    boolean existsByEmail(String email);
}
