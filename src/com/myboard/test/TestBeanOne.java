package com.myboard.test;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ManagedProperty;

/**
 * @author Oz Zapata
 *
 */
@ManagedBean(name="testBeanOne")
@RequestScoped
public class TestBeanOne implements Serializable {

	private static final long serialVersionUID = 8128289652074345213L;

	@ManagedProperty(value="#{TestBeanOne.message}")
	private String message;
	
	public TestBeanOne() {
		this.message = "Hello, this is test!";
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
