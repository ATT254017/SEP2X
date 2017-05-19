package Net;

import java.util.Map;

public class NetMessage {
	private String msg;
	private Map<String, Object> params;
	
	public NetMessage(String msg, Map<String, Object> params) {
		this.msg = msg;
		this.params = params;
	}
	
	public String getMessage() {
		return msg;
	}
	
	public Map<String, Object> getParams() {
		return params;
	}
}
