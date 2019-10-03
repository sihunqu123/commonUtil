package util.commonUtil.interfaces;

import java.util.List;


public interface IConfigManager {
	
	/**
	 * get value as Boolean
	 * @param key
	 * @return true if value is 'true' case insensitively; else false.
	 */
	public Boolean getBoolean(String key);
	
	public String[] getArray(String key);
	
	public List<String> getList(String key);

	public String getString(String key);

}
