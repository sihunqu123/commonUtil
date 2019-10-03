package util.commonUtil.interfaces;


public interface IFormatToString {
	
	// public static can be omitted in interface
	int defaultIndent = 2;
	
	// public abstract canbe omitted in interface
	String toString(int indent);
	
}
