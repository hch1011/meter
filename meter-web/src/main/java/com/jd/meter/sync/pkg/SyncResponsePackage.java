package com.jd.meter.sync.pkg;
 
/**
 * 同步数据返回,
 * body内容：
 * {
 * 	code=0,  //成功，!失败
 *  message=message
 *  data = 成功的数据的id
 * }
 * @author hc
 *
 */
public class SyncResponsePackage extends PackageBasic {
	 public static int CODE_OK = 0;
	
	 private int code = -1;
	 private String  data;
	 private String  message;
	 
	 public SyncResponsePackage(){
		 packageType = PackageType.sync_response;
	 }
	 
	 public SyncResponsePackage(int code,String data,String message ){
		 this.packageType = PackageType.sync_response;
		 this.code = code;
		 this.data = data;
		 this.message = message;
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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
