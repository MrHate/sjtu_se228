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

@WebServlet(name = "ebookServlet", urlPatterns = "/ebookServlet")
public class EbookServlet extends HttpServlet {
	public EbookServlet(){
		super();
	}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[][] books = {
                {"salaboy", "$12", "This is a book in black.Since the Sass port has a separate repo and serves a slightly different audience, the contents of the project differ greatly from the main Bootstrap project. This ensures the Sass port is as compatible with as many Sass-based systems as possible."},
                {"ryandawsonuk","$15", "If you are hacker.Using color to add meaning to a button only provides a visual indication, which will not be conveyed to users of assistive technologies – such as screen readers. Ensure that information denoted by the color is either obvious from the content itself (the visible text of the button), or is included through alternative means, such as additional text hidden with the .sr-only class."},
                {"erdemedeiros", "$6", "GROUP_activitiTeam"},
                {"other", "$4", "GROUP_otherTeam"},
                {"system", "$20","Things go better."},
                {"admin", "$9","Now you see it."},
        };
		String id = request.getParameter("id");
		System.out.print("Get: ");
		System.out.println(id);
		int index = Integer.parseInt(id);
		JSONObject obj = new JSONObject();
		if(index == -1){
			obj.put("id","-1");
			obj.put("book_num",String.valueOf(books.length));
		}else{
			if(index < books.length){
				obj.put("id",id);
				obj.put("name",books[index][0]);
				obj.put("price",books[index][1]);
				obj.put("description",books[index][2]);
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
