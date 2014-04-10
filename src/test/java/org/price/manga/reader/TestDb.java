package org.price.manga.reader;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.price.manga.reader.dao.services.api.UserMgmtDao;
import org.price.manga.reader.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/db-context.xml" })
public class TestDb {

	@Autowired
	@Qualifier("userMgmtDao")
	private UserMgmtDao userMgmtDao;

	@Test
	@Transactional(propagation = Propagation.REQUIRED)
	public void testUserCreationGetDelete() throws Exception {

		User user = userMgmtDao.createUser("Price", "Price");
		user = userMgmtDao.getUser("Price");

		Assert.assertNotNull(user);
		System.out.println(user.getCreated());

		Assert.assertSame(1, userMgmtDao.getUsers().size());

		userMgmtDao.deleteUser(user.getId());
	}
}
