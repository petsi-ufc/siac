package br.ufc.petsi.util;

public class Response {
	public static final int ERROR = 500;
	public static final int SUCCESS = 200;
	
	private int code;
	private String message;
	
	public Response(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public Response() {
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
