package com.tzh.lndexes;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.tzh.entity.Page;
import com.tzh.entity.User;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/lndexes")
public class Lndexes {

	public static Map<String, Object> selectQuery(String value, Page<User> page) throws Exception {
		Map<String,Object> map = new HashMap<>();
		if (null == value || "".equals(value)) {
			map.put("error", "请将输入框补充完整");
		} else {
			value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
			List<User> userList = new ArrayList<>();
			try {
				Directory indexDir = FSDirectory.open(Paths.get("D:/lucence"));
				DirectoryReader directoryReader = DirectoryReader.open(indexDir);
				IndexSearcher searcher = new IndexSearcher(directoryReader);
				IKAnalyzer analyzer = new IKAnalyzer();

				QueryParser parser = new QueryParser("fileContent", analyzer);
				Query query = parser.parse(value);

				QueryParser parser1 = new QueryParser("fileName", analyzer);
				Query query1 = parser1.parse(value);

				BooleanQuery.Builder builder = new BooleanQuery.Builder();
				// 1．MUST和MUST：取得连个查询子句的交集。
				// 2．MUST和MUST_NOT：表示查询结果中不能包含MUST_NOT所对应得查询子句的检索结果。
				// 3．SHOULD与MUST_NOT：连用时，功能同MUST和MUST_NOT。
				// 4．SHOULD与MUST连用时，结果为MUST子句的检索结果,但是SHOULD可影响排序。
				// 5．SHOULD与SHOULD：表示“或”关系，最终检索结果为所有检索子句的并集。
				// 6．MUST_NOT和MUST_NOT：无意义，检索无结果。
				builder.add(query, BooleanClause.Occur.SHOULD);
				builder.add(query1, BooleanClause.Occur.SHOULD);
				BooleanQuery booleanQuery = builder.build();

				ScoreDoc score;
				if(page.getPage()==1){
					score = null;
				} else {
					int num = page.getPageSize()*(page.getPage()-1);
					TopDocs top = searcher.search(booleanQuery, num);
					score = top.scoreDocs[num-1];
				}

				// 高亮显示
				SimpleHTMLFormatter simpleHtmlFormatter = new SimpleHTMLFormatter("<font style='color:red'>",
						"</font>");// 设定高亮显示的格式，也就是对高亮显示的词组加上前缀后缀
				Highlighter highlighter = new Highlighter(simpleHtmlFormatter, new QueryScorer(query));
				highlighter.setTextFragmenter(new SimpleFragmenter(50));// 设置每次返回的字符数.想必大家在使用搜索引擎的时候也没有一并把全部数据展示出来吧，当然这里也是设定只展示部分数据
				Highlighter highlighter1 = new Highlighter(simpleHtmlFormatter, new QueryScorer(query1));
				highlighter1.setTextFragmenter(new SimpleFragmenter(100));// 设置每次返回的字符数.想必大家在使用搜索引擎的时候也没有一并把全部数据展示出来吧，当然这里也是设定只展示部分数据
				TopDocs topDocs = searcher.searchAfter(score, booleanQuery, page.getPageSize());// 这里的page...像hibernate，是在这一页上查12条
				page.setPageSizeCount(Integer.valueOf(String.valueOf(topDocs.totalHits)));
				int docsNum = Integer.valueOf(String.valueOf(topDocs.totalHits));
				if (0 == docsNum) {
					map.put("error", "没有该资源,我们正努力备货中,请另输你要查的资源相关信息");
				} else {
					ScoreDoc[] docs = topDocs.scoreDocs;
					int j = 100;
					for (ScoreDoc scoreDoc : docs) {
						User user = new User();
						Document doc = searcher.doc(scoreDoc.doc);
						String fileName = doc.get("fileName");
						String fileUrl = doc.get("fileUrl");
						String fileSize = doc.get("fileSize");
						Long size = Long.parseLong(fileSize);
						if (size < 1024) {
							size = (long)1;
						} else if (size == 0) {
							size = (long)0;
						} else {
							size = size / 1024;
						}
						fileSize = size.toString() + "kb";
						String fileContent = doc.get("fileContent");

						String str = highlighter.getBestFragment(analyzer, value, fileName);

						if (null == str) {
							str = fileName;
						}

						String str2 = highlighter1.getBestFragment(analyzer, value, fileContent);
						if(null == str2){
							str2 = fileContent;
							if(fileContent.length() < 100){
								if (null == str2) {
									str2 = fileContent;
								}
							} else {
								str2 = fileContent.substring(0, 100);
							}
						}
						user.setFileName(str);
						user.setFileContent(str2);
						user.setFileSize(fileSize);
						user.setFileUrl(fileUrl);
						userList.add(user);
					}
					page.setPt(userList);
				    map.put("page", page);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;

	}
	@RequestMapping(value = "/loadFile")
	public ResponseEntity<byte[]> download(@RequestParam(value = "loadFilePath", required = false) String loadFilePath)
			throws IOException {
		loadFilePath = new String(loadFilePath.getBytes("ISO-8859-1"), "UTF-8");
		File file = new File(loadFilePath.trim());
		String fileName = file.getName();
		HttpHeaders headers = new HttpHeaders();
		String download = new String(fileName.getBytes("ISO-8859-1"), "UTF-8");// 为了解决中文名称乱码问题
		headers.setContentDispositionFormData("attachment", download);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
	}




	/**
     * 分页查询
     * String
     * 2018年6月15日下午5:09:15
     */
    @ResponseBody
    @RequestMapping(value="/query", method=RequestMethod.GET, produces = "text/json;charset=UTF-8")
    public String testList(String value, Page<User> page,Map<String,Object> mso) throws Exception {
    	if("".equals(page.getPage())){
            page.setPage(1);
        }
        if("".equals(page.getPageSize())){
            page.setPageSize(10);
        }
    	Map<String, Object> map = Lndexes.selectQuery(value, page);
    	JSONObject json = JSONObject.fromObject(map);
    	String test = json.toString();
    	String test1 = null;
        try{
            test1 = new String(test.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (null == test1) {
        	test1 = "1";
        }
    	return test1;
    }

    @RequestMapping(value="/queryValue", method=RequestMethod.GET, produces = "text/json;charset=UTF-8")
    public String value(String value, Map<String,Object> mso){
    	try {
			value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
			mso.put("value", value);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	return "display";
    }

}
