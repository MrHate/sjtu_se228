package com.dgy.ebook;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dgy.ebook.entity.BookInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

@WebServlet(name = "bookManager", urlPatterns = "/bookManager")
public class BookManager extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(BookManager.class);

	public BookManager(){
		super();
	}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			DbUtil db = DbUtil.getInstance();
			String id = request.getParameter("id");
			String imgNeeded = request.getParameter("img");
			//logger.info("Get :" + id);
			int book_num = db.getBookNum();
			int index = Integer.parseInt(id);
			JSONObject obj = new JSONObject();
			if(index == -1){
				obj.put("id","-1");
				obj.put("book_num",String.valueOf(book_num));
			}else{
				if(index < book_num){
					BookInfo book = db.getBook(index);
					obj.put("id",book.getId());
					obj.put("name",book.getName());
					obj.put("price",book.getPrice());
					obj.put("description",book.getDesp());
					obj.put("quantity",book.getQuantity());
					obj.put("isbn",book.getIsbn());
					obj.put("author",book.getAuthor());
					if(imgNeeded != null && book.getId() != -1){
						obj.put("img",db.getImg(book.getId()));
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
		DbUtil db = DbUtil.getInstance();
		Writer out = response.getWriter();

		int id = Integer.valueOf(request.getParameter("id"));
		
		String name = request.getParameter("name");
		String isbn = request.getParameter("isbn");
		String author = request.getParameter("author");
		double price = Double.valueOf(request.getParameter("price"));
		int quantity = Integer.valueOf(request.getParameter("quantity"));
		String desp = request.getParameter("desp");
		boolean isUploadedImg = Boolean.parseBoolean(request.getParameter("isUploadedImg"));
		byte[] img = request.getParameter("img").getBytes();

		BookInfo info = new BookInfo();
		info.setId(id);
		info.setName(name);
		info.setIsbn(isbn);
		info.setAuthor(author);
		info.setPrice(price);
		info.setQuantity(quantity);
		info.setDesp(desp);

		db.updateBook(info);
		if(isUploadedImg){
			db.updateImg(id,img);
		}
		
		out.write("post");
		out.flush();
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DbUtil db = DbUtil.getInstance();
		Writer out = response.getWriter();
		
		String name = request.getParameter("name");
		String isbn = request.getParameter("isbn");
		String author = request.getParameter("author");
		double price = Double.valueOf(request.getParameter("price"));
		int quantity = Integer.valueOf(request.getParameter("quantity"));
		String desp = request.getParameter("desp");
		int	id = db.getUniqueID();
		boolean isUploadedImg = Boolean.parseBoolean(request.getParameter("isUploadedImg"));
		byte[] img = request.getParameter("img").getBytes();

		BookInfo info = new BookInfo();
		info.setId(id);
		info.setName(name);
		info.setIsbn(isbn);
		info.setAuthor(author);
		info.setPrice(price);
		info.setQuantity(quantity);
		info.setDesp(desp);

		db.insertBook(info);
		if(isUploadedImg){
			db.storeImg(id,img);
		}
		
		out.write("put");
		out.flush();
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DbUtil db = DbUtil.getInstance();
		Writer out = response.getWriter();
		int id = Integer.valueOf(request.getParameter("id"));

		db.removeBook(id);

		out.write("delete");
		out.flush();
	}
}
