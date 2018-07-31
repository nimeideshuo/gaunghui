package com.ahjswy.cn.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ahjswy.cn.model.DefDocPayType;
import com.ahjswy.cn.response.RespServiceInfor;

public class JSONUtil {

	public static String getSimpleJSONString(String[] paramArrayOfString1, String[] paramArrayOfString2) {
		if ((paramArrayOfString1 == null) || (paramArrayOfString1.length == 0))
			return "{}";
		String str = "{\"" + paramArrayOfString1[0] + "\":" + "\"" + paramArrayOfString2[0] + "\"";
		for (int i = 1;; ++i) {
			if (i >= paramArrayOfString1.length)
				return str + "}";
			str = str + ",\"" + paramArrayOfString1[i] + "\":" + "\"" + paramArrayOfString2[i] + "\"";
		}
	}

	// 判断是否是 json 串
	public static boolean isJson(String paramString) {
		if (!paramString.isEmpty()) {
			return (((paramString.startsWith("{"))) || ((paramString.startsWith("["))));
		}
		return false;
	}

	// public static String makeJsonStr(Map<String, Object> paramMap) {
	// if ((paramMap == null) || (paramMap.size() == 0))
	// return "{}";
	// StringBuilder localStringBuilder = new StringBuilder("{");
	// Iterator localIterator = paramMap.keySet().iterator();
	// while (true) {
	// if (!(localIterator.hasNext()))
	// return localStringBuilder.substring(0, -1 +
	// localStringBuilder.length()).toString() + "}";
	// String str1 = (String) localIterator.next();
	// String str2 = (String) paramMap.get(str1);
	// if ((TextUtils.isEmpty(str2)) || ("null".equals(str2)))
	// continue;
	// localStringBuilder.append("\"" + str1 + "\":\"" + str2.replaceAll("\\\"",
	// "\\\\\"") + "\",");
	// }
	// }

	// public static String makeJsonStr2(Map<String, String> paramMap) {
	// if ((paramMap == null) || (paramMap.size() == 0))
	// return "{}";
	// StringBuilder localStringBuilder = new StringBuilder("{");
	// Iterator localIterator = paramMap.keySet().iterator();
	// while (true) {
	// if (!(localIterator.hasNext()))
	// return localStringBuilder.substring(0, -1 +
	// localStringBuilder.length()).toString() + "}";
	// String str1 = (String) localIterator.next();
	// String str2 = (String) paramMap.get(str1);
	// if ((TextUtils.isEmpty(str2)) || ("null".equals(str2)))
	// continue;
	// localStringBuilder.append("\"" + str1 + "\":\"" + str2.replaceAll("\\\"",
	// "\\\\\"") + "\",");
	// }
	// }

	/**
	 * 废弃 请使用 toJSONString();
	 * 
	 * @param obj
	 * @return
	 * 
	 */
	public static String object2Json(Object obj) {
		ObjectMapper localObjectMapper = new ObjectMapper();
		StringWriter localStringWriter = new StringWriter();
		try {
			JsonGenerator localJsonGenerator = new JsonFactory().createJsonGenerator(localStringWriter);
			localObjectMapper.writeValue(localJsonGenerator, obj);
			localJsonGenerator.close();
			String localString = localStringWriter.toString();
			return localString;
		} catch (Exception e) {
			e.printStackTrace();
			DocUtils.insertLog(e, obj == null ? "obj is null" : obj.toString());
		}
		return null;
	}

