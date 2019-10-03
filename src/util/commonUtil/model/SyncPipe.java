package util.commonUtil.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import util.commonUtil.ComFileUtil;

public class SyncPipe implements Runnable {

	/**
	 *
	 * @param istrm_ inputStream
	 * @param ostrm_ outputStream. Will not output if null.
	 * @param collectedResult all output String. Will not collect if null.
	 */
	public SyncPipe(InputStream istrm_, OutputStream ostrm_, StringBuffer collectedResult) {
		this(istrm_, ostrm_, collectedResult, ComFileUtil.UTF8, ComFileUtil.UTF8);
	}

	/**
	 *
	 * @param istrm_ inputStream
	 * @param ostrm_ outputStream. Will not output if null.
	 * @param collectedResult all output String. Will not collect if null.
	 * @param inputCharset default UTF-8
	 * @param outputCharset default UTF-8
	 */
	public SyncPipe(InputStream istrm_, OutputStream ostrm_, StringBuffer collectedResult, String inputCharset, String outputCharset) {
		this.istrm_ = istrm_;
		this.collectedResult = collectedResult;
		this.inputCharset = inputCharset;
		this.outputCharset = outputCharset;
		this.ostrm_ = ostrm_;
	}

	@Override
	public void run() {
		/**/
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(istrm_, inputCharset));
//			br = new BufferedReader(new InputStreamReader(istrm_, "GBK"));
			for(String readStr; (readStr = br.readLine()) != null;) {
//				resStr.append(readStr);
				if(collectedResult != null) collectedResult.append(readStr).append(ComFileUtil.EOL);
				if(ostrm_ != null) ostrm_.write(("read:" + readStr + ComFileUtil.EOL).getBytes(outputCharset));
//				System.out.println("cmdLog:" + readStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(br != null) {
				try {br.close();} catch (IOException e) {e.printStackTrace();}
				br = null;
			}
		}

		/*
		try {
			final byte[] buffer = new byte[1024];
			for (int length = 0; (length = istrm_.read(buffer)) != -1;) {
				ostrm_.write(buffer, 0, length);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
	}

	private final StringBuffer collectedResult;

	private final OutputStream ostrm_;
	private final InputStream istrm_;
	private final String inputCharset;
	private final String outputCharset;
}