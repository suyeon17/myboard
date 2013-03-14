package com.myboard.bean;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ManagedProperty;

@ManagedBean(name="testPageBean")
@RequestScoped
public class TestPageBean implements Serializable {

	private static final long serialVersionUID = 8128289652074345213L;

	@ManagedProperty(value="#{TestPageBean.message}")
	private String message;
	
	public TestPageBean() {
		this.message = "Hello, this is test!!!!";
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}