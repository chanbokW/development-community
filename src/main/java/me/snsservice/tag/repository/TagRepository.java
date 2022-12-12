package me.snsservice.tag.repository;

import me.snsservice.tag.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findByNameIn(List<String> name);
    Optional<Tag> findByName(String name);
}
