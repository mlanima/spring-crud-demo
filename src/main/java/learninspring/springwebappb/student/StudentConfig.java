package learninspring.springwebappb.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(
            StudentRepository repository
    ) {
        return args -> {
            Student miriam = new Student(
                    "Miriam",
                    "mariam@test.com",
                    LocalDate.of(2004, Month.JANUARY, 5),
                    20
            );

            Student alex = new Student(
                    "alex",
                    "alex@test.com",
                    LocalDate.of(2003, Month.MARCH, 7),
                    21
            );

            repository.saveAll(List.of(miriam, alex));
        };
    }
}
