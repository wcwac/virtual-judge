package judge.remote.provider.aizu;

import judge.httpclient.DedicatedHttpClient;
import judge.httpclient.HttpBodyValidator;
import judge.remote.RemoteOjInfo;
import judge.remote.account.RemoteAccount;
import judge.remote.loginer.RetentiveLoginer;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Component
public class AizuLoginer extends RetentiveLoginer {

    @Override
    public RemoteOjInfo getOjInfo() {
        return AizuInfo.INFO;
    }


    @Override
    protected void loginEnforce(RemoteAccount account, DedicatedHttpClient client) {
        String idString = "{\"id\":\"" + account.getAccountId() + "\"";
        if (client.get("https://judgeapi.u-aizu.ac.jp/self").getBody().startsWith(idString)) {
            return;
        }

        Map<String, String> payload = new HashMap<>();
        payload.put("id", account.getAccountId());
        payload.put("password", account.getPassword());
        HttpPost post = new HttpPost("https://judgeapi.u-aizu.ac.jp/session");
        try {
            post.setEntity(new StringEntity(JSONUtil.serialize(payload)));
            post.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            client.execute(post,  new HttpBodyValidator(idString));
        } catch (UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
        }
    }

}
