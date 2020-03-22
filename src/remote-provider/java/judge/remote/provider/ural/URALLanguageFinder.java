package judge.remote.provider.ural;

import java.util.HashMap;
import java.util.LinkedHashMap;

import judge.remote.RemoteOjInfo;
import judge.remote.language.LanguageFinder;
import judge.remote.shared.LanguageUtil;
import judge.tool.Handler;

import org.springframework.stereotype.Component;

@Component
public class URALLanguageFinder implements LanguageFinder {

    @Override
    public RemoteOjInfo getOjInfo() {
        return URALInfo.INFO;
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
        HashMap<String, String> adapter = new HashMap<>();
        adapter.put("3", "31");
        adapter.put("12", "32");
        adapter.put("16", "34");
        adapter.put("17", "35");
        adapter.put("20", "25");
        adapter.put("21", "26");
        adapter.put("22", "27");
        adapter.put("23", "28");
        return adapter;
    }

}
