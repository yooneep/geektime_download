package com.geekdoc.download;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.geekdoc.download.constant.CommonConstant;
import com.geekdoc.download.util.FileUtil;
import com.geekdoc.download.util.Html2MDUtil;
import com.geekdoc.download.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.geekdoc.download.constant.CommonConstant.*;

/**
 * @author: zhumlu@yonyou.com
 * @date: 2022/2/28 15:04
 * @description:
 */
@Slf4j
public class GeekTimeDownloader {
    private static final Integer interval = 500;
    private static Integer v = 0;
    private static List<String> keywords = Arrays.asList("kafka", "redis", "mysql", "中间件", "管理", "性能", "优化", "消息");

    private static List<String> cidList = Arrays.asList("100758001", "100799401", "100786301", "100785001", "100781001", "100039601", "100017301", "100020801", "100002201", "100006601", "100007101", "100006701", "100021701", "100039001", "100017001", "100015201", "100026901", "100023201", "100010301", "100020901", "100024701", "100025201", "100021201", "100006201", "100056701", "100013101", "100017301", "100020801", "100002201", "100006601", "100007101", "100006701", "100021701", "100039001", "100017001", "100015201", "100026901", "100023201", "100010301", "100020901", "100024701", "100025201", "100021201", "100006201", "100056701", "100013101", "100029501", "100023901", "100009601", "100020201", "100026001", "100078401", "100014401", "100014301", "100029001", "100028001", "100003101", "100033601", "100017501", "100037301", "100038501", "100064201", "100022301", "100047701", "100029201", "100021101", "100032301", "100035801", "100002401", "100034101", "100042501", "100023701", "100032701", "100023401", "100046801", "100005101", "100064501", "100024501", "100006501", "100036501", "100031001", "100025301", "100057601", "100079601", "100005701", "100046201", "100036401", "100009701", "100012001", "100027701", "100019601", "100094401", "100084801", "100003401", "100036601", "100040201", "100007201", "100099801", "100001901", "100051201", "100051801", "100008701", "100041701", "100040501", "100034501", "100002601", "100012101", "100002101", "100060801", "100039701", "100031801", "100035501", "100049101", "100051901", "100024801", "100045801", "100541001", "100081501", "100053801", "100057401", "100044301", "100069901", "100048001", "100052801", "100061801", "100082101", "100053301", "100063801", "100041101", "100025001", "100046901", "100062901", "100034901", "100058001", "100066601", "100100901", "100105701", "100046101", "100085301", "100104301", "100090601", "100043001", "100055801", "100071001", "100048401", "100058401", "100069501", "100093501", "100104701", "100100701", "100093001", "100028301", "100094901", "100020001", "100060101", "100052601", "100067701", "100068401", "100117801", "100114001", "100079101", "100311801", "100043901", "100085501", "100114501", "100050101", "100074001", "100090001", "100037701", "100085101", "100083301", "100046301", "100108401", "100030701", "100310101", "100109401", "100110101", "100050701", "100084301", "100073201", "100059201", "100082001", "100063601", "100077001", "100070901", "100109201", "100050201", "100097301", "100053901", "100119601", "100101301", "100124001", "100120501", "100122101", "100064801", "100101501", "100095401", "100091101", "100048201", "100062001", "100070001", "100073301", "100091501", "100117601", "100119701", "100076501", "100309001", "100069101", "100052201", "100062401", "100059901", "100079901", "100122701", "100104501", "100093301", "100079201", "100065501", "100060501", "100102001", "100056401", "100522501", "100070801", "100073401", "100550701", "100031101", "100061401", "100113201", "100075401", "100117701", "100085201", "100098901", "100617601", "100119501", "100618301", "100123701", "100084201", "100115201", "100072001", "100311101", "100103401", "100072201", "100057701", "100056201", "100082501", "100551601", "100109601", "100613101", "100312001", "100555001", "100637501", "100536701", "100112401", "100111101", "100078501", "100098801", "100552001", "100121701", "100310901", "100104601", "100626901", "100107801", "100312101", "100117501", "100523801", "100102601", "100546501", "100080901", "100112001", "100542801", "100524201", "100613601", "100548101", "100525001", "100081901", "100547601", "100636608", "100636401", "100636604", "100671401", "100636606", "100636601", "100636610", "100636612", "100636602", "100636605", "100636611", "100636607", "100636609", "100636603", "100772701", "100780501", "100770601", "100764201", "100761401", "100759401", "100758001", "100755801", "100755401");

