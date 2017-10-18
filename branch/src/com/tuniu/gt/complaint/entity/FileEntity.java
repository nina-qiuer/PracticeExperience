package com.tuniu.gt.complaint.entity;

public class FileEntity {
	
	//文件名称
	private String fileName;
	
	//文件内容
	private byte[] fileContents;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getFileContents() {
		return fileContents;
	}

	public void setFileContents(byte[] fileContents) {
		this.fileContents = fileContents;
	}
	
}
