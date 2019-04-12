package com.dgy.ebook;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.dgy.ebook.DbUtil.BookInfo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EbookApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void dbNullPointerTest(){
		Assert.assertNotNull(DbUtil.getInstance());
	}

	@Test
	public void dbBookInfoTests(){
		BookInfo info = new BookInfo(-1,"_used_for_dbtest",99,20,"new entry created;");
		DbUtil db = DbUtil.getInstance();
		db.insertBook(info);
		info.desp = "entry updated;";
		db.updateBook(info);
		db.getBooks();
		db.getBookNum();
		db.removeBook(info.id);
	}

	@Test
	public void dbUserInfoTests(){
		DbUtil db = DbUtil.getInstance();
		Assert.assertTrue(db.createUser("junit_test","test"));
		Assert.assertTrue(db.validateUser("junit_test","test"));
		Assert.assertTrue(db.removeUser("junit_test"));
	}

}
