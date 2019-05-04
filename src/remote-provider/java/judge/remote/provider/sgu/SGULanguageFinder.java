package judge.remote.provider.sgu;

import judge.remote.RemoteOjInfo;
import judge.remote.shared.codeforces.CFStyleLanguageFinder;
import org.springframework.stereotype.Component;

@Component
public class SGULanguageFinder extends CFStyleLanguageFinder {

    @Override
    public RemoteOjInfo getOjInfo() {
        return SGUInfo.INFO;
    }
}
