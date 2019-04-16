package com.dgy.ebook;

import java.io.OutputStream;
import java.io.FileOutputStream;
import java.util.Base64;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ImgUtil{
    private Logger logger = LoggerFactory.getLogger(ImgUtil.class);
	private static ImgUtil instance_ = new ImgUtil();
	private ImgUtil(){}
	public static ImgUtil getInstance(){
		return instance_;
	}

	public boolean storeBase64ForId(String img,int id){
		try{
			String path = "./images/" + String.valueOf(id) + ".jpeg";
			logger.info("Img path: "+path);
			//String content = img.substring(img.indexOf("base64"+6));
			OutputStream out = new FileOutputStream(path);
			out.write(Base64.getDecoder().decode(img));
			out.flush();
			out.close();
		}catch(Exception e){
			return false;
		}
		return true;
	}
}
