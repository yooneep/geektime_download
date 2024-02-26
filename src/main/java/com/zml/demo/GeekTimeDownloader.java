package com.zml.demo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zml.demo.constant.CommonConstant;
import com.zml.demo.util.FileUtil;
import com.zml.demo.util.Html2MDUtil;
import com.zml.demo.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: zhumlu@yonyou.com
 * @date: 2022/2/28 15:04
 * @description:
 */
public class GeekTimeDownloader {
    public static Logger log = LoggerFactory.getLogger(GeekTimeDownloader.class);
    public static final String cid = "100085301";
    public static final String articleUrl = "https://time.geekbang.org/serv/v1/article";
    public static final String articlesUrl = "https://time.geekbang.org/serv/v1/column/articles";
    public static final String infoUrl = "https://time.geekbang.org/serv/v3/column/info";

    public static final String productList = "https://time.geekbang.org/serv/v4/pvip/product_list";
    public static final String filePath = "/Users/pangning/geek/";
    public static final Integer interval = 1000;
    static Integer v = 1;
    public static List<String> cids = Arrays.asList("100017301", "100020801", "100002201", "100006601", "100007101", "100006701", "100021701", "100039001", "100017001", "100015201", "100026901", "100023201", "100010301", "100020901", "100024701", "100017301", "100020801", "100002201", "100006601", "100007101", "100006701", "100021701", "100039001", "100017001", "100015201", "100026901", "100009601", "100020201", "100026001", "100078401", "100014401", "100014301", "100029001", "100028001", "100003101", "100033601", "100017501", "100037301", "100038501", "100064201", "100022301", "100047701", "100029201", "100021101", "100032301", "100035801", "100002401", "100034101", "100084801", "100003401", "100036601", "100040201", "100007201", "100099801", "100001901", "100051201", "100051801", "100008701", "100041701", "100040501", "100034501", "100002601", "100012101", "100002101", "100060801", "100039701", "100031801", "100035501", "100049101", "100051901", "100024801", "100045801", "100081501", "100541001", "100053801", "100057401", "100044301", "100069901", "100048001", "100052801", "100061801", "100117801", "100114001", "100079101", "100311801", "100043901", "100085501", "100114501", "100050101", "100074001", "100090001", "100037701", "100085101", "100083301", "100046301", "100108401", "100030701", "100310101", "100109401", "100110101", "100050701", "100084301", "100073201", "100059201", "100082001", "100063601", "100077001", "100070901", "100050201", "100109201", "100097301", "100053901", "100119601", "100101301", "100124001", "100120501", "100122101", "100064801", "100101501", "100095401", "100091101", "100048201", "100062001", "100070001", "100073301", "100036401", "100009701", "100012001", "100027701", "100019601", "100094401", "100084801", "100003401", "100036601", "100040201", "100007201", "100099801", "100001901", "100051201", "100051801", "100008701", "100041701", "100040501", "100034501", "100002601", "100012101", "100002101", "100060801", "100039701", "100031801", "100035501", "100049101", "100051901", "100024801", "100045801", "100081501", "100541001", "100053801", "100057401", "100044301", "100069901", "100048001", "100052801", "100061801", "100082101", "100053301", "100063801", "100041101", "100025001", "100046901", "100062901", "100034901", "100058001", "100066601", "100100901", "100105701", "100046101", "100085301", "100090601", "100104301", "100043001", "100055801", "100071001", "100048401", "100058401", "100069501", "100093501", "100104701", "100100701", "100093001", "100028301", "100094901", "100020001", "100060101", "100052601", "100067701", "100068401", "100117801", "100114001", "100079101");

    public static void main(String[] args) {
//        List<String> cids = getCids();
//        System.out.println(JSONObject.toJSONString(cids));
        for (String cid : cids) {
            articles(cid);
        }
//        articles(cid);

    }

