package judge.remote.provider.sgu;

import judge.remote.RemoteOjInfo;
import judge.remote.shared.codeforces.CFStyleLoginer;
import org.springframework.stereotype.Component;

@Component
public class SGULoginer extends CFStyleLoginer {

    @Override
    public RemoteOjInfo getOjInfo() {
        return SGUInfo.INFO;
    }

}
