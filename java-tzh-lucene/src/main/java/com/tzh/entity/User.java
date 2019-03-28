package com.tzh.entity;

public class User {
	private String fileName;
	
	private String fileSize;
	
	private String fileUrl;
	
	private String fileContent;

	public String getFileUrl() {
		return fileUrl;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}
	
	@Override
	public String toString() {
		return "User [fileName=" + fileName + ", fileSize=" + fileSize + ", fileUrl=" + fileUrl + ", fileContent="
				+ fileContent + "]";
	}
}
