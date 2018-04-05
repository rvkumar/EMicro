package com.microlabs.utilities;

public class IdValuePair<E,F> {
	
	private E id;
	private F value;
	
	public IdValuePair(E id,F value) {
		this.id=id;
		this.value=value;
	}
	
	public E getId() {
		return id;
	}
	
	public void setId(E id) {
		this.id = id;
	}
	
	public F getValue() {
		return value;
	}
	
	public void setValue(F value) {
		this.value = value;
	}
	
 }
