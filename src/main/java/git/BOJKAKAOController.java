package git;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpSession;

import org.apache.http.NameValuePair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.JsonNode;

@Controller
public class BOJKAKAOController {
	private JsonNode access_token  = null;
    @GetMapping("/")
    public String home() {
        return "home";
    }
    
    @GetMapping("/messagelink")
    public String messagelink() {
        return "MessageLink";
    }
    	
    @RequestMapping(value = "/oauth", produces = "application/json")
    public String kakaoLogin(@RequestParam("code") String code, Model model, HttpSession session) throws IOException {
        System.out.println("로그인 할 때 임시 코드값");
        System.out.println(code);
        System.out.println("로그인 후 결과값");

        kakao_rest_api kr = new kakao_rest_api();
        JsonNode node = kr.getAccessToken(code);

        System.out.println(node);
        access_token = node.get("access_token");

        session.setAttribute("token", access_token.asText());


        BOJ boj = new BOJ();
        ArrayList<Collection<NameValuePair>> ar = boj.ToJson();

        kr.sendTextMsgToMe(access_token, ar);

        return "logininfo";
    }

}
