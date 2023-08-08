package com.d_development.todoList.Controller;

import com.d_development.todoList.Controller.Exception.BDExcepcion.NotExistException;
import com.d_development.todoList.Controller.Exception.GeneralExceptionAndControllerAdvice.ListEmptyException;
import com.d_development.todoList.Controller.Exception.UserException.EmptyFieldException;
import com.d_development.todoList.Controller.Exception.UserException.IncorrectFieldException;
import com.d_development.todoList.Entity.Extra.Exclusion;
import com.d_development.todoList.Entity.Tag;
import com.d_development.todoList.Entity.User;
import com.d_development.todoList.Services.Contract.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("api/user")
@CrossOrigin(origins = {"http://localhost:4200"})
public class UserController {
    @Autowired
    private UserService userService;

//-----------------------GetMappings-----------------------------------

    /* Return all user */
    @GetMapping()
    public ResponseEntity<List<User>> findAllUsers() {
        List<User> users = userService.findAll();
        if (users.isEmpty()) throw new ListEmptyException("getAllUsers", "Search of all users");
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /* Return a page's user */
    @GetMapping("/page/{page}")
    public ResponseEntity<Page<User>> findAllUserPage(@PathVariable int page) {
        Page<User> users = userService.findAll(PageRequest.of(page,10));
        if (users.isEmpty()) throw new ListEmptyException("Users", "Search of all users");
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /* Return a specific user */
    @GetMapping("/searchID/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id){
        User user = userService.findById(id);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /* Returns all users that contenting the specified name */
    @GetMapping("/searchName/{name}")
    public ResponseEntity<List<User>> findByName(@PathVariable String name){
        List<User> users;
        Exclusion exclusions = new Exclusion();

        if (searchIfNotBeInExclusion(name, exclusions) != null) return searchIfNotBeInExclusion(name, exclusions);

        checkParam(name);

        users = userService.findUsersByNameContains(name);

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    private ResponseEntity<List<User>> searchIfNotBeInExclusion(String name, Exclusion exc) {
        List<User> users;
        try {
            if(!exc.getExcUserName().isEmpty()){
                Set<String> excUserName = exc.getExcUserName();

                for (String exclusion :
                        excUserName) {
                    if(exclusion.equals(name)) {
                        users = userService.findUsersByNameContains(name);
                        return new ResponseEntity<>(users, HttpStatus.OK);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private void checkParam(String name) {
        if(name == null || name.equals("")){
            throw new EmptyFieldException("name", name);
        }else if(name.matches(".*[^a-zA-Z0-9].*")){
            throw new IncorrectFieldException("name", name);
        }
    }

    /* Return number of users */
    @GetMapping("/quantity")
    public ResponseEntity<Integer> quantityUser() {
        return new ResponseEntity<>(userService.findAll().size(), HttpStatus.OK);
    }

    /* Return a specific tag of the user */
    @GetMapping("/tag/{idUser}+{nameTag}")
    public ResponseEntity<Tag> findTagById(@PathVariable Long idUser, @PathVariable String nameTag) {
        User user = userService.findById(idUser);

        checkParam(nameTag);

        Tag tagFound = user.getTags().stream()
                .filter(tag -> tag.getNameTag().equals(nameTag))
                .findFirst()
                .orElseThrow(() -> {
                    throw new NotExistException(nameTag, "Tag");
                });

        return new ResponseEntity<>(tagFound, HttpStatus.OK);
    }

    /* Return a specific tag of the user */
    @GetMapping("/tag/quantity/{idUser}")
    public ResponseEntity<Integer> quantityTagsUser(@PathVariable Long idUser) {
        User user = userService.findById(idUser);

        return new ResponseEntity<>(user.getTags().size(), HttpStatus.OK);
    }


//-----------------------PostMappings-----------------------------------

    /* Create and return a User */
    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody @Valid User user) throws IOException {
        User userAux;
        evaluateExclusionsWithNameUser(user);

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userAux = userService.save(user);

        return new ResponseEntity<>(userAux, HttpStatus.CREATED);
    }

    private void evaluateExclusionsWithNameUser(User user) throws IOException {
        boolean val = true;
        Exclusion exclusion = new Exclusion();

        if(exclusion.getExcUserName().stream().anyMatch(text -> text.equals(user.getName())))
            val = false;

        if(user.getName().matches(".*[^a-zA-Z0-9].*") && val){
            throw new IncorrectFieldException("name", user);
        }
    }

    @PostMapping("/exclusion")
    public ResponseEntity<HashMap<Integer, String>> createExclusion(@RequestBody String exclusionText) throws IOException {
        Exclusion exclusion = new Exclusion();

        exclusion.createExclusion(exclusionText);
        AtomicInteger i = new AtomicInteger(0);
        HashMap<Integer, String> exclusionConId = new HashMap<>();

        exclusion.getExcUserName().forEach(text-> exclusionConId.put(i.incrementAndGet(), text));

        return new ResponseEntity<>(exclusionConId, HttpStatus.CREATED);
    }

//-----------------------PutMappings-----------------------------------

    /* Update a User */
    @PutMapping("/updateUser/{id}")
    public ResponseEntity<User> updateUser(@RequestBody @Valid User user, @PathVariable long id) {
        User previousUser = userService.findById(id);

        previousUser.setEnabled(user.isEnabled());
        previousUser.setName(user.getName());
        previousUser.setTags(user.getTags());
        previousUser.setPassword(user.getPassword());
        previousUser.setImageName(user.getImageName());
        previousUser.setPriorities(user.getPriorities());
        previousUser.setRoles(user.getRoles());

        User userSave = userService.save(previousUser);

        return new ResponseEntity<>(userSave, HttpStatus.CREATED);
    }


//-----------------------DeleteMappings-----------------------------------

    /* Delete user by id */
    @DeleteMapping("/deleteUser/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.findById(id);

        userService.deleteById(id);
    }

    /* Delete all users */
    @DeleteMapping("/deleteAllUsers")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllUsers() {
        userService.deleteAll();
    }
}
