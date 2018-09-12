package cn.tyrion.pagemonitor.service;

import cn.tyrion.pagemonitor.domain.ApiAccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Formatter;

@Service
public class WechatService {

    @Value("${wechat.api.access.token.url}")
    private String getAccessTokenUrl;

    @Value("${wechat.app.id}")
    private String appId;

    @Value("${wechat.app.secret}")
    private String appSecret;

    public String getGlobalAccessToken() {
        UriComponentsBuilder urlBuilder = UriComponentsBuilder
                .fromHttpUrl(getAccessTokenUrl)
                .queryParam("grant_type", "client_credential")
                .queryParam("appid", appId)
                .queryParam("secret", appSecret);
        String URL = urlBuilder.build().toUriString();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ApiAccessToken> apiAccessTokenEntity = restTemplate.getForEntity(URL, ApiAccessToken.class);

        if (apiAccessTokenEntity.getStatusCode() != HttpStatus.OK) {
            return null;
        }
        System.out.println("access_token: "+apiAccessTokenEntity.getBody().getAccessToken());
        return apiAccessTokenEntity.getBody().getAccessToken();
    }

    public boolean validate(String signature, String timestamp, String nonce, String publicAccountToken) {
        String sortStr = this.sort(publicAccountToken, timestamp, nonce);
        String myToken = this.sha1(sortStr);
        return myToken.equals(signature);
    }

    private String sort(String token, String timestamp, String nonce) {
        String[] strArray = {token, timestamp, nonce};
        Arrays.sort(strArray);
        StringBuilder sbuilder = new StringBuilder();
        for (String str : strArray) {
            sbuilder.append(str);
        }
        return sbuilder.toString();
    }

    private String sha1(String str) {
        if (str == null || str.length() == 0)
            return "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] bytes = md.digest(str.getBytes());
            return byteToHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}
