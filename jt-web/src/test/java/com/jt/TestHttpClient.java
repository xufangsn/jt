package com.jt;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jt.util.HttpClientService;
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestHttpClient {
	
	@Autowired
	CloseableHttpClient httpClient;
	
	@Test
	public void test01() throws ClientProtocolException, IOException{

		//创建HTTPClient的实例
		//CloseableHttpClient httpClient = HttpClients.createDefault();   
		//定义访问IP
		String url = "https://www.baidu.com";		
		//设定请求  
		HttpGet httpGet = new HttpGet(url); 	
		//获取response对象
		CloseableHttpResponse response =  httpClient.execute(httpGet);	 
		//获取页面信息
		if(response.getStatusLine().getStatusCode() == 200) {
			System.out.println("恭喜请求成功!~");
			HttpEntity httpEntity = response.getEntity();
			String html = EntityUtils.toString(httpEntity);
			System.out.println(html);
		}else {
			throw new RuntimeException();
		}
		
	}
	
	@Autowired
	private HttpClientService httpClientService;
	@Test
	public void testUtil() {
		String url = "https://www.baidu.com";
		String result = httpClientService.doGet(url);
		System.out.println(result);
	}
}
