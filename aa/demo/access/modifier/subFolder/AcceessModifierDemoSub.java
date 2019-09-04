package demo.access.modifier.subFolder;

import demo.access.modifier.AcceessModifierDemo;
import util.commonUtil.ComLogUtil;

public class AcceessModifierDemoSub {

	public static void main(String[] args) throws Exception {
		// none child class in sub package can only access: public
		ComLogUtil.info(AcceessModifierDemo.strPluic);
		// ComLogUtil.info(AcceessModifierDemo.strProtected);
		// ComLogUtil.info(AcceessModifierDemo.strDefault);
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