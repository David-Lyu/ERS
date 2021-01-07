package com.example.daotest;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.dao.ReimbDAOImpl;
import com.example.model.Reimbursement;

class ReimbDaoTest {
	private static ReimbDAOImpl rdi; 
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		rdi = new ReimbDAOImpl("jdbc:h2:./testDBFolder;MODE=PostgreSQL","sa","sa");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		rdi.init();
	}

	@AfterEach
	void tearDown() throws Exception {
		rdi.destroy();
	}

	@Test
	void test() {
		List<Reimbursement> rList = rdi.selectAllReimb();
		assertEquals("should be true",true, rList.size() != 0);
		
		Reimbursement r = rdi.selectReimb(1);
		assertEquals("get single reimbursement", true, r != null);
	}

}
