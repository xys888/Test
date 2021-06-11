package com.example.demo.util;


import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class HttpUtil {

	private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

	public static String httpSend(Map<String, Object> dataMap, String path) {
		HttpResponse resp = null;
		try {
			resp = HttpRequest.post(path).form(dataMap).send();
		} catch (Exception e) {
			logger.error("【http请求异常】", e);
		}
		String result = "error";
		if (resp != null) {
			if (resp.statusCode() == 200) {
				result = resp.body();
				try {
					result = new String(result.getBytes("ISO8859-1"), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					logger.error("【http请求异常】", e);
				}
			}
		}
		return result;
	}

	public static String httpSend(String body, String path) {
		HttpResponse resp = null;
		try {
			resp = HttpRequest.post(path).bodyText(body).contentType("application/json").charset("UTF-8").send();
		} catch (Exception e) {
			logger.error("【http请求异常】", e);
		}
		String result = "error";
		if (resp != null) {
			// if (resp.statusCode() == 200) {}
			result = resp.body();
			try {
				result = new String(result.getBytes("ISO8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				logger.error("【http请求异常】", e);
			}
		}
		return result;
	}

	public static void main(String[] args) {
		String result = httpSend(new HashMap<String, Object>(),
				"http://182.92.118.156:8088/9fbank-account2/main/index.htm");
		System.out.println(result);
	}
}
