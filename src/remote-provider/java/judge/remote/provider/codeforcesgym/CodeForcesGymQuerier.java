package judge.remote.provider.codeforcesgym;

import judge.remote.RemoteOjInfo;
import judge.remote.shared.codeforces.CFStyleQuerier;
import org.springframework.stereotype.Component;

@Component
public class CodeForcesGymQuerier extends CFStyleQuerier {
    @Override
    public RemoteOjInfo getOjInfo() {
        return CodeForcesGymInfo.INFO;
    }
    
}