	// json 转 list hashMap
	public static List<HashMap<String, String>> parse2ListMap(String paramString) {
		try {
			List<HashMap<String, String>> list_map = new ArrayList<HashMap<String, String>>();
			JSONArray localJSONArray = new JSONArray(paramString);

			for (int i = 0; i < localJSONArray.length(); i++) {
				list_map.add(parse2Map(localJSONArray.get(i).toString()));
			}
			return list_map;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	// json 转 hashMap
	public static HashMap<String, String> parse2Map(String paramString) {
		HashMap<String, String> localHashMap = new HashMap<String, String>();
		try {
			JSONObject localJSONObject = new JSONObject(paramString);
			Iterator<String> localIterator = localJSONObject.keys();
			while ((localIterator.hasNext())) {
				String str = (String) localIterator.next();
				localHashMap.put(str, localJSONObject.getString(str));
			}
			return localHashMap;
		} catch (JSONException localJSONException) {
			localJSONException.printStackTrace();
		}

		return null;
	}

	public static <T> T readValue(String content, Class<T> valueType) {
		ObjectMapper localObjectMapper = new ObjectMapper();
		try {
			T localObject = localObjectMapper.readValue(content, valueType);
			return localObject;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * readValue(String json, Class<T> classOfT)
	 * 
	 * @param content
	 * @param valueType
	 * @return
	 */

	public static <T> T fromJson(String json, Class<T> classOfT) {
		if (!TextUtils.isEmptyS(json) || classOfT == null) {
			return null;
		}
		try {
			return com.alibaba.fastjson.JSONObject.parseObject(json, classOfT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T parseObject(String json, Class<T> classOfT) {
		if (!TextUtils.isEmptyS(json) || classOfT == null) {
			return null;
		}
		try {
			return com.alibaba.fastjson.JSONObject.parseObject(json, classOfT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * use object2Json();
	 * 
	 * @param obj
	 * @return
	 */
	@Deprecated
	public static String toJSONString(Object obj) {
		return com.alibaba.fastjson.JSONObject.toJSONString(obj);
	}

	public static <T> List<T> parseArray(String json, Class<T> classOfT) {
		if (!TextUtils.isEmptyS(json) || classOfT == null) {
			return null;
		}
		try {
			return com.alibaba.fastjson.JSONObject.parseArray(json, classOfT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * parseArray(String json, Class<T> classOfT);
	 * 
	 * @param content
	 * @param paramClass
	 * @return
	 */
	// String json to list
	public static <T> List<T> str2list(String content, Class<T> paramClass) {
		ObjectMapper objectMapper = new ObjectMapper();
		org.codehaus.jackson.JsonParser parser;
		List<T> list = new ArrayList<T>();
		if (!TextUtils.isEmptyS(content)) {
			return list;
		}
		try {
			parser = objectMapper.getJsonFactory().createJsonParser(content);
			JsonNode nodes = parser.readValueAsTree();
			// list = new ArrayList<T>(nodes.size());
			for (JsonNode node : nodes) {
				list.add(objectMapper.readValue(node, paramClass));
			}
			return list;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;

	}

	/* 支付方式Json转换成对象 */
	public static List<DefDocPayType> jsonToListDefDocPayType(String payType) {
		if (payType == null || payType.length() == 0 || payType.equals("null")) {
			return null;
		}
		List<DefDocPayType> paytypeList = new ArrayList<DefDocPayType>();
		try {
			JSONArray array = new JSONArray(payType);
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObjectPayType = array.getJSONObject(i);
				DefDocPayType pay = new DefDocPayType();
				String itemid = jsonObjectPayType.getString("id");
				String paytypename = jsonObjectPayType.getString("name");
				String docid = jsonObjectPayType.getString("accountid");
				if ((TextUtils.isEmpty(itemid)) || ("null".equals(itemid))) {
					continue;
				} else {
					pay.setItemid(Long.parseLong(itemid));
				}
				if ((TextUtils.isEmpty(paytypename)) || ("null".equals(paytypename))) {
					continue;
				} else {
					pay.setPaytypename(paytypename);
				}
				if ((TextUtils.isEmpty(docid)) || ("null".equals(docid))) {
					continue;
				} else {
					pay.setDocid(Long.parseLong(docid));
				}
				paytypeList.add(pay);
			}
			return paytypeList;
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;

	}

	public static String Info;
	public static String Json;
	public static String Status;
	public static String Data;
	public static String Desc;
	public static String Code;
	public static String Page;
	public static int PageSize;
	public static int MaxRow;
	public static int PageIndex;
	public static String TitleList;
	public static String StartTime;
	public static double Sum;
	public static int DocID;
	public static boolean Result;

	/**
	 * 返回json 处理
	 * 
	 * @param jsonMessage
	 *            需要处理的json(新服务的数据)
	 * @param String
	 *            Info 返回信息
	 * @param String
	 *            Json 返回的json
	 * @param String
	 *            Status 返回的json状态
	 * @param String
	 *            Data 返回json中的数据
	 * @param String
	 *            Desc 返回json中的详情
	 * @param String
	 *            Code 返回json中的Code
	 * @param String
	 *            Page 返回json中的Page
	 * @param int
	 *            PageSize 返回json中的PageSize
	 * @param int
	 *            MaxRow 返回json中的MaxRow
	 * @param int
	 *            PageIndex 返回json中的PageIndex
	 * @param String
	 *            TitleList 返回json中的TitleList
	 * @param String
	 *            StartTime 返回json中的当前时间
	 * @param double
	 *            Sum 返回json中的Sum
	 * @param int
	 *            DocID 返回json中的单据id
	 * @param boolean
	 *            Result 返回结果状态
	 * @return
	 */
	public static void readValue2(String jsonMessage) {
		try {
			if (TextUtils.isEmpty(jsonMessage)) {
				return;
			}
			// 第一层
			JSONObject jsonInfor = new JSONObject(jsonMessage);
			Info = jsonInfor.getString("Info");
			Result = jsonInfor.getBoolean("Result");
			Json = jsonInfor.getString("Json");
			// 第二层
			if (TextUtils.isEmptyS(Json)) {
				JSONObject jsonObject = new JSONObject(Json);
				Status = jsonObject.getString("Status");
				Data = jsonObject.getString("Data");
				Desc = jsonObject.getString("Desc");
				Code = jsonObject.getString("Code");
				Page = jsonObject.getString("Page");
				PageSize = jsonObject.getInt("PageSize");
				MaxRow = jsonObject.getInt("MaxRow");
				PageIndex = jsonObject.getInt("PageIndex");
				TitleList = jsonObject.getString("TitleList");
				StartTime = jsonObject.getString("StartTime");
				Sum = jsonObject.getDouble("Sum");
				DocID = jsonObject.getInt("DocID");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	public static RespServiceInfor readValue3(String serviceinfor) {
		if (isJson(serviceinfor)) {
			RespServiceInfor serviceInfor = new RespServiceInfor();
			JSONObject jsonInfor;
			try {
				jsonInfor = new JSONObject(serviceinfor);
				serviceInfor.Info = jsonInfor.getString("Info");
				serviceInfor.Result = jsonInfor.getBoolean("Result");
				String respJson = jsonInfor.getString("Json");
				if (TextUtils.isEmptyS(respJson)) {
					RespServiceInfor.RespJson res = new RespServiceInfor.RespJson();
					JSONObject jsonObject = new JSONObject(respJson);
					res.Status = jsonObject.getString("Status");
					res.Data = jsonObject.getString("Data");
					res.Desc = jsonObject.getString("Desc");
					res.Code = jsonObject.getString("Code");
					res.Page = jsonObject.getString("Page");
					res.PageSize = jsonObject.getInt("PageSize");
					res.MaxRow = jsonObject.getInt("MaxRow");
					res.PageIndex = jsonObject.getInt("PageIndex");
					res.TitleList = jsonObject.getString("TitleList");
					res.StartTime = jsonObject.getString("StartTime");
					res.Sum = jsonObject.getDouble("Sum");
					res.DocID = jsonObject.getString("DocID");
					serviceInfor.Json = res;
				}
				return serviceInfor;

			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
		return null;
	}
}
