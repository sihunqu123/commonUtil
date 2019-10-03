package util.commonUtil;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import util.commonUtil.json.JSONObject;


/**
 * access private field/method
 */
public class ComReflectUtil extends CommonUtil{
	private static final String GREPMARK = "ComReflectUtil";

	private static Object cc;

	public static final String SEPARATOR = File.separator;

	/**
	 * get chain field value. e.g: orderDetail.userInfo.username will get the field value 'username' in userInfo, which is in orderDetail.
	 *
	 * @param obj
	 * @param fieldName
	 * @return field value
	 * @throws Exception
	 */
	public static Object accessFieldVal(Object obj, String fieldName) throws Exception {
		String[] split = fieldName.split("\\.");
		for(int i = 0; i < split.length; i++) {
			obj = accessFieldValNoChain(obj, ComTypeUtil.toClass(obj), split[i]);
		}
		return obj;
	}

	/**
	 * get field value.
	 * @param obj
	 * @param fieldName
	 * @return field value
	 * @throws Exception
	 */
	private static Object accessFieldValNoChain(Object obj, Class<?> class_, String fieldName) throws Exception {
		Field f;
//		Object res = null;
		try {
			// all fields(private ~ public) in this class(not including it's superClass/superInterface)
			f = class_.getDeclaredField(fieldName);
			f.setAccessible(true);
		} catch (Exception e) {
			try {
				// all public fields in this class and it's superClass/superInterface
				f = class_.getField(fieldName);
				// since it's already public, no need to setAccessible(true)
			} catch (Exception e1) {
//				for(Class interfaceClass : class_.getInterfaces()) {
//					if ((res = accessFieldValNoChain(obj, interfaceClass, fieldName)) != null) return res;
//				}
//
//				if (!class_.isInterface()) {
//		            Class<?> superclass = class_.getSuperclass();
//		            if (superclass != null && (res = accessFieldValNoChain(obj, superclass, fieldName)) != null) return res;
//		        }
//				return null;

				// if no accessible field found, then find field of it's interfaces
				// iterate it's directly interfaces(only the father interfaces, not including the grandfather interfaces.)
				for(Class<?> interfaceClass : class_.getInterfaces()) {
					// if exception occurred, which means NoSuchFiled/securityIssue in this interface
					// then continue to next interface
					// otherwise, return the found field value.
					try {return accessFieldValNoChain(obj, interfaceClass, fieldName);} catch (Exception e2) {}
				}

				// if no accessible field found, then find field of it's interfaces
				if (!class_.isInterface()) {
					Class<?> superclass = class_.getSuperclass();
					// getSuperclass will return null if given class doesn't have superclass anymore.
					if(superclass != null) {
						// if exception occurred, which means NoSuchFiled/securityIssue in this superclass
						// then just throw this exception
						// since there is no available field anymore.
						return accessFieldValNoChain(obj, superclass, fieldName);
					}
				}
//				ComLogUtil.info("Field not found. Existing fields:" + ComLogUtil.objToString(getAllFieldsVisibleInItself(class_)));
				throw e1;
			}
		}
		return f.get(obj);
	}


	/**
	 * get the method.
	 * @param obj
	 * @param methodName
	 * @param params
	 * @return method.
	 * @throws Exception
	 */
	public static Method accessMethod(Object obj, String methodName) throws Exception {
		return accessMethod(obj, methodName, new Class[] {});
	}

	/**
	 * get the method.
	 * @param obj
	 * @param methodName
	 * @param params
	 * @return method.
	 * @throws Exception
	 */
	public static Method accessMethod(Object obj, String methodName, Class<?>... params) {
		Method method;
		Class<?> class_ = ComTypeUtil.toClass(obj);
		try {
			// all methods(private ~ public) in this class(not including it's superClass/superInterface)
			method = class_.getDeclaredMethod(methodName, params);
			method.setAccessible(true);
		} catch (Exception e) {
			try {
				// all public methods in this class and it's superClass/superInterface
				method = class_.getMethod(methodName, params);
				// since it's already public, no need to setAccessible(true)
			} catch (Exception e1) {
				//ComLogUtil.info("Method:" + methodName + " not found. Existing methods:" + ComLogUtil.objToString(getAllMethodsVisibleInItself(class_)));
				//throw e1;
				return null;
			}
		}
		return method;
	}

	public static List<Method> getAllMethodsVisibleInItself(Class<?> class_) {
		// should new ArrayList, otherwise, java.lang.UnsupportedOperationException
		List<Method> list = new ArrayList<Method>(Arrays.asList(class_.getMethods()));
		list.addAll(Arrays.asList(class_.getDeclaredMethods()));
		return list;
	}

	public static List<Field> getAllFieldsVisibleInItself(Class<?> class_) {
		// should new ArrayList, otherwise, java.lang.UnsupportedOperationException
		List<Field> list = new ArrayList<Field>(Arrays.asList(class_.getFields()));
		list.addAll(Arrays.asList(class_.getDeclaredFields()));
		return list;
	}

