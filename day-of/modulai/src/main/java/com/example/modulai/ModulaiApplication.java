package com.example.modulai;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.modulith.events.IncompleteEventPublications;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class ModulaiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModulaiApplication.class, args);
	}

}

//@Component
class YouIncompleteMe
	implements ApplicationRunner
{
	
	private final IncompleteEventPublications publications;
	
	YouIncompleteMe(IncompleteEventPublications publications) {
		this.publications = publications;
	}


	@Override
	public void run(ApplicationArguments args) throws Exception {
//		LockRegistry registry; 
//		var lock = registry.tryAccquire("myLock" , TimeOUt );
//		// ..
//		this.publications.resubmitIncompletePublications( 
//				event -> event.
//		);
	}
}
