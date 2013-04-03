package com.myboard.bean;

import java.io.Serializable;

import com.myboard.business.User;
import com.myboard.business.UserSession;

public class LoginBean implements Serializable {

	private static final long serialVersionUID = -9123661301334518697L;

	private UserSession userSession;
	private String username;
	private String password;
	
	public LoginBean(){
		this.username = "";
		this.password = "";

	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username.trim();
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password.trim();
	}
	

	public UserSession getUserSession() {
		return userSession;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}

	public String login(){
		User user = new User(this.username);
		user.readUser();
		
		if(!user.getFirstName().isEmpty() && !user.getLastName().isEmpty() && !user.getPassword().isEmpty()){
			if(this.password.equals(user.getPassword())){
				userSession.setUser(user);
			}
		}else{
			System.out.println("Failed login!");
		}
		
		return "OK";
	}
	
	public String logout(){
		userSession.setUser(null);
		
		return "OK";
	}
	
	
}
