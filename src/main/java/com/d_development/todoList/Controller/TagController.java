package com.d_development.todoList.Controller;

import com.d_development.todoList.Controller.Exception.GeneralExceptionAndControllerAdvice.ListEmptyException;
import com.d_development.todoList.Entity.Tag;
import com.d_development.todoList.Services.Contract.TagService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestControllerAdvice
@RequestMapping("api/tag")
public class TagController {
    @Autowired
    private TagService tagService;

//-----------------------GetMappings-----------------------------------

    /* Return all tags */
    @GetMapping()
    public ResponseEntity<List<Tag>> findAllTags() {
        List<Tag> tags = tagService.findAll();
        if (tags.isEmpty()) throw new ListEmptyException("getAllTags", "Search of all tags");
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    /* Return a page's tag */
    @GetMapping("/page/{page}")
    public ResponseEntity<Page<Tag>> findAllTagPage(@PathVariable int page) {
        Page<Tag> tags = tagService.findAll(PageRequest.of(page, 10));
        if (tags.isEmpty()) throw new ListEmptyException("Tags", "Search of all tags");
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    /* Return a specific tag */
    @GetMapping("/searchID/{id}")
    public ResponseEntity<Tag> findTagById(@PathVariable Long id) {
        Tag tag = tagService.findById(id);

        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    /* Return number of tags */
    @GetMapping("/quantity")
    public ResponseEntity<Integer> quantityTag() {
        return new ResponseEntity<>(tagService.findAll().size(), HttpStatus.OK);
    }

//-----------------------PostMappings-----------------------------------

    /* Create and return a Tag */
    @PostMapping("/createTag")
    public ResponseEntity<Tag> createTag(@RequestBody @Valid Tag tag){
        Tag tagAux = tagService.save(tag);

        return new ResponseEntity<>(tagAux, HttpStatus.CREATED);
    }

//-----------------------PutMappings-----------------------------------

    /* Update a Tag */
    @PutMapping("/updateTag/{id}")
    public ResponseEntity<Tag> updateTag(@RequestBody @Valid Tag tag, @PathVariable long id) {
        Tag previousTag = tagService.findById(id);

        previousTag.setNameTag(tag.getNameTag());
        previousTag.setLabels(tag.getLabels());
        previousTag.setColor(tag.getColor());
        previousTag.setIconName(tag.getIconName());

        Tag tagSave = tagService.save(previousTag);

        return new ResponseEntity<>(tagSave, HttpStatus.CREATED);
    }


//-----------------------DeleteMappings-----------------------------------

    /* Delete tag by id */
    @DeleteMapping("/deleteTag/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable Long id) {
        tagService.findById(id);

        tagService.deleteById(id);
    }

    /* Delete tag by group id */
    @DeleteMapping("/deleteTag")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@RequestBody Long[] ids) {
        if ( ids != null && ids.length > 0){
            List<Long> longList = new ArrayList<>(Arrays.asList(ids));
            tagService.deleteAllByIds(longList);
        }else throw new ListEmptyException("Group of ids Tag", "Arrays ids");
    }

    /* Delete all tags */
    @DeleteMapping("/deleteAllTags")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllTags() {
        tagService.deleteAll();
    }
}