    public static void main(String[] args) throws InterruptedException {
        cidList.stream()
                .sorted()
                .filter(p -> p.compareTo("100759401") > 0)
                .forEach(cid ->
//                        ThreadUtil.execute(() ->
                        getColumn(cid));
        Thread.sleep(10000000000L);
    }


    private static Map<String, String> downloadChapter(String cid) {
        Map<String, Object> data = new HashMap<>();
        data.put("cid", cid);
        String s = geekRequest(chapters, data);
        JSONObject jsonObject = JSONObject.parseObject(s);
        JSONArray chapterList = jsonObject.getJSONArray("data");
        return chapterList.stream()
                .map(obj -> (JSONObject) obj)
                .collect(Collectors.toMap(
                        obj -> obj.getString("id"),
                        obj -> obj.getString("title")
                ));
    }

    // 获取课程 ID


    // 按专栏数量倒序查看
    private static Map<String, Object> createRequestData(String cid) {
        Map<String, Object> data = new HashMap<>();
        data.put("cid", cid);
        data.put("order", "earliest");
        data.put("prev", 0);
        data.put("sample", false);
        data.put("size", 1000);
        return data;
    }

    // 下载指定专栏【NEW】
    private static void getColumn(String cid) {
        // 获取专栏信息
        String result = geekRequest(articlesUrl, createRequestData(cid));
        JSONArray list = JSONObject.parseObject(result).getJSONObject("data").getJSONArray("list");
        //获取专栏标题
        String info = getColumnInfo(Long.parseLong(cid));
        info = FileUtil.replace(info);

        //文件目录
        String mdPath = filePath + "md" + File.separator + cid + "_" + info + "-[" + list.size() + "]";
        String mp3Path = filePath + "mp3" + File.separator + cid + "_" + info + "-[" + list.size() + "]";

        Map<String, String> idTitleMap = downloadChapter(cid);

        String head = FileUtil.replace("000__目录") + ".md";
        boolean needmp3 = keywords.stream().anyMatch(info.toLowerCase()::contains);

        if (FileUtil.isExist(mdPath, head) && new File(mdPath).listFiles().length == list.size() + 1 && !needmp3) {
            log.info(info + " 文档齐全，跳过内容");
            return;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("# ").append(info).append("\n");
            //制作目录
            IntStream.range(0, list.size())
                    .mapToObj(i -> new AbstractMap.SimpleEntry<>(i, (JSONObject) list.get(i)))
                    .forEach(entry -> {
                        JSONObject jsonObject1 = entry.getValue();
                        String id = jsonObject1.getString("id");
                        String article_title = jsonObject1.getString("article_title");

                        String fileName = article_title.replaceAll("^\\d*\\s*\\|\\s*", "");
                        fileName = getPrefix(entry.getKey()) + "_" + getFileName(fileName);
                        sb.append("* ");
                        if (MapUtils.isNotEmpty(idTitleMap)) {
                            String chapterId = jsonObject1.getString("chapter_id");
                            String chapterTitle = idTitleMap.get(chapterId);
                            chapterTitle = FileUtil.replace(chapterTitle);
                            if (StringUtils.isNotBlank(chapterTitle)) {
                                sb.append(chapterTitle).append("-");
                            }
                        }
                        sb.append("[").append(fileName).append("]").append("(").append("./").append(fileName).append(")").append("\n");
                    });
            FileUtil.write(sb.toString(), mdPath, head);
            log.info("保存:{}成功!", head);
        }
        if (list.size() > 100) {
            log.error("======================= list.size()>200 =======================" + info);
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            saveArticle((JSONObject) list.get(i), mdPath, mp3Path, i, needmp3, idTitleMap);
        }
        log.info("完成!");
    }

