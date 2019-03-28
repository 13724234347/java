package com.tzh.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class StorageTxtFile {
	public static void main(String[] args) {
		try {
			storage("d:/");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *@date 2018年6月14日下午3:39:25
	 *@author 唐子豪
	 */
	public static void storage(String path)throws Exception{
		File file = new File(path);
		String[] files = file.list();
		if(null == files || "".equals(files)){
			return;
		}
		for (int i = 0; i < files.length; i++) {
			String fileName = files[i]; //文件名
			files[i] = file.getPath() + file.separator + files[i]; //获取文件路劲
			boolean isFile= new File(files[i]).isDirectory(); //判断文件是否是文件夹
			if(isFile){
				storage(files[i]);
			}else{
				boolean isTxtFile = files[i].endsWith(".txt"); //判断文件是否是txt文件
				if(isTxtFile){
					String fileUrl = files[i]; // 文件路径
					Long size = new File(fileUrl).length(); //文件大小
					String fileSize = size.toString();
					String fileContent =readTxtFile(fileUrl);
					createIndexes("D:/lucence",fileName,fileUrl,fileSize,fileContent);
				}
			}
		}
		
	}
	/**
	 * 
	 *@date 2018年6月14日下午3:39:30
	 *@author 唐子豪
	 */
	public static void createIndexes(String path,String fileName,String fileUrl,String fileSize,String fileContent) {
		try {
			Path docDirPath = Paths.get(path, new String[0]);
			Directory directory = FSDirectory.open(docDirPath);
			IKAnalyzer analyzer = new IKAnalyzer();
			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
			indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
			IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
			Document document = new Document();
			
			Field name = new TextField("fileName",fileName, Field.Store.YES);
			document.add(name);
			
			Field url = new TextField("fileUrl",fileUrl, Field.Store.YES);
			document.add(url);
				
			Field size = new StringField("fileSize",fileSize, Field.Store.YES);
			document.add(size);
			
			Field content = new TextField("fileContent",fileContent, Field.Store.YES);
			document.add(content);
			
			indexWriter.addDocument(document);
			
			indexWriter.commit();// 提交
			indexWriter.close();// 关闭
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	 /**
     * 功能：Java读取txt文件的内容
     * 步骤：1：先获得文件句柄
     * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
     * 3：读取到输入流后，需要读取生成字节流
     * 4：一行一行的输出。readline()。
     * 备注：需要考虑的是异常情况
     * @param filePath
     */
    public static String readTxtFile(String filePath){
    	String value = "";
        try {
                String encoding="GBK";
                File file=new File(filePath);
                if(file.isFile() && file.exists()) { //判断文件是否存在
                    InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file),encoding);//考虑到编码格式
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    while((lineTxt = bufferedReader.readLine()) != null){
                    	value+=lineTxt;
                    }
                    read.close();
		        } else { 
		            System.out.println("找不到指定的文件");
		        }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return value;
    }
}