	/**
	 * get all fields of a given class, including it's interfaces(and super...interfaces) and superclass(and super... class)
	 *
	 * @param class_
	 * @return
	 */
	public static List<Field> getAllFields(Class<?> class_) {
		// only add all fields(private ~ public) in this class(not including it's superClass/superInterface)
		// and will add field in it's interface/superclass later.
		List<Field> res = new ArrayList<Field>(Arrays.asList(class_.getDeclaredFields()));
		// iterate it's directly interfaces(only the father interfaces, not including the grandfather interfaces.)
		for(Class<?> interfaceClass : class_.getInterfaces()) {
			// the public final static member variables of interfaces will also be include.
			res.addAll(getAllFields(interfaceClass));
		}

		if (!class_.isInterface()) {
            Class<?> superclass = class_.getSuperclass();
            // getSuperclass will return null if given class doesn't have superclass anymore.
            if (superclass != null) res.addAll(getAllFields(superclass));
        }
		return res;
	}

	public static void main(String[] args) {
		try {
			ComReflectUtil comReflectUtil = new ComReflectUtil();
			// superclass's private static parameter method can be invoked
			Method parentMethodAdd1 = accessMethod(ComReflectUtil.class.getSuperclass(), "add1", new Class[] {int.class});
			System.out.println("parentMethodAdd1 result:" + parentMethodAdd1.invoke(ComReflectUtil.class, new Object[]{2}));
			// superclass's private none-static parameter method can be invoked
			Method parentMethodAdd2NoneStatic = accessMethod(ComReflectUtil.class.getSuperclass(), "add2NoneStatic", new Class[] {int.class});
			System.out.println("parentMethodAdd2NoneStatic result:" + parentMethodAdd2NoneStatic.invoke(comReflectUtil, new Object[]{3}));

			System.out.println(CommonUtil.class.getSuperclass().getSuperclass());
			System.out.println(ComLogUtil.objToFormatString(getAllFields(JSONObject.class)));
//			System.out.println(ComLogUtil.objToFormatString(getAllFields(ComRegexUtil.class)));
			System.out.println(ComLogUtil.objToFormatString(JSONObject.class.getInterfaces()));
			System.out.println(getAllFields(ComReflectUtil.class));
			System.out.println(new ComReflectUtil().cc3);

			System.out.println(ComLogUtil.objToString(ComReflectUtil.class.getFields()));
			System.out.println(ComLogUtil.objToString(ComReflectUtil.class.getDeclaredFields()));
			System.out.println(ComLogUtil.objToString(ComReflectUtil.class.getSuperclass().getDeclaredFields()));

			System.out.println(CommonUtil.log);
			System.out.println(ComReflectUtil.accessFieldVal(new ComReflectUtil(), "GREPMARK"));
			Field commonUtilGrepMark = ComReflectUtil.class.getSuperclass().getDeclaredField("GREPMARK");
			commonUtilGrepMark.setAccessible(true);
			System.out.println("oo:" + commonUtilGrepMark.get(ComReflectUtil.class));

			Field commonUtilCC4 = ComReflectUtil.class.getSuperclass().getDeclaredField("cc4");
			commonUtilCC4.setAccessible(true);
			System.out.println("cc4:" + commonUtilCC4.get(ComReflectUtil.class));

			Field commonUtilCC5 = ComReflectUtil.class.getSuperclass().getDeclaredField("cc5");
			commonUtilCC5.setAccessible(true);
			System.out.println("cc5:" + commonUtilCC5.get(comReflectUtil));

			Field commonUtilCC6 = ComReflectUtil.class.getSuperclass().getDeclaredField("cc6");
			commonUtilCC6.setAccessible(true);
			System.out.println("cc6:" + commonUtilCC6.get(comReflectUtil));
			//System.out.println("cc6 static:" + commonUtilCC6.get(ComReflectUtil.class));// error

			Method objectToString = accessMethod(Object.class, "toString", new Class[] {});
			System.out.println("objectToString():" + objectToString);
			Method myToString = null;
			int a = 0;

			try {
				myToString = accessMethod(ComLogUtil.class, "toString", new Class[] {});
			} catch (Exception e) {
			}
			Method intToString = accessMethod(a, "toString");
			System.out.println("myToString():" + myToString);
			System.out.println("whether they equals:" + (objectToString == myToString));
			System.out.println("IntToString():" + intToString);
			System.out.println("fload toString():" + accessMethod(1.1f, "toString"));
			System.out.println("string toString():" + accessMethod("", "toString"));
			System.out.println("string toString() getDeclaringClass:" + accessMethod("", "toString").getDeclaringClass());
			System.out.println("string toString() .getDeclaringClass() == Object.class:" + (accessMethod("", "toString").getDeclaringClass() == Object.class));
			System.out.println("StringBuffer toString():" + accessMethod(new StringBuffer(), "toString"));
			System.out.println("arr toString():" + accessMethod(new int[] {1, 2}, "toString"));
			System.out.println("arr toString() .getDeclaringClass() == Object.class:" + (accessMethod(new int[] {1, 2}, "toString").getDeclaringClass() == Object.class));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}