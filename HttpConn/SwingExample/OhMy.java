package GetAndPost;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

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
public class OhMy extends JFrame {
	private JButton jbt = new JButton("GO");
	private JPanel jp = new JPanel();
	private JTextField jtf1 = new JTextField(10);
	private JTextField jtf2 = new JTextField(10);

	static {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public OhMy() {
		this.jp.add(jtf1);
		this.jp.add(jtf2);
		this.jp.add(jbt);
		this.add(jp);

		jbt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String num = jtf1.getText();
				String pwd = jtf2.getText();
				OhMy.go(num, pwd, "");
			}
		});
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setResizable(false);
		this.setLocation(530, 200);
		this.setTitle("Go");
		this.setVisible(true);
	}

	public static void go(String num, String pwd, String fake) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(
				"http://202.200.206.54/(0uyrqu550yog5nqi1fu4md45)/Default2.aspx");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		// 伪造参数
		nvps.add(new BasicNameValuePair("__VIEWSTATE",
				"dDwtMTcyNDQ4MTQ0ODs7PpKYCWhcxjpbo8FvrJR9siL4DQwX"));
		nvps.add(new BasicNameValuePair("TextBox1", num));
		nvps.add(new BasicNameValuePair("TextBox2", pwd));
		nvps.add(new BasicNameValuePair("RadioButtonList1", ""));
		nvps.add(new BasicNameValuePair("Button1", ""));
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
				HttpGet hc = new HttpGet("http://202.200.206.54/" + location);
				response = httpClient.execute(hc);
				EntityUtils.consume((response.getEntity()));
				// 获取个人信息
				hc = new HttpGet(
						"http://202.200.206.54/(0uyrqu550yog5nqi1fu4md45)/readimagexs.aspx?xh="
								+ num);
				response = httpClient.execute(hc);
				byte[] bit = EntityUtils.toByteArray(response.getEntity());
				if (bit.length > 0) {
					BufferedOutputStream out = null;
					try {
						out = new BufferedOutputStream(new FileOutputStream(num
								+ ".jpg"));
						out.write(bit);
						out.flush();
					} finally {
						if (out != null)
							out.close();
					}
				}
			} else {
				System.out.println("获取失败");
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

	public static void main(String[] args) {
		new OhMy();
	}
}
