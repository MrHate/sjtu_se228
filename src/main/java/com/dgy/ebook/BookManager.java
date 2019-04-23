package com.dgy.ebook;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dgy.ebook.entity.BookImage;
import com.dgy.ebook.entity.BookInfo;
import com.dgy.ebook.service.BookService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import net.sf.json.JSONObject;

@WebServlet(name = "bookManager", urlPatterns = "/bookManager")
public class BookManager extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(BookManager.class);

	@Autowired
	private BookService bookService;

	public BookManager(){
		super();
	}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			String id = request.getParameter("id");
			String imgNeeded = request.getParameter("img");
			//logger.info("Get :" + id);
			int book_num = bookService.getBookNum();
			int index = Integer.parseInt(id);
			JSONObject obj = new JSONObject();
			if(index == -1){
				obj.put("id","-1");
				obj.put("book_num",String.valueOf(book_num));
			}else{
				if(index < book_num){
					BookInfo book = bookService.getBook(index);
					obj.put("id",book.getId());
					obj.put("name",book.getName());
					obj.put("price",book.getPrice());
					obj.put("description",book.getDesp());
					obj.put("quantity",book.getQuantity());
					obj.put("isbn",book.getIsbn());
					obj.put("author",book.getAuthor());
					if(imgNeeded != null && book.getId() != -1){
						obj.put("img",bookService.getImg(book.getId()));
					}
				}else{
					obj.put("id","-2");
				}
			}
			response.getWriter().append(obj.toString());
		}catch(Exception e){
			response.getWriter().append("failed to get");
		}
    }

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Writer out = response.getWriter();

		int id = Integer.valueOf(request.getParameter("id"));
		
		String name = request.getParameter("name");
		String isbn = request.getParameter("isbn");
		String author = request.getParameter("author");
		double price = Double.valueOf(request.getParameter("price"));
		int quantity = Integer.valueOf(request.getParameter("quantity"));
		String desp = request.getParameter("desp");
		boolean isUploadedImg = Boolean.parseBoolean(request.getParameter("isUploadedImg"));

		BookInfo info = new BookInfo();
		info.setId(id);
		info.setName(name);
		info.setIsbn(isbn);
		info.setAuthor(author);
		info.setPrice(price);
		info.setQuantity(quantity);
		info.setDesp(desp);

		bookService.updateBook(info);
		if(isUploadedImg){
			BookImage img = new BookImage();
			img.setId(id);
			img.setImg(request.getParameter("img").getBytes());
			bookService.updateImage(img);
		}

		
		out.write("post");
		out.flush();
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Writer out = response.getWriter();
		
		String name = request.getParameter("name");
		String isbn = request.getParameter("isbn");
		String author = request.getParameter("author");
		double price = Double.valueOf(request.getParameter("price"));
		int quantity = Integer.valueOf(request.getParameter("quantity"));
		String desp = request.getParameter("desp");
		boolean isUploadedImg = Boolean.parseBoolean(request.getParameter("isUploadedImg"));

		BookInfo info = new BookInfo();
		info.setName(name);
		info.setIsbn(isbn);
		info.setAuthor(author);
		info.setPrice(price);
		info.setQuantity(quantity);
		info.setDesp(desp);

		bookService.updateBook(info);
		if(isUploadedImg){
			BookImage img = new BookImage();
			img.setId(0);
			img.setImg(request.getParameter("img").getBytes());
			bookService.updateImage(img);
		}
		
		out.write("put");
		out.flush();
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Writer out = response.getWriter();
		int id = Integer.valueOf(request.getParameter("id"));

		bookService.removeBook(id);

		out.write("delete");
		out.flush();
	}
}
