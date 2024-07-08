package util.commonUtil.model;

import java.io.File;

import util.commonUtil.ComFileUtil;
import util.commonUtil.ComLogUtil;
import util.commonUtil.ComRegexUtil;
import util.commonUtil.ComStrUtil;

/**
 * file "Name" operator
 * @author Administrator
 *
 */
public class FileName {
	FileInfo fileInfo;
	
	public FileName(String file) {
		this.fileInfo = ComFileUtil.getFileInfo(file);
	}
	
	public FileName(File file) {
		this.fileInfo = ComFileUtil.getFileInfo(file);
	}
	
	public FileName(String dir, String fileName, String fileExt) {
		this.fileInfo = new FileInfo(dir, fileName, fileExt);
	}
	
	/**
	 * append on the filenameOnly part before the extension. e.g. a.txt append "-suffix" => a-suffix.txt
	 * @param str
	 * @return
	 */
	public FileName append(String str) {
		this.fileInfo.setFileName(this.fileInfo.getFileName() + str);
		return this;
	}
	
	/**
	 * preAppend on the filename part.
	 * @param str
	 * @return
	 */
	public FileName preAppend(String str) {
		this.fileInfo.setFileName(str + this.fileInfo.getFileName());
		return this;
	}
	
	/**
	 * set the filename only part. e.g. a.txt setFileName("newFileName") => newFileName.txt
	 * @param str
	 * @return
	 */
	public FileName setFileName(String str) {
		this.fileInfo.setFileName(str);
		return this;
	}
	
	/**
	 * set the filename and ext part. e.g. a.txt setFileName("b.log") => b.log
	 * @param str
	 * @return
	 */
	public FileName setFileNameAndExt(String str) {
		String fileNameOnly = ComFileUtil.getFileName(str, false);
		String ext = ComFileUtil.getFileExtension(str, true);
		this.setFileName(fileNameOnly);
		this.setExt(ext);
		return this;
	}

	public String getFileNameOnly() {
		return fileInfo.getFileName();
	}
	
	/**
	 * get the filename and ext part. e.g.  a.txt
	 * @return e.g: a.txt
	 */
	public String getFileNameAndExtension() {
		return fileInfo.getFileName() + fileInfo.getFileExt();
	}
	
	/**
	 * set file extension including dot.
	 * @param ext both withDot and withoutDot are OK.
	 * @return
	 */
	public FileName setExt(String ext) {
		this.fileInfo.setFileExt(ext);
		return this;
	}
	
	/**
	 * file extension including dot. e.g: .txt  .java
	 */
	public String getExt() {
		return this.fileInfo.getFileExt();
	}
	
	
	public String getExt(boolean needDot) {
		return this.fileInfo.getFileExt(needDot);
	}
	
	/**
	 * 
	 * @return file directory including '/' at the end. e.g: e:/music/
	 */
	public String getDir() {
		return this.fileInfo.getDir();
	}
	
	/**
	 * 
	 * @param dir file directory including '/' at the end. e.g: e:/music/
	 */
	public void setDir(String dir) {
		this.fileInfo.setDir(dir);
	}
	
	/**
	 * 
	 * @return file directory including '/' at the end. e.g: e:/music/
	 */
	public String getDisk() {
		return this.fileInfo.getDisk();
	}
	
	
	public FileName increaseTailNum() {
		String fileName = this.fileInfo.getFileName();
		String regForTailNumber = "(?<=.{0,99}\\()\\d+(?=\\)$)";
		String tailNumber = ComRegexUtil.getMatchedString(fileName, regForTailNumber);
		String newFileName = null;
		if(ComStrUtil.isBlankOrNull(tailNumber)) {
			newFileName = fileName + " (1)";
		} else {
			int tailNumberInt = Integer.parseInt(tailNumber, 10);
			newFileName = fileName.replaceFirst(regForTailNumber, tailNumberInt + 1 + "");
		}
//		ComLogUtil.info("newFileName: " + newFileName);
		fileInfo.setFileName(newFileName);
		return this;
	}
	
	
	/**
	 * get the full file path. e.g. E:\Downloads\a.mp4
	 * @return
	 */
	public String getFullPathName() {
		return this.toString();
	}

	/**
	 * @return the full file path. e.g. E:\Downloads\a.mp4
	 */
	@Override
	public String toString() {
		return new StringBuilder(fileInfo.getDir()).append(fileInfo.getFileName()).append(fileInfo.getFileExt()).toString();
	}
	
	public File toFile() {
		return new File(this.toString());
	}
	
	
	public static void main(String[] args) {
//		System.out.println(new FileName("F:\\Downloads\\test\\Tokyo Hot jup0022  -.mp4").append("(1)"));
//		System.out.println(new FileName("F:\\Downloads\\test\\Tokyo Hot jup0022  -.mp4").increaseTailNum());
		System.out.println(new FileName("F:\\Downloads\\test\\Tokyo Hot jup0022  -.mp4").increaseTailNum().increaseTailNum());
	}
	
}