package com.ahjswy.cn.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.ahjswy.cn.app.AccountPreference;

import android.os.SystemClock;

public class HttpRequestUtils {
	private static AccountPreference ap = new AccountPreference();

	/**
	 * HTTP post请求 url 地址 json数据
	 * 
	 * @param url
	 * @param json
	 * @return
	 */
	private static HttpPost getHttpPost(String url, String json) {
		HttpPost request = null;
		try {
			request = new HttpPost(url);
			request.setHeader("Accept", "application/json");
			request.setHeader("Content-Type", "application/json");
			request.setHeader("User-Agent", "Mozilla/4.5");
		} catch (IllegalArgumentException e) {
			System.out.println("网络调用出现异常，请检查访问的URL的书写！");
			return null;
		} catch (Exception ex) {
			System.out.println("网络调用出现异常，请检查网络是否开启！");
			return null;
		}
		if (json != null) {
			try {

				StringEntity entity = new StringEntity(json, "UTF-8");
				request.setEntity(entity);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return request;
	}

	@SuppressWarnings("deprecation")
	public String Posts(String url, String json) {
		// String urls = "http://" + ap.getServerIp() + ":3721/" + url;
		String urls = "http://192.168.3.106:3721/" + url;
		System.out.println("urls>>" + urls);
		System.out.println("json>>>" + json);
		HttpPost request = getHttpPost(urls, json);
		String result = null;
		if (request == null) {
			result = "Post调用网络失败，网络有问题！";
			return result;
		}
		MLog.d(">>>do post url :" + url + "->params:" + urls + ">>>>" + json);
		try {
			HttpResponse response = new DefaultHttpClient().execute(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity());
			} else {
				result = "网络Post异常！请求失败！";
			}
			MLog.d(">>>do post res :" + result.toString());
		} catch (ClientProtocolException e) {
			result = "网络Post异常！ClientProtocolException错误！";

		} catch (IOException e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			if (!startOpen) {
				startOpen = true;
				SystemClock.sleep(300);
				return Post(mUrl, mJson);
			}
		}
		return result;
	}

	// 发送Get请求，获得响应查询结果
	public static String Get(String url) {
		// 获得HttpGet对象
		HttpGet request = null;
		String result = null;
		try {
			System.out.println(url);
			request = new HttpGet(url);
		} catch (IllegalArgumentException e) {
			result = "网络Get调用出现异常，请检查访问的URL的书写！";
			System.out.println(result);
			return result;
		} catch (Exception ex) {
			result = "网络Get调用出现异常，请检查网络是否开启！";
			System.out.println(result);
			return result;
		}

		try {
			HttpResponse response = new DefaultHttpClient().execute(request);
			System.out.println("Code>>" + response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity());
				return result;
			}
		} catch (ClientProtocolException e) {
			result = "网络Get异常！ClientProtocolException错误！";
			return result;
		} catch (IOException e) {
			result = "网络Get异常！IOException错误！";
			return result;
		}
		return null;
	}

	private String mUrl;
	private String mJson;
	private boolean startOpen;

	public String Post(String url, String json) {
		mUrl = url;
		mJson = json;
		url = "http://" + ap.getServerIp() + ":" + ap.getCGServerPort() + "/" + url;
		PrintWriter out = null;
		String result = "";
		BufferedReader br = null;
		try {
			MLog.d(">>>do post url :" + url + "->params:" + json);

			URL realUrl = new URL(url);
			URLConnection conn = realUrl.openConnection();
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("User-Agent", "Mozilla/4.5");
			// conn.setConnectTimeout(5000);
			// conn.setReadTimeout(5000);
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(json);
			out.flush();
			br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				result += line;
			}
			MLog.d(">>>do post res :" + result.toString());
		} catch (IOException e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			if (!startOpen) {
				startOpen = true;
				SystemClock.sleep(300);
				return Post(mUrl, mJson);
			}
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return result;
	}

	public String PostIp(String ip, String urls, String json) {
		String url = "http://" + ip + "/" + urls;
		PrintWriter out = null;
		String result = "";
		BufferedReader br = null;
		try {
			MLog.d(">>>do post url :" + url + "->params:" + json);

			URL realUrl = new URL(url);
			URLConnection conn = realUrl.openConnection();
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("User-Agent", "Mozilla/4.5");
			// conn.setConnectTimeout(5000);
			// conn.setReadTimeout(5000);
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(json);
			out.flush();
			br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				result += line;
			}
			MLog.d(">>>do post res :" + result.toString());
		} catch (IOException e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return result;
	}

}
