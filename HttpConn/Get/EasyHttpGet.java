package GetAndPost;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;

/**
 * @ClassName: EasyHttpGet
 * @Description: TODO最简单的HttpGet
 * @author solomonsuheng
 * @date 2014年5月19日 上午11:14:30
 */
public class EasyHttpGet {
	public static void main(String[] args) {
		try {
			System.out.println(Request.Get("http://www.google.com.hk")
					.execute().returnContent());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
