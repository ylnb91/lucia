/**
 * 
 */
package dblucia;

/**
 * @author y.lucia
 * @email ylnb91@gmail.com
 * 
 */
public class StringUtil {

	public static Class<?> ex(String type){
		if(type.equals("String"))
			return String.class;
		if(type.equals("Int"))
			return int.class;
		return null;
	}
}
