package com.springmvc.test;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
@ContextConfiguration(locations={ "classpath:/config/applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager="transactionManager",defaultRollback=true)
@Transactional
public class SpringDaoTest extends TestCase {
   /* @Autowired
	private UserDaoImpl userDao;
    @Test
	public void testQuery(){
		User u =new User();
		u.setUserName("1");
		u.setPassword("2");
		userDao.countAll();
		System.err.println("success");
		
	}*/
}
