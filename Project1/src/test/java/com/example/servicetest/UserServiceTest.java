package com.example.servicetest;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.example.dao.UsersDAOImpl;
import com.example.model.Accounts;
import com.example.servicelayer.UserService;
import com.example.servicelayer.UserServiceImpl;

class UserServiceTest {

	UserService usi;
	private static UsersDAOImpl udi = Mockito.mock(UsersDAOImpl.class);
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		usi = new UserServiceImpl();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		Accounts a = new Accounts();
		Mockito.when(udi.selectUser("David", "asdf")).thenReturn(a);
		assertEquals("selecting user",true, usi.getUser("David", "asdf") instanceof Accounts);
	}

}
