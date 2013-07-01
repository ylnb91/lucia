/**
 * 
 */
package dblucia;

import java.io.File;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author y.lucia
 * @email ylnb91@gmail.com
 * 
 */
public class TableInfo {

	public static Map<String, String> getTableInfo(String clazz) {
		Class<?> model = null;
		Map<String, String> map = new HashMap<String, String>();
		try {
			model = Class.forName(clazz);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Field[] field = model.getDeclaredFields();
		for (int i = 0; i < field.length; i++) {
			String type = field[i].getType().getName();
			if (type.contains("."))
				type = type.substring(type.lastIndexOf(".") + 1);
			else
				type = type.substring(0, 1).toUpperCase() + type.substring(1);
			map.put(field[i].getName(), type);
		}
		return map;
	}

	public static List<String> getTableNames() {
		List<String> tableNames = new ArrayList<String>();
		String sql = "show tables";
		DBManager conn = DBManager.getInstance();
		ResultSet rs = conn.excuteQuery(sql);
		try {
			while (rs.next()) {
				String name = rs.getString(1);
				name = name.substring(0, 1).toUpperCase() + name.substring(1);
				tableNames.add(name);
			}
		} catch (SQLException e) {
			System.out.println("show tables 查询数据库所有表名失败");
			e.printStackTrace();
		}
		conn.releaseConnection();
		return tableNames;
	}

	public static List<String> getClassRoot() {
		List<String> tablenames = getTableNames();
		List<String> classRoot = new ArrayList<String>();
		List<String> allClassRoot = new ArrayList<String>();
		File rootFile = new File(TableInfo.class.getResource("/").getFile()
				.replaceFirst("/", ""));
		interDir(rootFile, allClassRoot);
		for (String s : allClassRoot) {
			for (String t : tablenames) {
				if (s.contains(t + ".class"))
					classRoot.add(s.substring(s.indexOf("classes.") + 8));
			}
		}
		return classRoot;
	}

	private static void interDir(File rootFile, List<String> list) {
		if (rootFile.isDirectory()) {
			File[] files = rootFile.listFiles();
			for (File f : files) {
				interDir(f, list);
			}
		} else {
			if (rootFile.getPath().indexOf(".class") != -1) {
				String className = rootFile.getPath().replace("\\", ".");
				list.add(className);
			}
		}
	}
}
