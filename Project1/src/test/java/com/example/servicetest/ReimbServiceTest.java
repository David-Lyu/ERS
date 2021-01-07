package com.example.servicetest;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.example.dao.ReimbDAOImpl;
import com.example.model.Reimbursement;
import com.example.servicelayer.ReimbService;
import com.example.servicelayer.ReimbServiceImpl;

class ReimbServiceTest {
	
	ReimbService rsi;
	ReimbDAOImpl reimbDaoImpl = Mockito.mock(ReimbDAOImpl.class);
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		rsi = new ReimbServiceImpl();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		List<Reimbursement> reimbList = new ArrayList<Reimbursement>();
		Mockito.when(reimbDaoImpl.selectAllReimb()).thenReturn(reimbList);
		assertEquals("getting all reimbursemets", reimbList ,rsi.viewAllReimb());
	}

}
