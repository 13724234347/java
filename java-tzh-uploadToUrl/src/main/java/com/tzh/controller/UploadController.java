package com.tzh.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tzh.entity.FileManager;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class UploadController {
	
	
		public String listRoots(Map<String, Object> map, List<FileManager> list) {
	        File[] paths;
	        paths = File.listRoots();
	        for (File path : paths) {
	            
	        	FileManager user = new FileManager();
	            user.setName(String.valueOf(path));
	            user.setMark(0);
	            list.add(user);
	        }
	        List<FileManager> list1 = new ArrayList<>();
	        FileManager user1 = new FileManager();
	        user1.setName("计算机");
	        user1.setMark(1);
	        list1.add(user1);
	        map.put("fileList1", list1);
	        map.put("fileList", list);
	        return "upload";
	    }

	    @RequestMapping("/user")
	    public String fileManager(Map<String, Object> map,@RequestParam(value = "filePath",required = false) String filePath){
	        File file = null;
	        List<FileManager> list = new ArrayList<FileManager>();
	        List<FileManager> list1 = new ArrayList<FileManager>();
	        if(filePath !=null && !filePath.equals(""))
	        {
	            try {
	                filePath= new String(filePath.getBytes("iso-8859-1"),"utf-8");
	                String filePaths = filePath.substring(filePath.indexOf("\\"));
	                FileManager fileManager1 = new FileManager();
	                fileManager1.setName(filePath);
	                fileManager1.setMark(0);
	                list1.add(fileManager1);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            file = new File(filePath);
	        }
	        else
	        {
	            return listRoots(map, list);
	        }
	        String[] files = file.list();
	        for (int i = 0; i < files.length; i++) {
	            files[i] = file.getPath() + file.separator + files[i];
	            
	            FileManager fileManager = new FileManager();
	            fileManager.setName(files[i]);
	            fileManager.setMark(new File(files[i]).isDirectory() ? 0 : 1);
	            list.add(fileManager);
	        }
	        map.put("fileList1", list1);
	        map.put("fileList", list);
	        //返回到界面,也就是我们的MODEL.
	        return "upload";
	    }
			/*
			 *	下载
			 * */
			@RequestMapping(value="/download")
			public ResponseEntity<byte[]> download(@RequestParam(value = "loadFilePath",required = false) String loadFilePath) throws IOException {
			loadFilePath= new String(loadFilePath.getBytes("iso-8859-1"),"utf-8");
			File file = new File(loadFilePath.trim());
			String fileName=file.getName();
			HttpHeaders headers = new HttpHeaders();
			String download = new String(fileName.getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
			headers.setContentDispositionFormData("attachment", download);
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
			}
											
											
}
