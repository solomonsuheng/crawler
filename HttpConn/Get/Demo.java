package GetAndPost;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * @ClassName: Demo
 * @Description: TODO使用Get方法访问URL资源
 * @author solomonsuheng
 * @date 2014年5月19日 上午11:02:01
 */
public class Demo {
	public static void main(String[] args) {
		CloseableHttpClient httpClient = HttpClients.createDefault();// 创建HttpClient(相当于打开一个客户端)
		HttpGet httpGet = new HttpGet("http://www.baidu.com");// 使用Get方法访问URL,创建一个Get访问方式
		CloseableHttpResponse response = null;// 创建一个访问后的response连接
		try {
			response = httpClient.execute(httpGet);// httpClient客户端执行Get方法访问URL并将底层Http连接返回给response1连接
			System.out.println(response.getStatusLine());// 返回状态码(如404,200)
			HttpEntity entity1 = response.getEntity();// 从response流中获取一个Http实体
			System.out.println(EntityUtils.toString(entity1));// 将返回响应流的内容输出(即网页内容)
			httpClient.close();// 因为response一直连接着底层Http所以一定要确保使用该方法关闭所有连接
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
