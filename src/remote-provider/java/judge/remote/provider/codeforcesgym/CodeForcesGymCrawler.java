package judge.remote.provider.codeforcesgym;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.Validate;
import org.apache.http.HttpHost;
import org.springframework.stereotype.Component;

import judge.executor.ExecutorTaskType;
import judge.executor.Task;
import judge.httpclient.DedicatedHttpClient;
import judge.httpclient.HttpStatusValidator;
import judge.remote.RemoteOjInfo;
import judge.remote.crawler.RawProblemInfo;
import judge.remote.shared.FileDownloader;
import judge.remote.shared.codeforces.CFStyleCrawler;

@Component
public class CodeForcesGymCrawler extends CFStyleCrawler {

	@Override
	public RemoteOjInfo getOjInfo() {
		return CodeForcesGymInfo.INFO;
	}

	@Override
	protected String getProblemUrl(String problemId) {
		String contestNum = problemId.replaceAll("\\D.*", "");
		String problemNum = problemId.replaceAll("^\\d*", "");
		return getHost().toURI() + "/gym/" + contestNum + "/problem/" + problemNum;
	}

	@Override
	protected void preValidate(String problemId) {
		Validate.isTrue(problemId.matches("\\d{6}[A-Z].*"));
		Validate.isTrue(problemId.toUpperCase().equals(problemId));
	}

	@Override
	protected void populateProblemInfo(RawProblemInfo info, String problemId, String html) throws Exception {


		try {
		    //try to craw html description as same as Codeforeces
	        super.populateProblemInfo(info, problemId, html);
		} catch (Exception e) {
		    //Not have html description,try to craw from contest problem list and download PDF
		    final String problemNum = problemId.replaceAll("^\\d*", "");
			final String contestNum = problemId.replaceAll("\\D.*", "");
			final HttpHost host = new HttpHost("codeforces.com");
			final DedicatedHttpClient client = dedicatedHttpClientFactory.build(host);
			final String contestUrl = host.toURI() + "/gym/" + contestNum;

			Task<String> taskDescription = new Task<String>(ExecutorTaskType.GENERAL) {
				@Override
				public String call() throws Exception {
					String html = client.get(contestUrl, HttpStatusValidator.SC_OK).getBody();
					return html;
				}
			};

			taskDescription.submit();
			String htmlDescription = taskDescription.get();

			String regex = "<a href=\"/gym/" + contestNum + "/problem/" + problemNum
					+ "\"><!--\\s*-->([^<]+)(?:(?:.|\\s)*?<div){2}[^>]*>\\s*([^<]+)</div>\\s*([\\d.]+)\\D*(\\d+)";

			Matcher matcher = Pattern.compile(regex).matcher(htmlDescription);
			matcher.find();

			info.title = matcher.group(1);
			info.timeLimit = (int) (Double.parseDouble(matcher.group(3)) * 1000);
			info.memoryLimit = Integer.parseInt(matcher.group(4)) * 1024;
			info.source = "";
			info.url = contestUrl;

			String io = matcher.group(2);
			String downloadURL = parseDownloadURL(html, contestNum);
			if (downloadURL == null) {
				// can't find downloadURL, perhaps the problemUrl is the downloadURL
				// for example http://codeforces.com/gym/102055/problem/A is a PDF file
				downloadURL = getProblemUrl(problemId);
			}
			String uri;
			try {
				String path = this.getClass().getResource("/").getPath().
						replace("WEB-INF/classes/", "ojFiles/cfgym" +
								downloadURL.substring(downloadURL.indexOf("/", "https://".length()), downloadURL.lastIndexOf("/")));
				FileDownloader.downLoadFromUrl(downloadURL, path);
				uri = "../ojFiles/cfgym" + downloadURL.substring(downloadURL.indexOf("/", "https://".length()));
			} catch (Exception e1) {
				uri = downloadURL;
			}
			info.description = "<strong>Input/Output:<br>" + io + "</strong>" + "<br>" +
					"<p><a href=\"" + uri + "\">Click here to download the description file.</a></p><hr>" +
					"<p><embed width=\"100%\" height=\"700\" src=\"" + uri + "\"> </embed></p>";
			info.input = "";
			info.output = "";
			info.sampleInput = "";
			info.sampleOutput = "";
		}

	}

	/**
	 *  parse description file download URL from html
	 *  first try to get the English version
	 *  then a file of any type under‘gym/contestNum/attachments/download/’
	 *  last a PDF file in <a>
	 */
	private String parseDownloadURL(String html, String contestNum) {
		/*
		<td>
            English
        </td>
        <td>
                <a href="/gym/100032/attachments/download/41/icl-cup-2012-en.pdf"
		 */
		String[] regexes = {
				"<td>\\s*English\\s*</td>\\s*<td>\\s*<a href=\"(\\S*?/gym/" + contestNum + "/attachments/download/\\S*?)\"",
				"<a href=\"(\\S*?/gym/" + contestNum + "/attachments/download/\\S*?)\"",
				"<a href=\"(\\S*?.pdf)\""};
		for (String regex : regexes) {
			Matcher matcher = Pattern.compile(regex).matcher(html);
			if(matcher.find()){
				return matcher.group(1);
			}
		}
		return null;
	}

}
