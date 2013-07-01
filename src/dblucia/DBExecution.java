/**
 * 
 */
package dblucia;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author y.lucia
 * @email ylnb91@gmail.com
 *
 */

public class DBExecution<T> {

	protected Map<String, Map<String, String>> maps = Tables.getMaps();
	protected Class<T> clazz;

	@SuppressWarnings("unchecked")
	public DBExecution() {
		Type genType = this.getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		this.clazz = (Class<T>) params[0];
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> getList(String sql) {
		List result = new ArrayList();
		DBManager conn = DBManager.getInstance();
		ResultSet rs = conn.excuteQuery(sql);
		Map<String, String> map = maps.get(this.clazz.getName());
		T model = null;
		try {
			while (rs.next()) {
				try {
					model = (T) Class.forName(this.clazz.getName())
							.newInstance();
				} catch (InstantiationException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				Iterator i = map.entrySet().iterator();
				Method method = null;
				Method method1 = null;
				while (i.hasNext()) {
					Entry<String, String> m = (Entry<String, String>) i
							.next();
					try {
						String type = m.getValue();
						String key = m.getKey();
						method = rs.getClass().getMethod("get" + type,
								String.class);
						method1 = model.getClass()
								.getMethod(
										"set"
												+ key.substring(0, 1)
														.toUpperCase()
												+ key.substring(1),
										StringUtil.ex(type));
						try {
							method1.invoke(model, method.invoke(rs, key));
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					}
				}
				result.add(model);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conn.releaseConnection();
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public T getOneById(int id) {
		String sql = "select * from "
				+ clazz.getName()
						.substring(clazz.getName().lastIndexOf(".") + 1)
						.toLowerCase() + " where id = " + id;
		DBManager conn = DBManager.getInstance();
		ResultSet rs = conn.excuteQuery(sql);
		Map<String, String> map = maps.get(this.clazz.getName());
		T model = null;
		try {
			rs.next();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		try {
			model = (T) Class.forName(this.clazz.getName()).newInstance();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		Iterator i = map.entrySet().iterator();
		Method method = null;
		Method method1 = null;
		while (i.hasNext()) {
			Entry<String, String> m = (Entry<String, String>) i.next();
			try {
				String type = m.getValue();
				String key = m.getKey();
				method = rs.getClass().getMethod("get" + type,
						String.class);
				method1 = model.getClass().getMethod(
						"set" + key.substring(0, 1).toUpperCase()
								+ key.substring(1), StringUtil.ex(type));
				try {
					method1.invoke(model, method.invoke(rs, key));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		conn.releaseConnection();
		return model;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void update(T model) {
		String sql = "update "
				+ clazz.getName()
						.substring(clazz.getName().lastIndexOf(".") + 1)
						.toLowerCase() + " set ";
		Method method = null;
		Map<String, String> map = maps.get(this.clazz.getName());
		Iterator i = map.entrySet().iterator();
		while (i.hasNext()) {
			Entry<String, String> m = (Entry<String, String>) i.next();
			try {
				String key = m.getKey();
				if (key.equals("id"))
					continue;
				method = model.getClass().getMethod(
						"get" + key.substring(0, 1).toUpperCase()
								+ key.substring(1));
				try {
					if (m.getValue().equals("String"))
						sql += key + " = '" + method.invoke(model) + "', ";
					else
						sql += key + " = " + method.invoke(model) + ", ";
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		try {
			method = model.getClass().getMethod("getId");
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		try {
			sql = sql.substring(0, sql.lastIndexOf(",")) + " where id = "
					+ method.invoke(model);
			System.out.print(sql);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		DBManager conn = DBManager.getInstance();
		conn.excuteUpdate(sql);
		conn.releaseConnection();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void create(T model) {
		String sql = "insert into "
				+ clazz.getName()
						.substring(clazz.getName().lastIndexOf(".") + 1)
						.toLowerCase() + " (";
		String sql1 = "";
		Method method = null;
		Map<String, String> map = maps.get(this.clazz.getName());
		Iterator i = map.entrySet().iterator();
		while (i.hasNext()) {
			Entry<String, String> m = (Entry<String, String>) i.next();
			try {
				String key = m.getKey();
				if (key.equals("id"))
					continue;
				sql += key + ", ";
				method = model.getClass().getMethod(
						"get" + key.substring(0, 1).toUpperCase()
								+ key.substring(1));
				try {
					if (m.getValue().equals("String"))
						sql1 += "'" + method.invoke(model) + "', ";
					else
						sql1 += method.invoke(model) + ", ";
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}

		sql = sql.substring(0, sql.lastIndexOf(",")) + ") values ("
				+ sql1.substring(0, sql1.lastIndexOf(",")) + ")";
		System.out.print(sql);
		DBManager conn = DBManager.getInstance();
		conn.excuteUpdate(sql);
		conn.releaseConnection();
	}

	public void deleteById(int id) {
		String sql = "delete from "
				+ clazz.getName()
						.substring(clazz.getName().lastIndexOf(".") + 1)
						.toLowerCase() + " where id=" + id;
		DBManager conn = DBManager.getInstance();
		try {
			conn.excuteSql(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conn.releaseConnection();
	}

}
