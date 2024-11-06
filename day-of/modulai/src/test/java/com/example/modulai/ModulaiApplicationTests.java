package com.example.modulai;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.ApplicationModule;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

@SpringBootTest
class ModulaiApplicationTests {

	@Test
	void contextLoads() {
		var am = ApplicationModules.of(ModulaiApplication.class); 
		am.verify(); // ArchUnit

		System.out.println(am);
		
		new Documenter(am).writeDocumentation();
	}

}
