package ozdemir0ozdemir.mayaecommercebackend;

import org.springframework.boot.SpringApplication;

public class TestMayaECommerceBackendApplication {

    public static void main(String[] args) {
        SpringApplication.from(MayaECommerceBackendApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
