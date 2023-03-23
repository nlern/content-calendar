package dev.danvega.contentcalendar.controller;

import dev.danvega.contentcalendar.model.Content;
import dev.danvega.contentcalendar.model.Status;
import dev.danvega.contentcalendar.repository.ContentRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/content")
@CrossOrigin
public class ContentController {
    //    private final ContentCollectionRepository repository;
    private final ContentRepository repository;

    public ContentController(ContentRepository repository) {
        this.repository = repository;
    }

    /**
     * Find all contents available.
     *
     * @return All available contents.
     */
    @GetMapping("")
    public List<Content> findAll() {
        return repository.findAll();
    }

    /**
     * Find content matching id.
     *
     * @param id Content id.
     * @return Content matching id.
     */
    @GetMapping("/{id}")
    public Optional<Content> findById(@PathVariable Integer id) {
        checkIfContentExistsById(id);

        return repository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public void create(@Valid @RequestBody Content content) {
        repository.save(content);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@PathVariable Integer id, @RequestBody Content content) {
        checkIfContentExistsById(id);
        repository.save(content);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        checkIfContentExistsById(id);
        repository.deleteById(id);
    }

    @GetMapping("/filter/{keyword}")
    public List<Content> findAllByTitle(@PathVariable String keyword) {
        return repository.findAllByTitleContains(keyword);
    }

    @GetMapping("/filter/status/{status}")
    public List<Content> findAllByStatus(@PathVariable Status status) {
        return repository.findAllByStatus(status);
    }

    private void checkIfContentExistsById(Integer id) throws ResponseStatusException {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Content not found"
            );
        }
    }
}
