package judge.remote.provider.codeforcesgym;

import judge.remote.RemoteOjInfo;
import judge.remote.shared.codeforces.CFStyleSubmitter;
import org.springframework.stereotype.Component;

@Component
public class CodeForcesGymSubmitter extends CFStyleSubmitter {

    @Override
    public RemoteOjInfo getOjInfo() {
        return CodeForcesGymInfo.INFO;
    }
}
