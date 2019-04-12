package com.dgy.ebook;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EbookApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void dbTests(){
		DbUtil.getInstance().getBooks();
		DbUtil.getInstance().getBookNum();
	}

}
