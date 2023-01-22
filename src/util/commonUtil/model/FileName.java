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

	public String getFileNameOnly() {
		return fileInfo.getFileName();
	}
	
	public String getFileNameAndExtension() {
		return fileInfo.getFileName() + fileInfo.getFileExt();
	}
	
	public FileName setExt(String ext) {
		this.fileInfo.setFileExt(ext);
		return this;
	}
	
	public String getExt() {
		return this.fileInfo.getFileExt();
	}
	
	public String getExt(boolean needDot) {
		return this.fileInfo.getFileExt(needDot);
	}
	
	public String getDir() {
		return this.fileInfo.getDir();
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
	 * @return the full file path.
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