package GetAndPost;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * @ClassName: DemoPost
 * @Description: TODOHttpPost方法
 * @author solomonsuheng
 * @date 2014年5月19日 上午11:12:22
 */
public class DemoPost {
	public static void main(String[] args) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(
				"http://www.gatheread.me:8080/gatheread/loginServlet");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("uname", "admin"));
		nvps.add(new BasicNameValuePair("upwd", "admin"));

		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			CloseableHttpResponse response = httpClient.execute(httpPost);
			System.out.println(response.getStatusLine());
			if ((response.getStatusLine().getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY || response
					.getStatusLine().getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY)) {
				// 302和301跳转
				Header hs = response.getFirstHeader("location");
				String location = hs.getValue();
				System.out.println("位置:" + location);
				// 已经正确登录
				HttpGet hc = new HttpGet(location);
				response = httpClient.execute(hc);
				System.out.println(EntityUtils.toString(response.getEntity()));

			} else {
				System.out.println(EntityUtils.toString(response.getEntity()));
			}

			response.close();
			httpClient.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
