package judge.remote.provider.aizu;

import judge.httpclient.DedicatedHttpClient;
import judge.httpclient.HttpBodyValidator;
import judge.remote.RemoteOjInfo;
import judge.remote.account.RemoteAccount;
import judge.remote.submitter.CanonicalSubmitter;
import judge.remote.submitter.SubmissionInfo;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AizuSubmitter extends CanonicalSubmitter {

    @Override
    public RemoteOjInfo getOjInfo() {
        return AizuInfo.INFO;
    }


    @Override
    protected boolean needLogin() {
        return true;
    }

    @Override
    protected Integer getMaxRunId(SubmissionInfo info, DedicatedHttpClient client, boolean submitted) {
        String html = client.get("https://judgeapi.u-aizu.ac.jp/submission_records/recent").getBody();
        Matcher matcher = Pattern.compile("\"judgeId\":(\\d+),[^}]*?\"userId\":\"" + info.remoteAccountId + "\",\"problemId\":\"" + info.remoteProblemId + "\",").matcher(html);
        return matcher.find() ? Integer.parseInt(matcher.group(1)) : -1;
    }

    @Override
    protected String submitCode(SubmissionInfo info, RemoteAccount remoteAccount, DedicatedHttpClient client) {
        Map<String, String> payload = new HashMap<>();
        payload.put("problemId", info.remoteProblemId);
        payload.put("language", info.remotelanguage);
        payload.put("sourceCode", info.sourceCode);
        HttpPost post = new HttpPost("https://judgeapi.u-aizu.ac.jp/submissions");
        try {
            post.setEntity(new StringEntity(JSONUtil.serialize(payload)));
            post.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            client.execute(post,  new HttpBodyValidator("{\"token\": \""));
        } catch (UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
