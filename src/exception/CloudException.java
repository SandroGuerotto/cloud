package exception;

public class CloudException extends Exception {
	
	private String msg;
	
	public String getMsg(){
		return msg;
	}
	
	public void setMsg(String msg){
		this.msg = msg;
	}

}
