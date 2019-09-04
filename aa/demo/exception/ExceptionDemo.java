package demo.exception;

/**
 * ExceptionDemo
 */
public class ExceptionDemo {
	public static void main(String[] args) throws Exception {
		
		// if NullPointerException matches the exception, then block Exception won't be excuted.
		try {
			throwNullPoint();
		} catch (NullPointerException e) {
			System.out.println("in NullPointerException catch");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("in Exception catch");
			e.printStackTrace();
		}
	}

	public static void throwNullPoint() throws NullPointerException {
		Object obj = null;
		obj.equals(obj);
	}
}