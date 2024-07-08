package util.commonUtil.model;

import java.io.File;

import util.commonUtil.ComFileUtil;
import util.commonUtil.ComLogUtil;
import util.commonUtil.ComRegexUtil;


public class RegRule {

	public String reg;
	
	private Boolean isCaseSensitive;
	
	public RegRule(String reg, Boolean isCaseSensitive) {
		super();
		this.reg = reg;
		this.isCaseSensitive = isCaseSensitive;
	}

	public String getReg() {
		return reg;
	}

	public Boolean getIsCaseSensitive() {
		return isCaseSensitive;
	}

	
}