    private static void saveArticle(JSONObject obj, String mdPath, String mp3Path, int i, boolean needmp3, Map<String, String> idTitleMap) {
        try {
            String id = obj.getString("id");
            String article_title = obj.getString("article_title");

            article_title = removeDigitsFromFirstFourChars(article_title);
            String prefix = getPrefix(i);
            String fileName = prefix + "_" + getFileName(article_title);
            if (MapUtils.isNotEmpty(idTitleMap)) {
                String chapterId = obj.getString("chapter_id");
                String chapterTitle = idTitleMap.get(chapterId);
                if (StringUtils.isNotBlank(chapterTitle)) {
                    chapterTitle = FileUtil.replace(chapterTitle);
                    fileName = prefix + "_" + chapterTitle + "-" + getFileName(article_title);
                }
            }

            String audio_download_url = obj.getString("audio_download_url");
            String audio_title = obj.getString("audio_title");
            String mp3Name = prefix + "_" + getMp3Name(article_title);

            if (!FileUtil.isExist(mdPath, fileName)) {
                String finalFileName = fileName;
                Html2MDUtil.convert(getArticle(id), mdPath + File.separator + finalFileName, true);
            }
            if (!FileUtil.isExist(mp3Path, mp3Name) && StringUtils.isNotBlank(audio_download_url) && needmp3) {
                HttpUtil.downloadFile(audio_download_url, mp3Path + File.separator + mp3Name);
            }
        } catch (Exception e) {
            log.error("error ", e);
        }
    }

    private static String getPrefix(int i) {
        return String.format("%03d", i);
    }

    private static String removeDigitsFromFirstFourChars(String input) {
        if (input == null || input.length() <= 4) {
            // 如果字符串为空或长度小于等于4，直接返回处理后的字符串
            return input == null ? "" : input.replaceAll("[\\d_ ]", "");
        }

        // 提取前4位和剩余部分
        String firstFourChars = input.substring(0, 6);
        String remainingChars = input.substring(6);

        // 使用正则表达式替换前4位中的数字、下划线和空格
        String modifiedFirstFourChars = firstFourChars.replaceAll("[\\_ 丨]", "");

        // 组合修改后的前4位和剩余部分
        return modifiedFirstFourChars + remainingChars;
    }

    private static String getFileName(String title) {
        return Optional.ofNullable(title)
                .map(FileUtil::replace)
                .map(name -> name + ".md")
                .orElse(".md");
    }

    private static String getMp3Name(String title) {
        return Optional.ofNullable(title)
                .map(FileUtil::replace)
                .map(name -> name + ".mp3")
                .orElse(".mp3");
    }

    private static String getArticle(String id) {
        Map data = new HashMap();
        data.put("id", id);
        data.put("include_neighbors", true);
        data.put("is_freelyread", true);
        String result = geekRequest(articleUrl, data);
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (jsonObject == null || jsonObject.getJSONObject("data") == null || jsonObject.getJSONObject("data").getString("article_content") == null) {
            v++;
            return getArticle(id);
        }
        JSONObject dataJSON = jsonObject.getJSONObject("data");
        String article_content = dataJSON.getString("article_content");
        return article_content;
    }

    private static String getColumnInfo(Long id) {
        Map data = new HashMap();
        data.put("product_id", id);
        data.put("with_recommend_article", true);
        String result = geekRequest(infoUrl, data);
        JSONObject jsonObject = JSONObject.parseObject(result);
        JSONObject dataJSON = jsonObject.getJSONObject("data");
        String title = dataJSON.getString("title");
        JSONObject author = dataJSON.getJSONObject("author");
        String name = author.getString("name");
        title = title + "_" + name;
        log.info("获取专栏名称:{}", title);
        return title;
    }

    private static String geekRequest(String url, Map data) {
        Map header = new HashMap<>();
        header.put("Cookie", CommonConstant.cookies);
        header.put("Origin", "https://time.geekbang.org");
        header.put("Host", "time.geekbang.org");
        header.put("User-Agent", userAgent + v);
        header.put("Content-Type", "text/plain");
        header.put("Origin", "https://time.geekbang.org");
        String result = HttpUtil.postJson(url, header, data);
        log.info("geekRequest request:" + data + " response:" + result);
        try {
            Random random = new Random();
            Thread.sleep(random.nextInt(800) + interval);
        } catch (InterruptedException e) {
            log.error("异常:", e);
        }
        return result;
    }

    private static List<String> getCids() {
        return IntStream.range(0, 30)
                .mapToObj(i -> CommonConstant.request.replace("9", String.valueOf(i)))
                .map(url -> {
                    Map<String, Object> data = JSONObject.parseObject(url, Map.class);
                    String response = geekRequest(productList, data);
                    JSONObject jsonObject = JSONObject.parseObject(response);
                    return jsonObject.getJSONObject("data");
                })
                .filter(Objects::nonNull)
                .map(dataObject -> dataObject.getJSONArray("list"))
                .filter(Objects::nonNull)
                .flatMap(list -> list.toJavaList(JSONObject.class).stream())
                .map(p -> p.getString("product_id"))
                .peek(productId -> System.out.println("productId: " + productId))
                .collect(Collectors.toList());
    }
}