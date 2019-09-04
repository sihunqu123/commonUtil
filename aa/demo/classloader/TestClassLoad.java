package demo.classloader;

/**
 * test when class being loaded.
 */
public class TestClassLoad {
	public static void main(String[] args) throws Exception {
		java.lang.reflect.Method m = ClassLoader.class.getDeclaredMethod("findLoadedClass", new Class[] { String.class });
		m.setAccessible(true);
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		Object test1 = m.invoke(cl, "test.classloader.TestClassLoad$ClassToTest");
		System.out.println(test1);
		ClassToTest.reportLoaded();
		Object test2 = m.invoke(cl, "test.classloader.TestClassLoad$ClassToTest");
		System.out.println(test2);
	}

	static class ClassToTest {
		static {
			System.out.println("Loading " + ClassToTest.class.getName());
		}

		static void reportLoaded() {
			System.out.println("Loaded");
		}
	}
}