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

@WebServlet(name = "ebookServlet", urlPatterns = "/ebookServlet")
public class EbookServlet extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(EbookServlet.class);

	public EbookServlet(){
		super();
	}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		//logger.info("Get :" + id);
		DbUtil db = DbUtil.getInstance();
		int book_num = db.getBookNum();
		int index = Integer.parseInt(id);
		JSONObject obj = new JSONObject();
		if(index == -1){
			obj.put("id","-1");
			obj.put("book_num",String.valueOf(book_num));
		}else{
			if(index < book_num){
				BookInfo book = db.getBook(index);
				obj.put("id",id);
				obj.put("name",book.name);
				obj.put("price",book.price);
				obj.put("description",book.desp);
				obj.put("quantity",book.quantity);
			}else{
				obj.put("id","-2");
			}
		}
		response.getWriter().append(obj.toString());
    }

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.valueOf(request.getParameter("id"));
		String name = request.getParameter("name");
		double price = Double.valueOf(request.getParameter("price"));
		int quantity = Integer.valueOf(request.getParameter("quantity"));
		String desp = request.getParameter("desp");

		DbUtil db = DbUtil.getInstance();
		BookInfo info = new BookInfo(id,name,price,quantity,desp);
		db.updateBook(info);
		
		Writer out = response.getWriter();
		out.write("post");
		out.flush();
	}

}
