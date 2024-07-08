package util.commonUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import util.commonUtil.model.SyncPipe;

public class ComCMDUtil {

	/**
	 * this method fit for complicate cmd, but have encoding issue.
	 * e.g:<br>
	 * cmd: "D:\workspace\wsJee\vget\ffmpeg.exe" -f concat -safe 0 -i  "E:\360CloudUI\temp\deleteMe_3JLqC.txt" -c copy  "E:\360CloudUI\temp\aa.flv"<br>
	 * can be succeed by invoking this mehtod
	 * @param cmd
	 * @param out
	 * @param errOut
	 * @param inputEncoding
	 * @return cmd output
	 * @throws Exception
	 */
	public static String runCMD(String cmd, OutputStream out, OutputStream errOut, String inputEncoding) throws Exception {
		String[] command = { "cmd", };
		Process p = Runtime.getRuntime().exec(command);
		StringBuffer sb = new StringBuffer();
		new Thread(new SyncPipe(p.getErrorStream()
				, errOut
				, sb)).start();
		new Thread(new SyncPipe(p.getInputStream()
				, out
				, sb)).start();

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(p.getOutputStream(), inputEncoding));
//		bw.write("chcp\n");
		cmd = cmd + " \n";
		bw.write(cmd);
		bw.flush();
		bw.close();
		// write any other commands you want here
		int returnCode = p.waitFor();
//		ComLogUtil.debug("CMD Return code = " + returnCode);
		return sb.toString();
	}


	/**
	 * run given windows cmd, and return cmd output.
	 * this method fit for simple cmd, and don't have encoding issue.
	 * e.g:<br>
	 * cmd: "D:\workspace\wsJee\vget\ffmpeg.exe" -f concat -safe 0 -i  "E:\360CloudUI\temp\deleteMe_3JLqC.txt" -c copy  "E:\360CloudUI\temp\aa.flv"<br>
	 * won't succeed by invoking this mehtod
	 * @param cmd
	 * @param out where standard out write
	 * @param errOut where error out write
	 * @return cmd output
	 * @throws Exception
	 */
	public static String runCMD_(String cmd, OutputStream out, OutputStream errOut) throws Exception {
		Process p = Runtime.getRuntime().exec("cmd /c " + cmd);
//		Process p = Runtime.getRuntime().exec(" " + cmd);
		StringBuffer sb = new StringBuffer();
		new Thread(new SyncPipe(p.getErrorStream()
				, errOut
				, sb)).start();
		new Thread(new SyncPipe(p.getInputStream()
				, out
				, sb)).start();
		int returnCode = p.waitFor();
		System.out.println("Return code = " + returnCode);
		return sb.toString();
	}

	public static String runCMD(String cmd) throws Exception {
//		return runCMD(cmd, null, null, ComFileUtil.UTF8);
		return runCMD(cmd, null, null, ComFileUtil.GBK);
	}

	public static void main(String[] args) throws IOException {
		/**
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
        System.out.println("Enter anything: ");
        String str = br.readLine();
        System.out.println("You have Entered: ");
        System.out.println(str);
        */
		try {
			System.out.println("res:" + runCMD("echo \"志村けんのバカ殿\" \n  chcp 936 \n echo \"志村けんのバカ殿\"\n "));
			System.out.println("res:" + runCMD("chcp"));
			System.out.println("res:" + runCMD("dir /d F:\\f8"));
			System.out.println("res:" + runCMD("dir /d F:\\f8\\����ר��"));
//			System.out.println("res:" + runCMD("dir /b", System.out, System.err));
			
//			String cmd = ComFileUtil.readFile2String("E:\\360CloudUI\\temp\\cmd.txt");
//			System.out.println(cmd);
//			System.out.println("res:" + runCMD(cmd, null, null, ComFileUtil.UTF8));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	private static String runCMD(String cmd) throws Exception {
		String[] command = { "cmd", };
		Process p = Runtime.getRuntime().exec(command);
		StringBuffer sb = new StringBuffer();
//		new Thread(new SyncPipe(p.getErrorStream(), sb)).start();
//		new Thread(new SyncPipe(p.getInputStream(), sb)).start();
//		ComLogUtil.info("cmd:" + cmd);
		cmd = cmd + " \n";
//		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(p.getOutputStream(), "GBK"));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(p.getOutputStream(), encode));
//		bw.write("chcp\n");
		bw.write(cmd);
		bw.flush();
		bw.close();
//		PrintWriter stdin = new PrintWriter(p.getOutputStream());

//		stdin.println();
		//stdin.println(cmd);
		// write any other commands you want here
//		stdin.close();
		int returnCode = p.waitFor();
		ComLogUtil.info("sb:" + sb);
		System.out.println("Return code = " + returnCode);
		return sb.toString();
	}
*/
}