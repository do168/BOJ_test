package git;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;

public class kakao_rest_api {
	private String client_id = "d83984dcd9f3fae1ec920f0407d50437";

	public JsonNode getAccessToken(String autorize_code) {
		final String RequestUrl = "https://kauth.kakao.com/oauth/token";
		final Collection<NameValuePair> postParams = new ArrayList<NameValuePair>();
		postParams.add(new BasicNameValuePair("grant_type", "authorization_code"));
		postParams.add(new BasicNameValuePair("client_id", client_id));
		postParams.add(new BasicNameValuePair("redirect_uri", "http://localhost:8080/oauth"));
		postParams.add(new BasicNameValuePair("code", autorize_code));

		final HttpClient client = HttpClientBuilder.create().build();
		final HttpPost post = new HttpPost(RequestUrl);

		JsonNode returnNode = null;

		try {
			post.setEntity(new UrlEncodedFormEntity(postParams));
			final HttpResponse response = client.execute(post);
			ObjectMapper mapper = new ObjectMapper();
			returnNode = mapper.readTree(response.getEntity().getContent());

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}
		return returnNode;
	}
	// https://kauth.kakao.com/oauth/authorize?client_id=1d898da97758eca206989c9aa2654296&redirect_uri=http://localhost:8080/oauth&response_type=code


	// https://kapi.kakao.com/v2/user/me

	public void sendTextMsgToMe(JsonNode access_token, ArrayList<Collection<NameValuePair>> ar) {
		final String RequestUrl = "https://kapi.kakao.com/v2/api/talk/memo/default/send";

		final HttpClient client = HttpClientBuilder.create().build();
		final HttpPost post = new HttpPost(RequestUrl);

		JsonNode returnNode = null;
		post.addHeader("Authorization", "Bearer " + access_token);
		for (Collection<NameValuePair> postParams : ar) {

			try {
				post.setEntity(new UrlEncodedFormEntity(postParams, Charset.forName("utf-8")));
				final HttpResponse response = client.execute(post);
				ObjectMapper mapper = new ObjectMapper();
				returnNode = mapper.readTree(response.getEntity().getContent());

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {

			}
		}
		return;

	}
	/*
	 * curl -v -X POST "https://kapi.kakao.com/v2/api/talk/memo/default/send" \ -H
	 * "Authorization: Bearer xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
	 * \ -d 'template_object={ "object_type": "text", "text":
	 * "https://developers.kakao.com", "mobile_web_url":
	 */

}
