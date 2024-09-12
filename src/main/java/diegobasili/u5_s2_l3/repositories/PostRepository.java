package diegobasili.u5_s2_l3.repositories;

import diegobasili.u5_s2_l3.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    boolean existsByTitle(String email);
    boolean existsByContent(String content);
}
