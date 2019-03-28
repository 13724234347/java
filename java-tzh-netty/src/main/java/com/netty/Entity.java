package com.netty;

public class Entity {
	
	private String fileName;

	private int fileNameLenth;
	
	private String fileContent;
	
	private int fileContentLenth;
	
	
	public Entity(int fileNameLenth,String fileName,int fileContentLenth,String fileContent){
		this.fileName = fileName;
		this.fileNameLenth = fileNameLenth;
		this.fileContent = fileContent;
		this.fileContentLenth = fileContentLenth;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getFileNameLenth() {
		return fileNameLenth;
	}

	public void setFileNameLenth(int fileNameLenth) {
		this.fileNameLenth = fileNameLenth;
	}

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	public int getFileContentLenth() {
		return fileContentLenth;
	}

	public void setFileContentLenth(int fileContentLenth) {
		this.fileContentLenth = fileContentLenth;
	}
	
	@Override
	public String toString() {
		return "Entity [fileName=" + fileName + ", fileNameLenth=" + fileNameLenth + ", fileContent=" + fileContent
				+ ", fileContentLenth=" + fileContentLenth + "]";
	}
	
}
