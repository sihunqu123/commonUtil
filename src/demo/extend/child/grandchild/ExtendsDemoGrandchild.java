package demo.extend.child.grandchild;


import demo.extend.child.ExtendsDemoChild;

public class ExtendsDemoGrandchild extends ExtendsDemoChild {

	private String strDefault2 = "defautlInExtendsDemoGrandchild";

	private static String strDefaultStatic = "strDefaultCtaticInExtendsDemoGrandchild";

	public String strDefaultPlulic = "strDefaultPlulicFrmExtendsDemoGrandchild";

	public static void main(String[] args) throws Exception {

		// if NullPointerException matches the exception, then block Exception won't be excuted.
		ExtendsDemoGrandchild extendsDemoGrandchild = new ExtendsDemoGrandchild();
		ExtendsDemoChild extendsDemoConverted = (ExtendsDemoChild) extendsDemoGrandchild;
		try {
			// will always invoke the C's  method no matter it's in it's own type or casted in superclass.
			// and the variable(both static & none-static) used in C's method is also in C's(or in superclass if C's doesn't have such variable)
			System.out.println(extendsDemoGrandchild.staticM2());
			System.out.println(extendsDemoConverted.staticM2());

			// will invoke the declared class's method, and the variables in declared class will be used.

			// will invoke the C's  method.
			// and the variable C's class
			System.out.println(extendsDemoGrandchild.staticM2Static());
			// will invoke the B's  method after casted in superclass.
			// and the variable B's class.
			System.out.println(extendsDemoConverted.staticM2Static());

			//the variables in declared class will be used.
			System.out.println(extendsDemoGrandchild.strDefaultPlulic);
			System.out.println(extendsDemoConverted.strDefaultPlulic);
			// class is the actual class C.
			System.out.println(extendsDemoGrandchild.getClass());
			System.out.println(extendsDemoConverted.getClass());
			System.out.println();

			System.out.println(strPluic);
			// If memberVariables that both A & B have, the access modifier in B will Always override A's.
//			System.out.println(extendsDemoGrandchild.strPluicNew);
//			System.out.println(strPluic2);
//			System.out.println(extendsDemoGrandchild.strProtectedNew);
//			System.out.println(strProtected);
			System.out.println(strPrivate);
		} catch (NullPointerException e) {
			System.out.println("in NullPointerException catch");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("in Exception catch");
			e.printStackTrace();
		}
	}

	public String staticM2() throws NullPointerException {
		return strDefault2 + " in Grandchild method " + strDefaultStatic;
	}

	public static String staticM2Static() {
		return " in Grandchild method " + strDefaultStatic;
	}

}