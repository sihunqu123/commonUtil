package demo.extend;

/**
 * An demo about how memberVariables, memberMethods, staticVariables, staticMethods works.
 * 
 * @author Administrator
 *
 */
public class ExtendsDemo {

	public static String strPluic = "public";
	public static String strPluic2 = "public2";

	public String strPluicNew = "publicNew";

	protected static String strProtected = "protected";
	protected String strProtectedNew = "protected";
	static String strDefault = "default";
	private static String strPrivate = "private";

	public String memberTest = "memberTest";

	public String memberTest2 = "memberTest2";

	public static String memberTest3 = "memberTest3";

	public static String memberTest4 = "memberTest4";

	public static void main(String[] args) throws Exception {

		// if NullPointerException matches the exception, then block Exception won't be excuted.
		try {
			getStrDefault();
		} catch (NullPointerException e) {
			System.out.println("in NullPointerException catch");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("in Exception catch");
			e.printStackTrace();
		}
	}

	public static String staticException() throws NoSuchFieldException {
		return strDefault;
	}

	public String memberException() throws NullPointerException {
		return strDefault;
	}

	public static String staticM() throws NullPointerException {
		return strDefault;
	}

	private static String staticM2() throws NullPointerException {
		return strDefault;
	}

	public String noneStaticM() throws NullPointerException {
		return strDefault;
	}

	private String noneStaticM2() throws NullPointerException {
		return strDefault;
	}

	public static String getStrDefault() throws NullPointerException {
		return strDefault;
	}

	private static String getStrDefaultPrivate() {
		return strDefault;
	}

	public static String getStrDefaultPlubic() {
		return strDefault;
	}

	protected static String getStrDefaultProtectedStatic() {
		return strDefault;
	}

	protected String getStrDefaultProtected() {
		return strDefault;
	}

	protected String getStrDefaultProtected2() {
		return strDefault;
	}

	protected String getStrDefaultProtected3() {
		return strDefault;
	}

	private String getStrDefaultProtected4() {
		return strDefault;
	}
}