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


}
