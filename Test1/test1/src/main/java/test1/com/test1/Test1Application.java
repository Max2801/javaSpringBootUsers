package test1.com.test1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan
@EnableJpaRepositories
public class Test1Application {

	public static void main(String[] args) {
		SpringApplication.run(Test1Application.class, args);
	}

}
