/**
 * 
 */
package test;

import service.ProductService;

/**
 * @author y.lucia
 * @email ylnb91@gmail.com
 * 
 */
public class Test {

	public static void main(String[] args) {
        ProductService p = new ProductService();
		p.deleteById(4);
	}
}