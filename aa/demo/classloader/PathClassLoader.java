package demo.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PathClassLoader extends ClassLoader {
	private String classPath;

	public PathClassLoader(String classPath) {
		this.classPath = classPath;
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		byte[] classData;
		try {
			classData = getData(name);
		} catch (IOException e) {
			throw new ClassNotFoundException(e.getMessage());
		}
		return defineClass(name, classData, 0, classData.length);
	}

	/**
     * read given class to byte array.
     * @param className
     * @return
     * @throws IOException
     */
    private byte[] getData(String className) throws IOException {
        String path = classPath + File.separatorChar + className.replace('.', File.separatorChar) + ".class";
        InputStream is = null;
        ByteArrayOutputStream stream = null;
        try {
            is = new FileInputStream(path);
            stream = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];
            int num = 0;
            while ((num = is.read(buffer)) != -1) {
                stream.write(buffer, 0, num);
            }
            return stream.toByteArray();
        } finally {
            if(is != null) {is.close();is = null;}
            if(stream != null) {stream.close();stream = null;}
        }
    }

	public static void main(String args[]) {
		System.out.println("classLoasderPath:" + PathClassLoader.class.getResource("/").getPath());
		ClassLoader pcl = new PathClassLoader(PathClassLoader.class.getResource("/").getPath());
		try {
			Class c = pcl.loadClass("demo.classloader.TestClassLoad");
			System.out.println(c.newInstance());// 打印类加载成功.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}