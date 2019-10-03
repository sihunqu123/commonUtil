package demo.access.modifier;

import util.commonUtil.ComLogUtil;

public class AcceessModifierDemoBrother {

	public static void main(String[] args) throws Exception {
		// none child class in same package can only access: public, protected, default
		ComLogUtil.info(AcceessModifierDemo.strPluic);
		ComLogUtil.info(AcceessModifierDemo.strProtected);
		ComLogUtil.info(AcceessModifierDemo.strDefault);
		// ComLogUtil.info(AcceessModifierDemo.strPrivate);

		// if NullPointerException matches the exception, then block Exception won't be excuted.
		try {
			// throwNullPoint();
		} catch (NullPointerException e) {
			System.out.println("in NullPointerException catch");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("in Exception catch");
			e.printStackTrace();
		}
	}

}