package com.d_development.todoList.Controller;

import com.d_development.todoList.Controller.Exception.GeneralExceptionAndControllerAdvice.ListEmptyException;
import com.d_development.todoList.Entity.Label;
import com.d_development.todoList.Services.Contract.LabelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/label")
public class LabelController {
    @Autowired
    private LabelService labelService;

//-----------------------GetMappings-----------------------------------

    /* Return all labels */
    @GetMapping()
    public ResponseEntity<List<Label>> findAllLabels() {
        List<Label> labels = labelService.findAll();
        if (labels.isEmpty()) throw new ListEmptyException("getAllLabels", "Search of all labels");
        return new ResponseEntity<>(labels, HttpStatus.OK);
    }

    /* Return a page's label */
    @GetMapping("/page/{page}")
    public ResponseEntity<Page<Label>> findAllLabelPage(@PathVariable int page) {
        Page<Label> labels = labelService.findAll(PageRequest.of(page, 10));
        if (labels.isEmpty()) throw new ListEmptyException("Labels", "Search of all labels");
        return new ResponseEntity<>(labels, HttpStatus.OK);
    }

    /* Return a specific label */
    @GetMapping("/searchID/{id}")
    public ResponseEntity<Label> findLabelById(@PathVariable Long id) {
        Label label = labelService.findById(id);

        return new ResponseEntity<>(label, HttpStatus.OK);
    }

    /* Return number of labels */
    @GetMapping("/quantity")
    public ResponseEntity<Integer> quantityLabel() {
        return new ResponseEntity<>(labelService.findAll().size(), HttpStatus.OK);
    }

//-----------------------PostMappings-----------------------------------

    /* Create and return a Label */
    @PostMapping("/createLabel")
    public ResponseEntity<Label> createLabel(@RequestBody @Valid Label label){
        Label labelAux = labelService.save(label);

        return new ResponseEntity<>(labelAux, HttpStatus.CREATED);
    }

//-----------------------PutMappings-----------------------------------

    /* Update a Label */
    @PutMapping("/updateLabel/{id}")
    public ResponseEntity<Label> updateLabel(@RequestBody @Valid Label label, @PathVariable long id) {
        Label previousLabel = labelService.findById(id);

        previousLabel.setNameLabel(label.getNameLabel());
        previousLabel.setTasks(label.getTasks());
        previousLabel.setColor(label.getColor());
        previousLabel.setIconName(label.getIconName());
        previousLabel.setDescription(label.getDescription());

        Label labelSave = labelService.save(previousLabel);

        return new ResponseEntity<>(labelSave, HttpStatus.CREATED);
    }


//-----------------------DeleteMappings-----------------------------------

    /* Delete label by id */
    @DeleteMapping("/deleteLabel/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabel(@PathVariable Long id) {
        labelService.findById(id);

        labelService.deleteById(id);
    }

    /* Delete label by group id */
    @DeleteMapping("/deleteLabel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabel(@RequestBody Long[] ids) {
        if ( ids != null && ids.length > 0){

            List<Long> longList = new ArrayList<>(Arrays.asList(ids));

            labelService.deleteAllByIds(longList);

        }else throw new ListEmptyException("Group of ids Label", "Arrays ids");
    }

    /* Delete all labels */
    @DeleteMapping("/deleteAllLabels")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllLabels() {
        labelService.deleteAll();
    }
}
