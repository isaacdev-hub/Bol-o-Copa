package hub.isaacode.bolao;

import org.springframework.boot.SpringApplication;

public class TestBolaoCopaApplication {

	public static void main(String[] args) {
		SpringApplication.from(BolaoCopaApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
