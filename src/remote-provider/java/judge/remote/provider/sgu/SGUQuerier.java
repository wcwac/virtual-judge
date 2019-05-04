package judge.remote.provider.sgu;

import judge.remote.RemoteOjInfo;
import judge.remote.shared.codeforces.CFStyleQuerier;
import org.springframework.stereotype.Component;

@Component
public class SGUQuerier extends CFStyleQuerier {

    @Override
    public RemoteOjInfo getOjInfo() {
        return SGUInfo.INFO;
    }

}
