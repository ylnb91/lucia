/**
 * 
 */
package dblucia;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author y.lucia
 * @email ylnb91@gmail.com
 * 
 */
public class Tables {

	private static Tables tables = null;
	private static Map<String, Map<String, String>> maps = null;

	protected Tables() {
		String s = null;
		maps = new HashMap<String, Map<String, String>>();
		List<String> classRoot = TableInfo.getClassRoot();
		for (String cr : classRoot) {
			s = cr.substring(0, cr.indexOf(".class"));
			maps.put(s, TableInfo.getTableInfo(s));
		}
	}

	public static Tables getTables() {
		if (tables == null || maps == null) {
			tables = new Tables();
		}
		return tables;
	}

	public static Map<String, Map<String, String>> getMaps() {
		if (tables == null || maps == null) {
			getTables();
		}
		return maps;
	}

}
