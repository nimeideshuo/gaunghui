package com.ahjswy.cn.print;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import com.ahjswy.cn.app.MyApplication;
import com.ahjswy.cn.utils.MLog;

import android.os.Environment;
import android.util.Xml;

public class ModeHelper {
	private int bodytype;
	private String name;
	private List<HashMap<String, String>> textViews;

	public ModeHelper() {
		textViews = new ArrayList<HashMap<String, String>>();
	}

	public int getBodytype() {
		return this.bodytype;
	}

	public String getName() {
		return this.name;
	}

	public List<HashMap<String, String>> getTextViews() {
		return this.textViews;
	}

	public void parse() throws IOException {
		File extra = new File(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath())
				+ File.separator + "swy/swyxs" + File.separator + "printmode" + File.separator + "extra.xml");
		if (extra.exists()) {
			this.parse(new FileInputStream(extra));
		} else {
			this.parse(MyApplication.getInstance().getAssets().open("systemmode1.xml"));
		}
	}

	public void parseExtra() throws IOException {
		if (new File(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath()) + File.separator
				+ "swy/swyxs" + File.separator + "printmode" + File.separator + "extra.xml").exists()) {
			this.parse(MyApplication.getInstance().getAssets().open("systemmode1.xml"));
		}
	}

	public void parseSystem() throws IOException {
		this.parse(MyApplication.getInstance().getAssets().open("systemmode1.xml"));
	}

	/**
	 * 解析模板
	 * 
	 * @param input
	 */
	private void parse(InputStream input) {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		try {
			XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
			parser.setInput(input, "utf-8");
			int eventType = parser.getEventType();

			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					MLog.d("START_DOCUMENT  eventType  " + eventType);
					break;
				case XmlPullParser.START_TAG:

					String name = parser.getName();
					if ("root".equals(name)) {
						MLog.d("start");
						bodytype = Integer.parseInt(parser.getAttributeValue(0));
						break;
					}
					if (name.equals("text")) {
						String text = parser.nextText();
						hashMap.put(name, text);
						break;
					}
					if (name.equals("garity")) {
						String garity = parser.nextText();
						hashMap.put(name, garity);
						break;
					}
					if (name.equals("marginleft")) {
						String marginleft = parser.nextText();
						hashMap.put(name, marginleft);
						break;
					}
					if (name.equals("margintop")) {
						String margintop = parser.nextText();
						hashMap.put(name, margintop);
						break;
					}
					break;
				case XmlPullParser.END_TAG:
					MLog.d("END_TAG  eventType  " + eventType);
					if (hashMap != null && hashMap.size() > 0) {
						textViews.add(hashMap);
					}
					hashMap = new HashMap<String, String>();
					break;
				case XmlPullParser.END_DOCUMENT:
					MLog.d("END_DOCUMENT  eventType  " + eventType);
					break;
				}
				eventType = parser.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private File makeXmlFile() {
		// 打印模板文件夹
		String printmodeFile = String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath())
				+ File.separator + "swy/swyxs" + File.separator + "printmode" + File.separator;
		File file = new File(printmodeFile);
		if (!file.exists()) {
			file.mkdirs();// 不存在，创建！
		}

		File fileExtra = new File(printmodeFile + "extra.xml");
		if (fileExtra.exists()) {
			fileExtra.delete();
		}
		MLog.d(fileExtra.getAbsolutePath());
		return fileExtra;
	}

	public void write(int type, List<HashMap<String, String>> listTextView)
			throws IllegalArgumentException, IllegalStateException, IOException {
		XmlSerializer serializer = Xml.newSerializer();
		FileOutputStream os = new FileOutputStream(this.makeXmlFile());
		serializer.setOutput(os, "UTF-8");
		serializer.startDocument("UTF-8", true);
		serializer.startTag(null, "root");
		serializer.attribute(null, "type", type + "");
		for (int v0 = 0; v0 < listTextView.size(); ++v0) {
			HashMap<String, String> map = listTextView.get(v0);
			serializer.startTag(null, "textview");
			serializer.startTag(null, "text");
			serializer.text(map.get("text"));
			serializer.endTag(null, "text");
			serializer.startTag(null, "garity");
			serializer.text(map.get("garity"));
			serializer.endTag(null, "garity");
			serializer.startTag(null, "marginleft");
			serializer.text(map.get("marginleft"));
			serializer.endTag(null, "marginleft");
			serializer.startTag(null, "margintop");
			serializer.text(map.get("margintop"));
			serializer.endTag(null, "margintop");
			serializer.endTag(null, "textview");
		}
		serializer.endTag(null, "root");
		serializer.endDocument();
		os.flush();
		os.close();
	}
}
