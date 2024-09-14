package learninspring.springwebappb.student;

import learninspring.springwebappb.student.exception.EmailAlreadyTakeException;
import learninspring.springwebappb.student.exception.StudentNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {


    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow( () -> new StudentNotExistException("Student not found"));
    }

    public void addNewStudent(Student student) throws EmailAlreadyTakeException {
        Optional<Student> studentOptional = studentRepository
                .findByEmail(student.getEmail());

        if (studentOptional.isPresent()) {
            throw new EmailAlreadyTakeException("Email already taken");
        }

        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        if (studentRepository.existsById(studentId)) {
            studentRepository.deleteById(studentId);
        } else {
            throw new StudentNotExistException("Student doesn't exist");
        }
     }

//     @Transactional
//    public void updateStudent(Long studentId, String name, String email) {
//
//        Student student = studentRepository.findById(studentId).orElseThrow(
//                () -> new StudentNotExistException("Student with id " + studentId +" doesn't exist")
//        );
//
//        if (name != null &&
//                !name.isEmpty() &&
//                !Objects.equals(student.getName(), name)) {
//            student.setName(name);
//        }
//
//        if (email != null &&
//                !email.isEmpty() &&
//                !Objects.equals(student.getEmail(), email) ) {
//            Optional<Student> studentOptional = studentRepository.findByEmail(email);
//
//            if (studentOptional.isPresent()) {
//                throw new EmailAlreadyTakeException("Email already taken");
//            }
//
//            student.setEmail(email);
//
//         }
//    }

    @Transactional
    public void updateStudent(Long studentId, Student student) {
        Student studentToUpdate = studentRepository.findById(studentId).orElseThrow(
                () -> new StudentNotExistException("Student with id " + studentId +" doesn't exist")
        );

        if (student.getName() != null &&
                !student.getName().isEmpty() &&
                !Objects.equals(student.getName(), studentToUpdate.getName()) ){
            studentToUpdate.setName(student.getName());
        }

        if (student.getEmail() != null &&
                !student.getEmail().isEmpty() &&
                !Objects.equals(studentToUpdate.getEmail(), student.getEmail()) ) {
            Optional<Student> studentOptional = studentRepository.findByEmail(student.getEmail());

            if (studentOptional.isPresent()) {
                throw new EmailAlreadyTakeException("Email already taken");
            }

            studentToUpdate.setEmail(student.getEmail());

        }
    }
}
