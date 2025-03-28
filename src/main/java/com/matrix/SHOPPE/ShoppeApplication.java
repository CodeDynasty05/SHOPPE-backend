package com.matrix.SHOPPE;

import com.matrix.SHOPPE.Repository.CategoryRepository;
import com.matrix.SHOPPE.Repository.UserRepository;
import com.matrix.SHOPPE.model.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
public class ShoppeApplication implements CommandLineRunner {

	private final CategoryRepository categoryRepository;
	private final UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(ShoppeApplication.class, args);
	}


	@Override
	public void run(String... args) {
	}
}
