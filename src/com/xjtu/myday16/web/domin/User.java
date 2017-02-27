package com.xjtu.myday16.web.domin;



import java.util.ArrayList;
import java.util.List;

import org.apache.commons.fileupload.FileItem;

public class User {
 private String username;
 private List<FileItem> upfileList = new ArrayList<>();
 public User(){}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public List<FileItem> getUpfileList() {
	return upfileList;
}
public void setUpfileList(List<FileItem> upfileList) {
	this.upfileList = upfileList;
}

 
 
 
 
}
