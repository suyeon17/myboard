package com.myboard.exception;

public class EntityAlreadyExistsException extends Exception {

	private static final long serialVersionUID = -689840107442784884L;
	
	public EntityAlreadyExistsException(){}
	
	public EntityAlreadyExistsException(String message){
		super(message);
	}

}
