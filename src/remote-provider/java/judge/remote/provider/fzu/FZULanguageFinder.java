package judge.remote.provider.fzu;

import java.util.HashMap;
import java.util.LinkedHashMap;

import judge.remote.RemoteOjInfo;
import judge.remote.language.LanguageFinder;
import judge.remote.shared.LanguageUtil;
import judge.tool.Handler;

import org.springframework.stereotype.Component;

@Component
public class FZULanguageFinder implements LanguageFinder {

    @Override
    public RemoteOjInfo getOjInfo() {
        return FZUInfo.INFO;
    }

    @Override
    public boolean isDiverse() {
        return false;
    }

    @Override
    public void getLanguages(String remoteProblemId, Handler<LinkedHashMap<String, String>> handler) {
        // TODO Auto-generated method stub
    }

    @Override
    public LinkedHashMap<String, String> getDefaultLanguages() {
        // move to config file WEB-INF/oj_languages.json
        return LanguageUtil.getDefaultLanguages(getOjInfo().literal);
    }

    @Override
    public HashMap<String, String> getLanguagesAdapter() {
        // TODO Auto-generated method stub
        return null;
    }

}
