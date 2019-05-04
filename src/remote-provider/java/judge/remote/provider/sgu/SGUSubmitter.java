package judge.remote.provider.sgu;

import judge.httpclient.DedicatedHttpClient;
import judge.httpclient.SimpleHttpResponse;
import judge.httpclient.SimpleNameValueEntityFactory;
import judge.remote.RemoteOjInfo;
import judge.remote.account.RemoteAccount;
import judge.remote.shared.codeforces.CFStyleSubmitter;
import judge.remote.shared.codeforces.CodeForcesTokenUtil;
import judge.remote.submitter.SubmissionInfo;
import org.apache.http.HttpEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SGUSubmitter extends CFStyleSubmitter {

    @Override
    public RemoteOjInfo getOjInfo() {
        return SGUInfo.INFO;
    }

    @Override
    protected String[] getProblemInfo(String remoteProblemId) {
        return new String[]{"acmsguru", remoteProblemId};
    }

    @Override
    protected boolean isSameProblem(String[] problemInfo, Map<String, Object> problem) {
        return problemInfo[0].equals(problem.get("problemsetName")) &&
                problemInfo[1].equals(problem.get("index"));
    }

    @Override
    protected String submitCode(SubmissionInfo info, RemoteAccount remoteAccount, DedicatedHttpClient client) {
        CodeForcesTokenUtil.CodeForcesToken token = CodeForcesTokenUtil.getTokens(client);

        HttpEntity entity = SimpleNameValueEntityFactory.create( //
                "csrf_token", token.csrf_token, //
                "_tta", token._tta, //
                "action", "submitSolutionFormSubmitted", //
                "contestId", "99999", //
                "submittedProblemIndex", info.remoteProblemId, //
                "programTypeId", info.remotelanguage, //
                "source", info.sourceCode + getRandomBlankString(), //
                "sourceFile", "", //
                "sourceCodeConfirmed", "true", //
                "doNotShowWarningAgain", "on" //
        );

        SimpleHttpResponse response = client.post(
                "/problemsets/acmsguru/submit?csrf_token=" + token.csrf_token,
                entity
        );

        return checkSubmitResult(response);
    }

}
