package com.d_development.todoList.Controller;

import com.d_development.todoList.Controller.Exception.GeneralExceptionAndControllerAdvice.ListEmptyException;
import com.d_development.todoList.Entity.Task;
import com.d_development.todoList.Services.Contract.TaskService;
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


@RestControllerAdvice
@RequestMapping("api/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

//-----------------------GetMappings-----------------------------------

    /* Return all tasks */
    @GetMapping()
    public ResponseEntity<List<Task>> findAllTasks() {
        List<Task> tasks = taskService.findAll();
        if (tasks.isEmpty()) throw new ListEmptyException("getAllTasks", "Search of all tasks");
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    /* Return a page's task */
    @GetMapping("/page/{page}")
    public ResponseEntity<Page<Task>> findAllTaskPage(@PathVariable int page) {
        Page<Task> tasks = taskService.findAll(PageRequest.of(page, 10));
        if (tasks.isEmpty()) throw new ListEmptyException("Tasks", "Search of all tasks");
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    /* Return a specific task */
    @GetMapping("/searchID/{id}")
    public ResponseEntity<Task> findTaskById(@PathVariable Long id) {
        Task task = taskService.findById(id);

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    /* Return number of tasks */
    @GetMapping("/quantity")
    public ResponseEntity<Integer> quantityTask() {
        return new ResponseEntity<>(taskService.findAll().size(), HttpStatus.OK);
    }

//-----------------------PostMappings-----------------------------------

    /* Create and return a Task */
    @PostMapping("/createTask")
    public ResponseEntity<Task> createTask(@RequestBody @Valid Task task){
        Task taskAux = taskService.save(task);

        return new ResponseEntity<>(taskAux, HttpStatus.CREATED);
    }

//-----------------------PutMappings-----------------------------------

    /* Update a Task */
    @PutMapping("/updateTask/{id}")
    public ResponseEntity<Task> updateTask(@RequestBody @Valid Task task, @PathVariable long id) {
        Task previousTask = taskService.findById(id);

        previousTask.setContent(task.getContent());
        previousTask.setDone(task.isDone());

        Task taskSave = taskService.save(previousTask);

        return new ResponseEntity<>(taskSave, HttpStatus.CREATED);
    }


//-----------------------DeleteMappings-----------------------------------

    /* Delete task by id */
    @DeleteMapping("/deleteTask/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        taskService.findById(id);

        taskService.deleteById(id);
    }

    /* Delete task by group id */
    @DeleteMapping("/deleteTask")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@RequestBody Long[] ids) {
        if ( ids != null && ids.length > 0){
            List<Long> longList = new ArrayList<>(Arrays.asList(ids));
            taskService.deleteAllByIds(longList);
        }else throw new ListEmptyException("Group of ids Task", "Arrays ids");
    }

    /* Delete all tasks */
    @DeleteMapping("/deleteAllTasks")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllTasks() {
        taskService.deleteAll();
    }
}
