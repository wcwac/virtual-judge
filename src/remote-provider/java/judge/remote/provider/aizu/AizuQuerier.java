package judge.remote.provider.aizu;

import judge.httpclient.DedicatedHttpClient;
import judge.remote.RemoteOjInfo;
import judge.remote.account.RemoteAccount;
import judge.remote.querier.AuthenticatedQuerier;
import judge.remote.status.SubmissionRemoteStatus;
import judge.remote.status.SubstringNormalizer;
import judge.remote.submitter.SubmissionInfo;
import org.apache.struts2.json.JSONUtil;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AizuQuerier extends AuthenticatedQuerier {

    private static final String[] STATUS = {"Compile Error", "Wrong Answer", "Time Limit Exceeded", "Memory Limit Exceeded",
            "Accepted", "Waiting Judge", "Output Limit Exceeded", "Runtime Error", "Presentation Error", "Running"};

    @Override
    public RemoteOjInfo getOjInfo() {
        return AizuInfo.INFO;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected SubmissionRemoteStatus query(SubmissionInfo info, RemoteAccount remoteAccount, DedicatedHttpClient client) throws Exception {
        String html = client.get("https://judgeapi.u-aizu.ac.jp/verdicts/" + info.remoteRunId).getBody();
        Map<String, Object> response = (Map<String, Object>) JSONUtil.deserialize(html);

        SubmissionRemoteStatus status = new SubmissionRemoteStatus();
        if(!response.containsKey("submissionRecord")){
            status.rawStatus = "Waiting Judge";
            status.statusType = SubstringNormalizer.DEFAULT.getStatusType(status.rawStatus);
            return status;
        }

        Map<String, Object> submissionRecord = (Map<String, Object>) response.get("submissionRecord");
        int statusValue = ((Long) submissionRecord.get("status")).intValue();
        status.rawStatus = statusValue == -1 ? "Judge Not Available" : STATUS[statusValue];
        status.statusType = SubstringNormalizer.DEFAULT.getStatusType(status.rawStatus);

        switch (status.statusType) {
            case AC:
                status.executionTime = ((Long) submissionRecord.get("cpuTime")).intValue();
                status.executionMemory = ((Long) submissionRecord.get("memory")).intValue();
                break;
            case CE:
                status.compilationErrorInfo = (String) response.get("compileError");
                break;
            case RE:
                status.compilationErrorInfo = (String) response.get("runtimeError");
            case PE:
            case WA:
            case MLE:
            case OLE:
            case TLE:
                status.failCase = Integer.parseInt(((String) submissionRecord.get("accuracy")).split("/")[0]) + 1;
                status.rawStatus += " on test " + status.failCase;
                break;
        }
        return status;
    }
}
