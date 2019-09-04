package util.commonUtil.interfaces;

import java.util.Set;

public interface IJSONObject extends ILength{
	
	public Object get(String key);
	
	public IJSONObject put(String key, Object value);
	
	public int length();
	
	public Set<String> keySet();
	
}
