package util.commonUtil.model;

public class CheckResult {

	private Integer result;

	private String reason;
	
	public CheckResult(Integer result, String reason) {
		super();
		this.result = result;
		this.reason = reason;
	}
	
	public Integer getResult() {
		return result;
	}
	public String getReason() {
		return reason;
	}
	
}