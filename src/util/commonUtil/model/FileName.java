package util.commonUtil.model;

import java.io.File;

import util.commonUtil.ComFileUtil;

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
	 * append on the filename part.
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
	
	public FileName setExt(String ext) {
		this.fileInfo.setFileExt(ext);
		return this;
	}
	
	public FileName getExt() {
		this.fileInfo.getFileExt();
		return this;
	}
	
	public FileName getExt(boolean needDot) {
		this.fileInfo.getFileExt(needDot);
		return this;
	}

	/**
	 * @return the full file path.
	 */
	@Override
	public String toString() {
		return new StringBuilder(fileInfo.getDir()).append(fileInfo.getFileName()).append(fileInfo.getFileExt()).toString();
	}
	
	
}