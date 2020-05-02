package git;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Hello world!
 *
 */

public class BOJ {
	final static String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Safari/537.36";
	static ArrayList<String> data;
	static String user_id = "do168";
	public ArrayList<Collection<NameValuePair>> ToJson() throws IOException {

	String url = "https://www.acmicpc.net/status?user_id="+user_id;


	data = new ArrayList<String>();
	Document Crawling_page = Connect(url);
//
	Elements Status = Crawling_page.select("table[id=\"status-table\"] > tbody > tr");
	String pnum = "";
	ArrayList<Collection<NameValuePair>> ar = new ArrayList<Collection<NameValuePair>>();
	for (Element record : Status) {
			if (record.select(".result").text().equals("맞았습니다!!")) {
				System.out.println(record.select("td a").text());
			}
			pnum = record.select("td a").text().split(" ")[1];
			boolean send = true;
			for (String s : data) {
				if (s.equals(pnum)) {
					send = false;
					break;
				}
			}
			
			if (send) {
				String text = pnum;
		        JSONObject template_object = new JSONObject();
		        Collection<NameValuePair> postParams = new ArrayList<NameValuePair>();
		        template_object.put("object_type","text");
		        template_object.put("text", user_id);
		        JSONObject link = new JSONObject();
		        link.put("web_url", "https://www.acmicpc.net/status?problem_id="+text+"&user_id="+user_id+"&language_id=-1&result_id=-1");
		        link.put("mobile_web_url","https://www.acmicpc.net/status?problem_id="+text+"&user_id="+user_id+"&language_id=-1&result_id=-1");
		        template_object.put("link",link);
		        template_object.put("button_title", text);
		        postParams.add(new BasicNameValuePair("template_object",template_object.toString()));
		        ar.add(postParams);
				data.add(text);
			}
		}
	return ar;
	}
	


	static Document Connect(String url) throws IOException {
		Document homePageDocument = Jsoup.connect(url).userAgent(userAgent).timeout(3000)
				.header("Origin", "https://www.acmicpc.net/")
				.header("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
				.header("Content-Type", "application/x-www-form-urlencoded").header("Sec-Fetch-Dest", "document")
				.header("Sec-Fetch-Mode", "navigate").header("Sec-Fetch-Site", "same-origin")
				.header("Upgrade-Insecure-Requests", "1").header("Sec-Fetch-User", "?1").get();
		return homePageDocument;
	}
}

