package dev.danvega.contentcalendar.repository;

import dev.danvega.contentcalendar.model.Content;
import dev.danvega.contentcalendar.model.Status;
import dev.danvega.contentcalendar.model.Type;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ContentCollectionRepository {

    private final List<Content> contentList = new ArrayList<>();

    public ContentCollectionRepository() {
    }

    public List<Content> findAll() {
        return contentList;
    }

    public Optional<Content> findById(Integer id) {
        return contentList.stream().filter(c -> c.id().equals(id)).findFirst();
    }

    @PostConstruct()
    private void init() {
        Content c = new Content(
                1,
                "My first blog post",
                "My first blog post",
                Status.IDEA,
                Type.ARTICLE,
                LocalDateTime.now(),
                null,
                ""
        );

        contentList.add(c);
    }

    public void add(Content content) {
        contentList.add(content);
    }

    public void update(Integer id, Content content) {
        contentList.set(id - 1, content);
    }

    public boolean existsById(Integer id) {
        return contentList.stream().filter(c -> c.id().equals(id)).count() == 1;
    }

    public void delete(Integer id) {
        contentList.removeIf(c -> c.id().equals(id));
    }
}
