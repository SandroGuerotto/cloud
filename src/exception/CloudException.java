package exception;

public class CloudException extends Exception {

	private String msg;
	private char type = ExceptionType.ERROR;

	public String getMsg() {
		return msg;
	}

	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
