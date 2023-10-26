package jpabook.jpashop;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JpashopApplication {
	public static void main(String[] args) {
		SpringApplication.run(JpashopApplication.class, args);
	}

	/**
	 *
	 *기본적으로 초기화 된 프록시 객체만 노출, 초기화되지 않은 프록시 객체는 노출 안함
	 * @return the hibernate 5 jakarta module
	 */
	@Bean
	Hibernate5Module hibernate5Module() {
		return new Hibernate5Module();
	}

}
