package com.employeemanagement;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class EmployeeManagementApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void contextLoads() {
		// Verify that the application context loads successfully
		assertNotNull(applicationContext);
	}

	@Test
	void testEmployeeManagementApplicationBean() {
		// Verify that the EmployeeManagementApplication bean is present in the context
		EmployeeManagementApplication app = applicationContext.getBean(EmployeeManagementApplication.class);
		assertNotNull(app);
	}
}
