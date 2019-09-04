package util.commonUtil.model;

import java.io.File;

import util.commonUtil.ComFileUtil;

/**
 * An entity of file directory, fileName and file extension
 * @author Administrator
 *
 */
public class FileInfo {
	/**
	 * file directory including '/' at the end. e.g: e:/music/
	 */
	public String dir;
	/**
	 * file directory. e.g: musicName
	 */
	public String fileName;
	/**
	 * file extension including dot. e.g: .txt  .java
	 */
	public String fileExt;

	public FileInfo(String dir, String fileName, String fileExt) {
		this.dir = dir;
		if(!dir.endsWith(ComFileUtil.SEPARATOR)) this.dir += ComFileUtil.SEPARATOR;

		this.fileName = fileName;
		setFileExt(fileExt);
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	/**
	 * get the fileName(extension not included). e.g. panda
	 * @param fileName
	 * @return fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * set the fileName(extension not included)
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * get file extension including dot. e.g. panda.jpg
	 * @return
	 */
	public String getFileExt() {
		return fileExt;
	}

	/**
	 * get file extension
	 * @param needDot
	 * @return
	 */
	public String getFileExt(boolean needDot) {
		return needDot ? fileExt : fileExt.substring(1);
	}

	/**
	 * set file extension including dot.
	 * @param fileExt both withDot and withoutDot are OK.
	 * @return
	 */
	public void setFileExt(String fileExt) {
		this.fileExt = getFileExtensionWithDot(fileExt);
	}

	public String getFullFilePath() {
		return dir + fileName + fileExt;
	}

	public File toFile() {
		return new File(getFullFilePath());
	}

	@Override
	public String toString() {
		return "FileInfo [dir=" + dir + ", fileName=" + fileName + ", fileExt=" + fileExt + "]";
	}

	public static String getFileExtensionWithDot(String fileExt) {
		return fileExt.startsWith(".") ? fileExt : ('.' + fileExt);
	}

}