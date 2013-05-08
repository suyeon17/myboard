//Coded by Shane Poloha
//Originally used to retrieve parameters for backing bean from url.  NOT CURRENTLY IN USE.

package com.myboard.bean;
import java.util.Map;

import javax.faces.context.FacesContext;

public class Parameter {
	private String passedParameter;
	
	public String getPassedParameter(){
		 
		FacesContext facesContext = FacesContext.getCurrentInstance();
		this.passedParameter = (String) facesContext.getExternalContext().getRequestParameterMap().get("id");
		 
		return this.passedParameter;
	}
		
	public void setPassedParameter(String passedParameter){
		this.passedParameter = passedParameter;
	}
}
