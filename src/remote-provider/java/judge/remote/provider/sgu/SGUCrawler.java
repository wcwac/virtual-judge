package judge.remote.provider.sgu;

import judge.remote.RemoteOjInfo;
import judge.remote.crawler.RawProblemInfo;
import judge.remote.crawler.SimpleCrawler;
import judge.tool.Tools;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

@Component
public class SGUCrawler extends SimpleCrawler {

    @Override
    public RemoteOjInfo getOjInfo() {
        return SGUInfo.INFO;
    }

    @Override
    protected String getProblemUrl(String problemId) {
        return getHost().toURI() + "/problemsets/acmsguru/problem/99999/" + problemId;
    }

    @Override
    protected void preValidate(String problemId) {
        Validate.isTrue(problemId.matches("[1-9]\\d*"));
    }

    @Override
    protected void populateProblemInfo(RawProblemInfo info, String problemId, String html) {
        info.title = Tools.regFind(html, "<h4>" + problemId + "\\. ([\\s\\S]*?)</h4>").trim();
        if(info.title.isEmpty()){
            info.title = Tools.regFind(html, "<p align=\"CENTER\">\\s*" + problemId + "\\. ([\\s\\S]*?)</p>").trim();
        }
        Double timeLimit = 1000 * Double.parseDouble(Tools.regFind(html, "[Tt]ime limit per test:\\s*([\\d\\.]+)\\s*sec"));
        info.timeLimit = timeLimit.intValue();
        info.memoryLimit = Integer.parseInt(Tools.regFind(html, "[Mm]emory limit(?: per test)?:\\s(\\d+) (kilobytes|KB)\\s*</"));
        info.description = Tools.regFind(html, "output: standard(?: output)?\\s*</div>(?:<br>)+([\\s\\S]*?)(<br>)*</div>(<hr>)?</div></div><script type=\"text/javascript\" src=\"\">");
        if(info.description.isEmpty()){
            info.description = Tools.regFind(html, "<p align=\"JUSTIFY\">([\\s\\S]*?)</td></tr></tbody></table></div></div><script type=\"text/javascript\" src=\"\">");
        }
}
}
