package com.matrix.SHOPPE;

import com.matrix.SHOPPE.Repository.CategoryRepository;
import com.matrix.SHOPPE.Repository.UserRepository;
import com.matrix.SHOPPE.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableScheduling;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
@EnableScheduling
@EnableFeignClients(basePackages = "com.matrix.SHOPPE.client")
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class ShoppeApplication implements CommandLineRunner {
    private final JwtService jwtService;

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(ShoppeApplication.class, args);
    }


    @Override
    public void run(String... args) {

//		User user=userRepository.findByUsername("nazim").get();
//		String token = jwtService.issueToken(user);
//		log.info(token);
    }
}
