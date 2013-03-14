package com.myboard.exception;

public class EntityNotFoundException extends Exception {

	private static final long serialVersionUID = 3049283065705795200L;

	public EntityNotFoundException(){}
	
	public EntityNotFoundException(String message){
		super(message);
	}
}