    public static List<String> getCids() {
//        Map data = new HashMap();
//        data.put("cid", cid);
//        data.put("order", "earliest");
//        data.put("prev", 0);
//        data.put("sample", false);
//        data.put("size", 500);
        List<String> cids = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String url = CommonConstant.request.replace("9", String.valueOf(i));
            Map data = JSONObject.parseObject(url);
            System.out.println(productList);
            String s = geekRequest(productList, data);
            JSONObject jsonObject = JSONObject.parseObject(s);
            JSONObject data1 = (JSONObject) jsonObject.get("data");
            List<JSONObject> list = JSONObject.parseArray(data1.get("list").toString(), JSONObject.class);
            List<String> productId = list.stream().map(p -> p.get("product_id").toString()).collect(Collectors.toList());
            cids.addAll(productId);
        }
        return cids;
    }


    public static void articles(String cid) {
        //获取专栏标题
        String info = info(Long.parseLong(cid));

        Map data = new HashMap();
        data.put("cid", cid);
        data.put("order", "earliest");
        data.put("prev", 0);
        data.put("sample", false);
        data.put("size", 500);
        String result = geekRequest(articlesUrl, data);
        System.out.println(result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        JSONObject dataJSON = jsonObject.getJSONObject("data");
        JSONArray list = dataJSON.getJSONArray("list");
        //文件目录
        String path = filePath + File.separator + cid + "_" + info;
        String head = getFileName("00_目录");
        if (!FileUtil.isExist(path, head)) {
            StringBuilder sb = new StringBuilder();
            sb.append("# ").append(info).append("\n");
            //制作目录
            list.forEach(obj -> {
                JSONObject jsonObject1 = (JSONObject) obj;
                String id = jsonObject1.getString("id");
                String article_title = jsonObject1.getString("article_title");
                String fileName = getFileName(article_title);
                sb.append("* ").append("[").append(fileName).append("]").append("(").append("./").append(fileName).append(")").append("\n");
            });

            FileUtil.write(sb.toString(), path, head);
            log.info("保存:{}成功!", head);
        } else {
            log.info("{}已存在，跳过!", head);
        }

        Collections.reverse(list);
        //循环生成文章
        list.stream().forEach(obj -> {
            try {
                JSONObject jsonObject1 = (JSONObject) obj;
                String id = jsonObject1.getString("id");
                String article_title = jsonObject1.getString("article_title");

                String audio_download_url = jsonObject1.getString("audio_download_url");
                String audio_title = jsonObject1.getString("audio_title");

                //文件名
                String fileName = getFileName(article_title);
                if (!FileUtil.isExist(path, fileName)) {
                    String fileNamePath = path + File.separator + fileName;
                    String article = article(id);
                    Html2MDUtil.convert(article, fileNamePath, true);
                    log.info("保存:{}成功!", fileName);
                } else {
                    log.info("{}已存在，跳过!", fileName);
                }
            } catch (Exception e) {
                log.error("error ", e);
            }
        });
        log.info("完成!");
    }

    public static String getFileName(String title) {
        //去除关键字
        String fileName = FileUtil.replace(title) + ".md";
        return fileName;
    }

    public static String article(String id) {
        Map data = new HashMap();
        data.put("id", id);
        data.put("include_neighbors", true);
        data.put("is_freelyread", true);
        String result = geekRequest(articleUrl, data);
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (jsonObject == null) {
            v++;
            return article(id);
        }
        JSONObject dataJSON = jsonObject.getJSONObject("data");
        String article_content = dataJSON.getString("article_content");
        return article_content;
    }

    public static String info(Long id) {
        Map data = new HashMap();
        data.put("product_id", id);
        data.put("with_recommend_article", true);
        String result = geekRequest(infoUrl, data);
        System.out.println(result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        JSONObject dataJSON = jsonObject.getJSONObject("data");
        String title = dataJSON.getString("title");
        log.info("获取专栏名称:{}", title);
        return title;
    }

    public static String geekRequest(String url, Map data) {
        Map header = new HashMap<>();
        header.put("Cookie", CommonConstant.cookies);
        header.put("Origin", "https://time.geekbang.org");
        header.put("Host", "time.geekbang.org");
        header.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/" + v + ".0 Safari/605.1.15");
        header.put("Content-Type", "text/plain");
        header.put("Origin", "https://time.geekbang.org");
        String result = HttpUtil.postJson(url, header, data);
        log.info(result + " request:" + data);
        sleep();
        return result;
    }

    public static void sleep() {
        try {
            Random random = new Random();
            //间隔时间1-2s
            Thread.sleep(random.nextInt(1000) + interval);
        } catch (InterruptedException e) {
            log.error("异常:", e);
        }
    }
}
