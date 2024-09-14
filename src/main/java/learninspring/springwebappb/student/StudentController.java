package learninspring.springwebappb.student;

import jakarta.validation.Valid;
import learninspring.springwebappb.student.exception.EmailAlreadyTakeException;
import learninspring.springwebappb.student.exception.StudentNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping(path = "{studentId}")
    public Student findStudentById(@PathVariable Long studentId) {
        return studentService.getStudentById(studentId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Student> registerNewStudent(@Valid @RequestBody Student student) {
        studentService.addNewStudent(student);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(student.getId())
                .toUri();
        return ResponseEntity.created(location).body(student);
    }


    @DeleteMapping(path = "{studentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable Long studentId) {
        studentService.deleteStudent(studentId);
    }

    @PutMapping(path = "{studentId}" )
//    public Student updateStudent(
//            @PathVariable("studentId") Long studentId,
//            @RequestBody(required = false) String name,
//            @RequestBody(required = false) String email
//    ) {
//        studentService.updateStudent(studentId, name, email);
//
//        return studentService.getStudentById(studentId);
//    }
    public Student updateStudent(@PathVariable Long studentId, @Valid @RequestBody Student student) {
        studentService.updateStudent(studentId ,student);

        return studentService.getStudentById(studentId);
    }


}
