package sea.incubator.sgdeb_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer

public class SgdebServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SgdebServerApplication.class, args);
	}

}
