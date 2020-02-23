package judge.remote.provider.aizu;

import judge.httpclient.DedicatedHttpClient;
import judge.httpclient.HttpStatusValidator;
import judge.remote.RemoteOjInfo;
import judge.remote.crawler.RawProblemInfo;
import judge.remote.crawler.SimpleCrawler;
import judge.tool.Tools;

import org.apache.http.HttpHost;
import org.springframework.stereotype.Component;

@Component
public class AizuCrawler extends SimpleCrawler {

    @Override
    public RemoteOjInfo getOjInfo() {
        return AizuInfo.INFO;
    }
    
    @Override
    protected String getProblemUrl(String problemId) {
        return getHost().toURI() + "/onlinejudge/description.jsp?id=" + problemId;
    }

    @Override
    protected void populateProblemInfo(RawProblemInfo info, String problemId, String html) {
        info.title = Tools.regFind(html, "<H1>(?:.+</a> -)?([\\s\\S]*?)</H1>").trim();
        info.description = Tools.regFind(html, "<div class=\"description\">([\\s\\S]*?)<hr>").replaceAll("^[\\s\\S]*</h1>", "");
        info.source = Tools.regFind(html, "style=\"font-size:10pt\">\\s*Source:([\\s\\S]*?)</div>");

        DedicatedHttpClient client = dedicatedHttpClientFactory.build(HttpHost.create("https://judgeapi.u-aizu.ac.jp"), getCharset());
        String jsonURl = "https://judgeapi.u-aizu.ac.jp/problems/ids/" + problemId;
        String json = client.get(jsonURl, HttpStatusValidator.SC_OK).getBody();
        info.timeLimit = 1000 * Integer.parseInt(Tools.regFind(json, "\"problemTimeLimit\":(\\d+)"));
        info.memoryLimit = Integer.parseInt(Tools.regFind(json, "\"problemMemoryLimit\":(\\d+)"));
    }

}
