package judge.remote.provider.vjudge;

import judge.remote.RemoteOjInfo;
import judge.remote.language.LanguageFinder;
import judge.remote.shared.LanguageUtil;
import judge.tool.Handler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;

@Component
public class VjudgeLanguageFinder implements LanguageFinder {

    @Override
    public RemoteOjInfo getOjInfo() {
        return VjudgeInfo.INFO;
    }

    @Override
    public boolean isDiverse() {
        return true;
    }

    @Override
    public void getLanguages(String remoteProblemId, Handler<LinkedHashMap<String, String>> handler) {
        String oj = remoteProblemId.substring(0, remoteProblemId.indexOf("-")).trim();
        handler.handle(LanguageUtil.getDefaultLanguages(oj));
    }

    @Override
    public LinkedHashMap<String, String> getDefaultLanguages() {
        return null;
    }

    @Override
    public HashMap<String, String> getLanguagesAdapter() {
        // TODO Auto-generated method stub
        return null;
    }

}
