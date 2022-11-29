package be.panidel.model;

import java.util.Date;

public class ServiceResponse implements ServiceMessage {

	private String message;

	private Object entity;

	private ResponseResult responseResult;

	private Date date;

	@Override
	public String toString() {

		StringBuffer stringBuffer = new StringBuffer();

		stringBuffer.append("message[");
		stringBuffer.append("" + message);
		stringBuffer.append("], ");

		stringBuffer.append("responseResult[");
		stringBuffer.append("" + responseResult.name());
		stringBuffer.append("], ");

		stringBuffer.append("date[");
		stringBuffer.append("" + date);
		stringBuffer.append("], ");

		stringBuffer.append("entity[");
		stringBuffer.append("" + entity);
		stringBuffer.append("], ");

		return stringBuffer.toString();

	}

	public ServiceResponse() {
		super();
	}

	public ServiceResponse(String message, Object entity, ResponseResult responseResult, Date date) {
		super();
		this.message = message;
		this.entity = entity;
		this.responseResult = responseResult;
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getEntity() {
		return entity;
	}

	public void setEntity(Object entity) {
		this.entity = entity;
	}

	public ResponseResult getResponseResult() {
		return responseResult;
	}

	public void setResponseResult(ResponseResult responseResult) {
		this.responseResult = responseResult;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}