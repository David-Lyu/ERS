package com.example.daotest;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.dao.UsersDAOImpl;
import com.example.model.Accounts;

class UsersDaoTest {
	private static UsersDAOImpl udi;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		udi = new UsersDAOImpl("jdbc:h2:./testDBFolder;MODE=PostgreSQL", "sa", "sa");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		udi.init();
	}

	@AfterEach
	void tearDown() throws Exception {
		udi.destroy();
	}

	@Test
	void test() {
		Accounts employee = udi.selectUser("david", "asdf");
		Accounts admin = udi.selectUser("david1", "asdf");
		assertEquals("employee Account", true, employee.getRole().equals("employee"));
		assertEquals("admin Account", true, admin.getRole().equals("f_manager"));
	}

}
