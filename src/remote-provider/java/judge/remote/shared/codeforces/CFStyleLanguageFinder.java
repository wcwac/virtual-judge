package judge.remote.shared.codeforces;

import java.util.HashMap;
import java.util.LinkedHashMap;

import judge.remote.language.LanguageFinder;
import judge.remote.shared.LanguageUtil;
import judge.tool.Handler;

import org.springframework.stereotype.Component;

@Component
public abstract class CFStyleLanguageFinder implements LanguageFinder {

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
        return LanguageUtil.getDefaultLanguages("CodeForces");
    }

    @Override
    public HashMap<String, String> getLanguagesAdapter() {
        // TODO Auto-generated method stub
        return null;
    }

}
