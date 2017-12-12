package com.zhongyang.java.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.zhongyang.java.system.Message;

@Controller
public class UploadController extends BaseController {

	@RequestMapping(value = "/uploadFile")
	public @ResponseBody Message uploadFile(HttpServletRequest request,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		Message message=new Message();
		byte[] bytes;
		try {
			bytes = file.getBytes();
			String uploadDir = request.getRealPath("/") + "tempFile";
			String sep = System.getProperty("file.separator");
			String fileName=file.getOriginalFilename();
			String fileNames[]=fileName.split("\\.");
			String name= System.currentTimeMillis()+"."+fileNames[1];
			File uploadedFile = new File(uploadDir + sep + name);
			FileCopyUtils.copy(bytes, uploadedFile);
			message.setMessage("tempFile/"+name);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return message;
	}
}
