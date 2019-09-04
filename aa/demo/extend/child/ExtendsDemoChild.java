package demo.extend.child;

import demo.extend.ExtendsDemo;

// say:
// A: class A. AB: Class B extends A. ABC: Class C extends B extends A.

// for member variables:
// 1, child-class can do whatever it want. private -> public, public -> private with same name with superclass.
// 2, If memberVariables that both A & B have, the access modifier in B will Always override A's. e.g:
//	 C.name cannot be resolved if A.name is public and B.name is private.
// 3, static/none-static memberVariables with same name in one class is not allowed.
// 4, child-class can have none-static/static variables shared the same name with superclass's static variable
// 5, child-class can have none-static/static variables shared the same name with superclass's none-static variable

// for static method:
// 1, u can never add @Override.
// 2, u can create same static method in B, even it's private in A, but cannot reduce the visibility.

// for member method:
// 1, static/none-static method with same name in one class is not allowed.
// 2, if static method of A is available for B, then B cannot create a none-static method with same name/modifier
// 3, if static method of A is not available for B, then B cannot still create a none-static method with same name/modifier

public class ExtendsDemoChild extends ExtendsDemo {

	/** public */
	protected static String strPluic = "publicFromExtendsDemoChild";

	// can reduce the visibility of a same memberVariable that superclass has.
	protected String memberTest = "memberTest";

	// can reduce(and convert to static) the visibility of a same memberVariable that superclass has.
	protected static String memberTest2 = "memberTest2";

	// can reduce(and convert to member) the visibility of a same Variable that superclass has.
	protected String memberTest3 = "memberTest";

	// can reduce the visibility of a same Variable that superclass has.
	protected static String memberTest4 = "memberTest2";

	// static/none-static memberVariables with same name in one class is not allowed.
//	protected static strPluic = "publicFromExtendsDemoChild";

	private static String strPluic2 = "public2FromExtendsDemoChild";

	private String strPluicNew = "publicNewFromExtendsDemoChild";

	/** protected */
	private static String strProtected = "protectedFromExtendsDemoChild";

	private String strProtectedNew = "protectedFromExtendsDemoChildNew";

	static String strDefault = "defaultFrmExtendsDemoChild";

	public String strDefault2 = "defaultFrmExtendsDemoChild";

	public static String strDefaultStatic = "strDefaultCtaticInExtendsDemochild";

	public String strDefaultPlulic = "strDefaultPlulicFrmExtendsDemoChild";

	/** private */
	public static String strPrivate = "privateFrmExtendsDemoChild";

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

	// ------------------------------ static method ------------------------------
	// override a static public method to public method is OK
	// but @Override is not allowed
	// error: The method getStrDefault() of type ExtendsDemoChild must override or implement a supertype method
//	@Override
	public static String getStrDefault() throws NullPointerException {
		return strDefault;
	}

	// override a static public method to private method is not allowed
	// error: Cannot reduce the visibility of the inherited method from ExtendsDemo
//	private static String getStrDefaultPlubic() {
//		return strDefault;
//	}

	// override a static private method to private method is OK
	// but @Override is not allowed
	private static String getStrDefaultPrivate() {
		return strDefault;
	}

	// override a static protected method to public method is OK
	// but @Override is not allowed
//	@Override
	public static String getStrDefaultProtectedStatic() {
		return strDefault;
	}

	//  child-class can throw bigger Exception than
//	public static String staticException()
//			// throws super Exception is OK.
//			//throws RuntimeException
//			// throws none relationship Exception is OK.
//			//throws NoSuchFieldException
//			throws Exception
//
//	{
//		return strDefault;
//	}

	// ------------------------------ static method(over) ------------------------------
	// ------------------------------ none static method ------------------------------

	// override a protected method to private
	// error: Cannot reduce the visibility of the inherited method from ExtendsDemo
//	@Override
//	private String getStrDefaultProtected3() {
//		return strDefault;
//	}

	// override a protected method to protected
	@Override
	protected String getStrDefaultProtected() {
		return strDefault;
	}

	// override a protected method to public
	@Override
	public String getStrDefaultProtected2() {
		return strDefault;
	}

	// Override a private none-static method of superclass is OK,
	// but @Override is not allowed
	// error:The method getStrDefaultProtected4() of type ExtendsDemoChild must override or implement a supertype
//	@Override
	private String getStrDefaultProtected4() {
		return strDefault;
	}

	@Override
	public String memberException() throws RuntimeException {
		return strDefault;
	}

	// ------------------------------ none static method(over) ------------------------------
	public String m() {
		return strDefault;
	}

	// static/none-static method with same name in one class is not allowed.
//	public static String m() {
//		return strDefault;
//	}

	// if static method of A is available for B, then B cannot create a none-static method with same name/modifier
//	public String staticM() throws NullPointerException {
//		return strDefault;
//	}

	// if static method of A is not available for B, then B can still create a none-static method with same name/modifier
	public String staticM2() throws NullPointerException {
		return strDefault;
	}

	// if none-static method of A is available for B, then B cannot create a static method with same name/modifier
	// error: This static method cannot hide the instance method from ExtendsDemo
//	public static String noneStaticM() throws NullPointerException {
//		return strDefault;
//	}

	// if none-static method of A is not available for B, then B can still create a static method with same name/modifier
	public static String noneStaticM2() throws NullPointerException {
		return strDefault;
	}

	public static String staticM2Static() {
		return " in child method " + strDefaultStatic;
	}


}