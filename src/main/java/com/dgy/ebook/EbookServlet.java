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
		int index = Integer.parseInt(id);
		JSONObject obj = new JSONObject();
		if(index == -1){
			obj.put("id","-1");
			obj.put("book_num",String.valueOf(6));
		}else{
			if(index < 6){
				BookInfo book = DbUtil.getInstance().getBook(index);
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
		//response.setContentType("text/html;charset=utf-8");
		//response.setHeader("Access-Control-Allow-Origin", "*");
		//response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		Writer out = response.getWriter();

		BufferedReader br = request.getReader();
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
			System.out.println(line);
		}
		out.write("post");
		out.flush();
	}

}
