package com.psl.tapestry.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.psl.tapestry.entities.User;
import com.psl.tapestry.exception.NoUserPresentException;
import com.psl.tapestry.service.UserService;
import com.psl.tapestry.util.DummyData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:SpringConfiguration.xml" })
@Transactional
public class SpringDataTest {

	@Autowired
	private UserService dao;

	@Test
	public void testCreateUser() {
		User user = new User(DummyData.firstName, DummyData.lastName,
				DummyData.username, DummyData.password, DummyData.email,
				DummyData.mobile, DummyData.enabled, DummyData.locked);
		Assert.assertEquals("Dummy", user.getFirstname());
	}

	@Test
	public void testFindUserById() {
		User user = (User) dao.findUserById(6);
		Assert.assertEquals(6, user.getId());
	}

	@Test
	public void testGetAllUsers() throws NoUserPresentException {
		User user = new User(DummyData.firstName, DummyData.lastName,
				DummyData.username, DummyData.password, DummyData.email,
				DummyData.mobile, DummyData.enabled, DummyData.locked);
		dao.createUser(user);
		List<User> users = dao.getAllUsers();
		System.out.println(users.size());
		Assert.assertEquals(2, users.size());
		User newUser = users.get(0);

		Assert.assertNotEquals("Jane Doe", newUser.getFirstname());
		Assert.assertNotEquals("gagan@psl.com", newUser.getEmail());
		Assert.assertNotEquals("2125552121", newUser.getMobile());
	}
}
