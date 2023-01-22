package util.commonUtil;

import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import util.commonUtil.interfaces.IConfigManager;

/**
 *
 */
public final class ConfigManager {

	//private static HashMap<String, String> config = new HashMap<String, String>();

	private final static String className = ConfigManager.class.getName();

	private ConfigManager() {}

	/**
	 * get a config manager with given property file and namespace
	 * @param propertyFile
	 * @param namespace
	 * @return
	 */
	public static IConfigManager getConfigManager(URL propertyURL, String namespace) {
		return new ConfigManagerCustomized(propertyURL, namespace);
	}

	/**
	 * get the default util config manager with given namespace
	 * @param namespace
	 * @return
	 */
	public static IConfigManager getConfigManager(String namespace) {
		return getConfigManager(utilPropertiesPath, namespace);
	}

	/**
	 * get a config manager with given property file and default namespace ""
	 * @param propertyFile
	 * @return
	 */
	public static IConfigManager getConfigManager(URL propertyURL) {
		return new ConfigManagerCustomized(propertyURL, "");
	}

	/**
	 * get default util config manager with caller's className as namespace.
	 * @return
	 */
	public static IConfigManager getConfigManager() {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		int i = 1;
		String stackClassName;
		while(className.equals((stackClassName = stackTrace[i++].getClassName())));
		return new ConfigManagerCustomized(utilPropertiesPath, stackClassName.substring(stackClassName.lastIndexOf('.') + 1, stackClassName.length()));
	}


	private static final URL utilPropertiesPath = ConfigManager.class.getResource("utilConfig.properties");

	private static IConfigManager configManager = getConfigManager(utilPropertiesPath, "");

	public static String getString(String key) {
		return configManager.getString(key);
	}

	/**
	 * get value as Boolean
	 * @param key
	 * @return true if value is 'true' case insensitively; else false.
	 */
	public static Boolean getBoolean(String key) {
		return configManager.getBoolean(key);
	}

	public static String[] getArray(String key) {
		return configManager.getArray(key);
	}

	public static List<String> getList(String key) {
		return  configManager.getList(key);
	}

	private static class ConfigManagerCustomized implements IConfigManager{

		/**
		 * @key: protperties file path in jar.
		 * @value: the loaded properties object.
		 */
		private static Map<String, Properties> cache = new HashMap<String, Properties>();

		String namespace;

		Properties properties;

		ConfigManagerCustomized(URL propertyURL, String namespace) {
			Properties cachedProperties = cache.get(propertyURL.getPath());
			if(cachedProperties == null) {
				try {
					this.properties = new Properties();
					
//					this.properties.load(propertyURL.openStream());
					// use InputStreamReader to resolve Chinese characters
					// refer: https://stackoverflow.com/questions/30755014/reading-from-property-file-containing-utf-8-character
					this.properties.load(new InputStreamReader(propertyURL.openStream(), Charset.forName("UTF-8")));
					
					cache.put(propertyURL.getPath(), properties);
				} catch (Exception e) {
					e.printStackTrace();
					System.err.println("init failed propertyFile:" + propertyURL + ", namespace:" + namespace);
				}
			} else {
				this.properties = cachedProperties;
			}
			this.namespace = namespace;
		}

		@Override
		public String getString(String key) {
			return properties.getProperty(ComStrUtil.isBlank(namespace) ? key : (namespace + "." + key));
		}

		@Override
		public Boolean getBoolean(String key) {
			return "true".equalsIgnoreCase(getString(key));
		}

		@Override
		public String[] getArray(String key) {
			String keyStr = getString(key);
			return ComStrUtil.isBlankOrNull(keyStr) ? new String[] {} : keyStr.split(ComRegexUtil.EOLRegex);
		}

		@Override
		public List<String> getList(String key) {
			String keyStr = getString(key);
			return ComStrUtil.isBlankOrNull(keyStr) ? new ArrayList<String>() : new ArrayList<String>(Arrays.asList(getString(key)));
		}

	}

	public static void main(String[] args) {
		try {
			System.out.println(ComReflectUtil.accessFieldVal(ComLogUtil.class, "overwriteSYSO"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		ComReflectUtil.class.getre
		System.out.println(className);
		System.out.println(new ConfigManager());
		System.out.println(getString("ComLogUtil.excludeClasses"));
		System.out.println(ComLogUtil.objToString(getArray("ComLogUtil.excludeClasses")));
		System.out.println(ComLogUtil.objToString(getList("ComLogUtil.excludeClasses")));
		System.out.println(getString("ComLogUtil.redirectLogFile"));
	}

}
