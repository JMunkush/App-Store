package kz.springboot.springbootdemo;

import kz.springboot.springbootdemo.entities.Size;
import kz.springboot.springbootdemo.repositories.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.jar.Attributes;
import java.util.jar.Manifest;

@SpringBootApplication
public class SpringbootdemoApplication {
	@Autowired
	SizeRepository sizeRepository;
	public static void main(String[] args) {
		SpringApplication.run(SpringbootdemoApplication.class, args);
	}
	@Bean
	public CommandLineRunner commandLineRunner(){
		return args -> {
			Size size = new Size("S");
			Size size1 = new Size("M");
			Size size2 = new Size("L");
			Size size3 = new Size("XL");
			Size size4 = new Size("XXL");

			Size size5 = new Size("XXXS");
			Size size6 = new Size("XXS");
			Size size7 = new Size("XS");
			Size size8 = new Size("S");

			sizeRepository.save(size);
			sizeRepository.save(size1);
			sizeRepository.save(size2);
			sizeRepository.save(size3);
			sizeRepository.save(size4);
			sizeRepository.save(size5);
			sizeRepository.save(size6);
			sizeRepository.save(size7);
			sizeRepository.save(size8);


		};
	}



}
