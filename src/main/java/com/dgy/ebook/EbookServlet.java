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
import net.sf.json.JSONObject;  

@WebServlet(name = "ebookServlet", urlPatterns = "/ebookServlet")
public class EbookServlet extends HttpServlet {
	public EbookServlet(){
		super();
	}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		System.out.println(id);
		JSONObject obj = new JSONObject();
		obj.put("id",id);
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
