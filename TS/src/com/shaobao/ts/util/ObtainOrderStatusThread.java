package com.shaobao.ts.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.shaobao.ts.MainActivity;
import com.shaobao.ts.OrderService;
import com.shaobao.ts.R;
import com.shaobao.ts.R.string;
import com.shaobao.ts.entity.OrderEntity;

import android.content.Context;
import android.nfc.tech.IsoDep;
import android.os.Message;

public class ObtainOrderStatusThread extends Thread
{
//	response:{"data":{"orders":[]},"msg":"成功"}
//	response:{"data":{"orders":[
//	{"id":3,"cId":"765432","oId":"876890","client":"66","status":"新建","dId":"123456"},
//	{"id":4,"cId":"765432","oId":"876890","client":"66","status":"新建","dId":"123456"},
//	{"id":5,"cId":"67543","oId":"无","client":"韶宝","status":"新建","dId":"无"},
//	{"id":6,"cId":"t567898","oId":"无","client":"韶宝","status":"新建","dId":"h675433"},
//	{"id":7,"cId":"opuioyuiti","oId":"797","client":"韶宝","status":"新建","dId":"hlkjpouiop"}]},"msg":"成功"}
	public Context context = null;
	
	public ObtainOrderStatusThread(Context context )
	{
		this.context = context;
	}
	@Override
	public void run() 
	{
		// TODO Auto-generated method stub
		super.run();
		while(true)
		{
			if (OrderService.release)
			{
				return;
			}
			String result = con();
			if (result != null)
			{
				parser(result);
			}
//			OrderService.uiHandler.sendEmptyMessage(OrderService.MSG_FRESH_LIST);
			System.out.println("sycn thread:"+ result );
		
			try {
				Thread.currentThread().sleep(1000 * 10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		
		
		
	}
	
	private void parser(String str)
	{
		if (str != null)
		{
			try {
				JSONObject jobj = new JSONObject(str);
				String msg = jobj.getString("msg");
				if (msg.equals("成功")) 
				{
					
					JSONObject dataJOBJ = jobj.getJSONObject("data");
					JSONArray orders = dataJOBJ.getJSONArray("orders");
					if (orders == null || orders.length() == 0)
					{
						return;
					}else 
					{
						int length = orders.length();
						OrderService.orderEntitys.clear();
						for (int i = 0; i < length; i++) {
							OrderEntity od = parserOrderEntity((JSONObject)orders.get(i));
//							if (od != null) {
//								Boolean result = isExist(od.getId() , OrderService.orderEntitys);
//								if (!result)
//								{
//									od.setNew(true);
//									Message message = OrderService.uiHandler.obtainMessage();
//									message.obj = od.getId();
//									message.what =OrderService.MSG_NOTIFICATION;
//									OrderService.uiHandler.sendMessage(message);
//								}else
//								{
//									od.setNew(false);
//								}
								
//							}
							
							OrderService.orderEntitys.add(od);
						}
						
					}
				}
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	private OrderEntity parserOrderEntity(JSONObject orderOBJ)
	{
		OrderEntity od = new OrderEntity();
		
//		{"id":3,"cId":"765432","oId":"876890","client":"66","status":"新建","dId":"123456"},
		
		try {
			String id = orderOBJ.getString("id");
			String cId  = orderOBJ.getString("cId");
			String oId  = orderOBJ.getString("oId");
			String client  = orderOBJ.getString("client");
			String status  = orderOBJ.getString("status");
			String dId  = orderOBJ.getString("dId");
			od.setId(id);
			od.setCid(cId);
			od.setOid(oId);
			od.setClient(client);
			od.setStatus(status);
			od.setdId(dId);
//			System.out.println("id:" + id +" cId:" + oId +" oID" + oId +" client:" + client + " status:" + status +" dId:" +dId);
		} catch (JSONException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
		return od;
	}
	private boolean isExist(String id , ArrayList<OrderEntity> orderEntities)
	{
		int length = orderEntities.size();
		boolean result = false;
		for (int i = 0; i < length; i++) 
		{
			OrderEntity orderEntity = orderEntities.get(i);
			String oid = orderEntity.getId(); 
			if (oid.equals(id)) {
				result = true;
				
			}
			
		}
		return result;
	}
	public String con()
	{

		 	String result = null;
	        BufferedReader reader = null;
	        try {
	
	                 SSLSocketFactory sf =  SSLSocketUtil.createSSLSocketFactory();
	                 sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);  //允许所有主机的验证
	                 HttpParams params = new BasicHttpParams();
	                 HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
	                 HttpProtocolParams.setContentCharset(params,
	                         HTTP.DEFAULT_CONTENT_CHARSET);
	                 HttpProtocolParams.setUseExpectContinue(params, true);
	                 // 设置连接管理器的超时
	                 ConnManagerParams.setTimeout(params, 10000);
	                 // 设置连接超时
	                 HttpConnectionParams.setConnectionTimeout(params, 10000);
	                 // 设置socket超时
	                 HttpConnectionParams.setSoTimeout(params, 10000);
	                 // 设置http https支持
	                 SchemeRegistry schReg = new SchemeRegistry();
	                 schReg.register(new Scheme("http", PlainSocketFactory
	                         .getSocketFactory(), 80));
	                 schReg.register(new Scheme("https", sf, 443));
	                 ClientConnectionManager conManager = new ThreadSafeClientConnManager(
	                         params, schReg);
	        	
		            HttpClient client = new DefaultHttpClient(conManager , params);
		            
		            HttpPost request = new HttpPost();
		            request.setURI(new URI(context.getString(R.string.url)+"/mobile/sync"));
		           
		            List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		            postParameters.add(new BasicNameValuePair("action", "MobileSync"));
		            postParameters.add(new BasicNameValuePair("employeeId",OrderService.userEntity.getName()));
		            postParameters.add(new BasicNameValuePair("token", OrderService.userEntity.getToken()));
		            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
		                    postParameters);
		            request.setEntity(formEntity);
		 
		            HttpResponse response = client.execute(request);
		            reader = new BufferedReader(new InputStreamReader(response
		                    .getEntity().getContent()));
		            StringBuffer strBuffer = new StringBuffer("");
		            String line = null;
		            while ((line = reader.readLine()) != null) {
		                strBuffer.append(line);
		            }
		            result = strBuffer.toString();
	 
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                    reader = null;
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	 
	        return result;
	}
	
}
