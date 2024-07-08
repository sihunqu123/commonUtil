package util.projectUtil;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
// import java.math.BigDecimal;
// import java.util.regex.Matcher;
// import java.util.regex.Pattern;
// import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public abstract class ProjectUtil{
  // public static Logger log = Logger.getLogger(new CommonUtil().getClass());
	
	public static void main(String[] args) {
		System.out.println("file:///e/p/a.java");
		System.out.println("file:///e/p/");
		System.out.println("https://www.baidu.com");
		System.out.println("file:///e/a.txt not ab");
		System.out.println("file:///e/a.txt Not ab");
		System.out.println("[ERROR] file:///e/p/");
		System.out.println("[WARN] file:///e/p url");
		System.out.println("[DEBUG] file:///e/p openFolderAndSelect.bat arg");
		System.out.println("[FATAL] file:///e/p command");
		System.out.println("[ERROR] file:///e/p/a.txt");
	}
	
}
