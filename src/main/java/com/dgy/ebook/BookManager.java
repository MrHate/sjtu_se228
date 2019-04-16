package com.dgy.ebook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import net.sf.json.JSONObject;  
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dgy.ebook.DbUtil.BookInfo;

@WebServlet(name = "bookManager", urlPatterns = "/bookManager")
public class BookManager extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(BookManager.class);

	public BookManager(){
		super();
	}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
				obj.put("id",book.id);
				obj.put("name",book.name);
				obj.put("price",book.price);
				obj.put("description",book.desp);
				obj.put("quantity",book.quantity);
				if(imgNeeded != null && book.id != -1){
					obj.put("img",db.getImg(book.id));
				}
			}else{
				obj.put("id","-2");
			}
		}
		response.getWriter().append(obj.toString());
    }

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DbUtil db = DbUtil.getInstance();
		Writer out = response.getWriter();

		int id = Integer.valueOf(request.getParameter("id"));
		
		String name = request.getParameter("name");
		double price = Double.valueOf(request.getParameter("price"));
		int quantity = Integer.valueOf(request.getParameter("quantity"));
		String desp = request.getParameter("desp");
		boolean isUploadedImg = Boolean.parseBoolean(request.getParameter("isUploadedImg"));
		byte[] img = request.getParameter("img").getBytes();
		BookInfo info = new BookInfo(id,name,price,quantity,desp);

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
		double price = Double.valueOf(request.getParameter("price"));
		int quantity = Integer.valueOf(request.getParameter("quantity"));
		String desp = request.getParameter("desp");
		int	id = db.getUniqueID();
		boolean isUploadedImg = Boolean.parseBoolean(request.getParameter("isUploadedImg"));
		byte[] img = request.getParameter("img").getBytes();
		BookInfo info = new BookInfo(id,name,price,quantity,desp);

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
