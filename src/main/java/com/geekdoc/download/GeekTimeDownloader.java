package com.geekdoc.download;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.geekdoc.download.constant.CommonConstant;
import com.geekdoc.download.util.FileUtil;
import com.geekdoc.download.util.Html2MDUtil;
import com.geekdoc.download.util.HttpUtil;
import com.geekdoc.download.util.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
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
    public static final String cid = "100056701";
    public static final Integer interval = 500;
    static Integer v = 0;
    public static List<String> cids1 = Arrays.asList("100017301", "100020801", "100002201", "100006601", "100007101", "100006701", "100021701", "100039001", "100017001", "100015201", "100026901", "100023201", "100010301", "100020901", "100024701", "100017301", "100020801", "100002201", "100006601", "100007101", "100006701", "100021701", "100039001", "100017001", "100015201", "100026901", "100009601", "100020201", "100026001", "100078401", "100014401", "100014301", "100029001", "100028001", "100003101", "100033601", "100017501", "100037301", "100038501", "100064201", "100022301", "100047701", "100029201", "100021101", "100032301", "100035801", "100002401", "100034101", "100084801", "100003401", "100036601", "100040201", "100007201", "100099801", "100001901", "100051201", "100051801", "100008701", "100041701", "100040501", "100034501", "100002601", "100012101", "100002101", "100060801", "100039701", "100031801", "100035501", "100049101", "100051901", "100024801", "100045801", "100081501", "100541001", "100053801", "100057401", "100044301", "100069901", "100048001", "100052801", "100061801", "100117801", "100114001", "100079101", "100311801", "100043901", "100085501", "100114501", "100050101", "100074001", "100090001", "100037701", "100085101", "100083301", "100046301", "100108401", "100030701", "100310101", "100109401", "100110101", "100050701", "100084301", "100073201", "100059201", "100082001", "100063601", "100077001", "100070901", "100109201", "100050201", "100097301", "100053901", "100119601", "100101301", "100124001", "100120501", "100122101", "100064801", "100101501", "100095401", "100091101", "100048201", "100062001", "100070001", "100073301", "100036401", "100009701", "100012001", "100027701", "100019601", "100094401", "100084801", "100003401", "100036601", "100040201", "100007201", "100099801", "100001901", "100051201", "100051801", "100008701", "100041701", "100040501", "100034501", "100002601", "100012101", "100002101", "100060801", "100039701", "100031801", "100035501", "100049101", "100051901", "100024801", "100045801", "100081501", "100541001", "100053801", "100057401", "100044301", "100069901", "100048001", "100052801", "100061801", "100082101", "100053301", "100063801", "100041101", "100025001", "100046901", "100062901", "100034901", "100058001", "100066601", "100100901", "100105701", "100046101", "100085301", "100090601", "100104301", "100043001", "100055801", "100071001", "100048401", "100058401", "100069501", "100093501", "100104701", "100100701", "100093001", "100028301", "100094901", "100020001", "100060101", "100052601", "100067701", "100068401", "100117801", "100114001", "100079101");
    public static List<String> cids2 = Arrays.asList("100039601", "100017301", "100020801", "100002201", "100006601", "100007101", "100006701", "100021701", "100039001", "100017001", "100015201", "100026901", "100023201", "100010301", "100020901", "100024701", "100025201", "100021201", "100006201", "100056701", "100013101", "100017301", "100020801", "100002201", "100006601", "100007101", "100006701", "100021701", "100039001", "100017001", "100015201", "100026901", "100023201", "100010301", "100020901", "100024701", "100025201", "100021201", "100006201", "100056701", "100013101", "100029501", "100023901", "100009601", "100020201", "100026001", "100078401", "100014401", "100014301", "100029001", "100028001", "100003101", "100033601", "100017501", "100037301", "100038501", "100064201", "100022301", "100047701", "100029201", "100021101", "100032301", "100035801", "100002401", "100034101", "100042501", "100023701", "100032701", "100023401", "100046801", "100005101", "100064501", "100024501", "100006501", "100036501", "100031001", "100025301", "100057601", "100079601", "100005701", "100046201", "100036401", "100009701", "100012001", "100027701", "100019601", "100094401", "100084801", "100003401", "100036601", "100040201", "100007201", "100099801", "100001901", "100051201", "100051801", "100008701", "100041701", "100040501", "100034501", "100002601", "100012101", "100002101", "100060801", "100039701", "100031801", "100035501", "100049101", "100051901", "100024801", "100045801", "100541001", "100081501", "100053801", "100057401", "100044301", "100069901", "100048001", "100052801", "100061801", "100082101", "100053301", "100063801", "100041101", "100025001", "100046901", "100062901", "100034901", "100058001", "100066601", "100100901", "100105701", "100046101", "100085301", "100104301", "100090601", "100043001", "100055801", "100071001", "100048401", "100058401", "100069501", "100093501", "100104701", "100100701", "100093001", "100028301", "100094901", "100020001", "100060101", "100052601", "100067701", "100068401", "100117801", "100114001", "100079101", "100311801", "100043901", "100085501", "100114501", "100050101", "100074001", "100090001", "100037701", "100085101", "100083301", "100046301", "100108401", "100030701", "100310101", "100109401", "100110101", "100050701", "100084301", "100073201", "100059201", "100082001", "100063601", "100077001", "100070901", "100109201", "100050201", "100097301", "100053901", "100119601", "100101301", "100124001", "100120501", "100122101", "100064801", "100101501", "100095401", "100091101", "100048201", "100062001", "100070001", "100073301", "100091501", "100117601", "100119701", "100076501", "100309001", "100069101", "100052201", "100062401", "100059901", "100079901", "100122701", "100104501", "100093301", "100079201", "100065501", "100060501", "100102001", "100056401", "100522501", "100070801", "100073401", "100550701", "100031101", "100061401", "100113201", "100075401", "100117701", "100085201", "100098901", "100617601", "100119501", "100618301", "100123701", "100084201", "100115201", "100072001", "100311101", "100103401", "100072201", "100057701", "100056201", "100082501", "100551601", "100109601", "100613101", "100312001", "100555001", "100637501", "100536701", "100112401", "100111101", "100078501", "100098801", "100552001", "100121701", "100310901", "100104601", "100626901", "100107801", "100312101", "100117501", "100523801", "100102601", "100546501", "100080901", "100112001", "100542801", "100524201", "100613601", "100548101", "100525001", "100081901", "100547601", "100636608", "100636401", "100636604", "100671401", "100636606", "100636601", "100636610", "100636612", "100636602", "100636605", "100636611", "100636607", "100636609", "100636603", "100772701", "100780501", "100770601", "100764201", "100761401", "100759401", "100758001", "100755801", "100755401");

    public static List<String> cids3 = Arrays.asList("100785001", "100781001", "100780501", "100772701", "100770601", "100764201", "100761401", "100759401", "100758001", "100755801", "100755401", "100671401", "100637501", "100636608", "100636610", "100636604", "100636606", "100636602", "100636601", "100785001", "100781001", "100780501", "100772701", "100770601", "100764201", "100761401", "100759401", "100758001", "100755801", "100755401", "100671401", "100637501", "100636608", "100636610", "100636604", "100636606", "100636602", "100636601", "100636605", "100636609", "100636611", "100636612", "100636603", "100636607", "100636401", "100626901", "100618301", "100617601", "100613601", "100613101", "100555001", "100552001", "100551601", "100550701", "100548101", "100547601", "100546501", "100542801", "100541001", "100536701", "100525001", "100524201", "100523801", "100522501", "100312101", "100311801", "100312001", "100311101", "100310901", "100310101", "100309001", "100123701", "100124001", "100122701", "100122101", "100121701", "100120501", "100119701", "100119601", "100119501", "100117801", "100117701", "100117601", "100117501", "100115201", "100114501", "100114001", "100113201", "100112401", "100112001", "100111101", "100110101", "100109601", "100109401", "100109201", "100108401", "100107801", "100105701", "100104701", "100104501", "100104601", "100104301", "100103401", "100102601", "100102001", "100101501", "100101301", "100100901", "100100701", "100099801", "100098801", "100098901", "100097301", "100095401", "100094901", "100093001", "100094401", "100093501", "100093301", "100091501", "100091101", "100090601", "100090001", "100085501", "100085201", "100085301", "100085101", "100084801", "100084301", "100084201", "100083301", "100082001", "100082501", "100082101", "100081901", "100081501", "100080901", "100079601", "100079901", "100079201", "100079101", "100078501", "100078401", "100077001", "100076501", "100075401", "100074001", "100073401", "100073301", "100073201", "100072201", "100072001", "100071001", "100070901", "100070801", "100069901", "100070001", "100069501", "100069101", "100068401", "100067701", "100066601", "100065501", "100064801", "100064201", "100064501", "100063801", "100063601", "100062901", "100062401", "100062001", "100061801", "100061401", "100060801", "100060501", "100060101", "100059901", "100059201", "100058401", "100058001", "100057701", "100057601", "100057401", "100056701", "100056401", "100056201", "100055801", "100053901", "100053801", "100053301", "100052801", "100052601", "100052201", "100051901", "100051801", "100051201", "100050701", "100050201", "100050101", "100049101", "100048401", "100048201", "100048001", "100047701", "100046901", "100046801", "100046301", "100046201", "100046101", "100045801", "100044301", "100043901", "100043001", "100042501", "100041701", "100041101", "100040501", "100040201", "100039701", "100039001", "100038501", "100037701", "100037301", "100036601", "100036501", "100036401", "100035801", "100035501", "100034901", "100034501", "100034101", "100033601", "100032701", "100032301", "100031801", "100031101", "100031001", "100030701", "100029501", "100029201", "100029001", "100028301", "100028001", "100027701", "100026901", "100024801", "100026001", "100025301", "100025201", "100025001", "100024701", "100024501", "100023901", "100023701", "100023401", "100023201", "100022301", "100021701", "100021201", "100021101", "100020901", "100020801", "100020201", "100020001", "100019601", "100017501", "100017301", "100017001", "100015201", "100014401", "100014301", "100013101", "100012101", "100012001", "100010301", "100009701", "100009601", "100008701", "100007201", "100007101", "100006701", "100006601", "100006501", "100006201", "100005701", "100005101", "100003401", "100003101", "100002601", "100002401", "100002201", "100002101", "100001901");

    public static void main(String[] args) throws InterruptedException {
//        System.out.println(cids2.stream().distinct().count());
//        Collection<String> strings = CollectionUtils.removeAll(cids2, cids1);
//        System.out.println(strings.stream().distinct().count());
//        cids3.parallelStream().distinct().sorted()
////                .filter(p -> p.compareTo("100121701") >= 0)
//                .forEach(cid -> articles1(cid));
//        List<String> list = getCids();
//        System.out.println(JSONObject.toJSONString(StringUtils.join(list.stream()
////                .map(s -> "\"" + s + "\"")
//                .collect(Collectors.toList()), ",")));
//        System.out.println(JSONObject.toJSONString(cids1));
//        column("100014301");
        cids2.stream().sorted().forEach(cid -> column(cid));

//        listFoldersAndFileCount("/Users/pangning/geektime/md/");
        Thread.sleep(10000000000L);
    }

    // 获取课程 ID
    public static List<String> getCids() {
        List<String> cids = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            String url = CommonConstant.request.replace("9", String.valueOf(i));
            Map data = JSONObject.parseObject(url);
            String s = geekRequest(productList, data);
            JSONObject jsonObject = JSONObject.parseObject(s);
            JSONObject data1 = (JSONObject) jsonObject.get("data");
            if (data1 == null || data1.get("list") == null) {
                continue;
            }
            List<JSONObject> list = JSONObject.parseArray(data1.get("list").toString(), JSONObject.class);
            List<String> productId = list.stream().map(p -> p.get("product_id").toString()).collect(Collectors.toList());
            System.out.println("productIds size " + productId.size());
            cids.addAll(productId);
        }
        return cids;
    }


    // 下载指定专栏
    public static void articles(String cid) {
        //获取专栏标题
        String info = info(Long.parseLong(cid));

        Map data = new HashMap();
        data.put("cid", cid);
        data.put("order", "earliest");
        data.put("prev", 0);
        data.put("sample", false);
        data.put("size", 1000);
        String result = geekRequest(articlesUrl, data);
        JSONObject jsonObject = JSONObject.parseObject(result);
        JSONObject dataJSON = jsonObject.getJSONObject("data");
        JSONArray list = dataJSON.getJSONArray("list");
        boolean needmp3 = false;
        //文件目录
        String path = filePath + File.separator + cid + "_" + info;
        String newPath = filePath + File.separator + cid + "_" + info + "-[" + list.size() + "]";
        File oldDirectory = new File(path);
        File newDirectory = new File(newPath);

        // 检查旧文件夹是否存在
        if (oldDirectory.exists() && oldDirectory.isDirectory()) {
            // 尝试重命名文件夹
            boolean success = oldDirectory.renameTo(newDirectory);
            if (!success) {
                System.out.println("文件夹重命名失败！");
            }
        }
        String head = getFileName("00_目录");
        if (!FileUtil.isExist(newPath + File.separator + "md", head)) {
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

            FileUtil.write(sb.toString(), newPath + File.separator + "md", head);
            log.info("保存:{}成功!", head);
        } else {
            File[] files = null;
            try {
                files = new File(newPath + File.separator + "md").listFiles();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (files.length == list.size() + 1 && !needmp3) {
                log.info(info + " 文档齐全，跳过内容");
                return;
            }
        }

        Collections.reverse(list);
        //循环生成文章
        list.stream().forEach(obj -> saveMarkDown((JSONObject) obj, newPath, needmp3));
        log.info("完成!");
    }


    // 按专栏数量倒序查看
    public static void listFoldersAndFileCount(String directoryPath) {
        TreeMap<String, Integer> treeMap = new TreeMap<>(Comparator.reverseOrder());
        File directory = new File(directoryPath);

        // 检查目录是否存在
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("指定的目录不存在或不是一个目录！");
            return;
        }

        File[] folders = directory.listFiles(File::isDirectory);

        for (File folder : folders) {
            File[] files = folder.listFiles();
            int count = files.length;
            treeMap.put(folder.getName(), count);
        }
        treeMap.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).forEach(entry -> System.out.println(entry.getValue() + "---" + entry.getKey()));
    }

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
    public static void column(String cid) {
        // 获取专栏信息
        String result = geekRequest(articlesUrl, createRequestData(cid));
        JSONArray list = JSONObject.parseObject(result).getJSONObject("data").getJSONArray("list");
        //获取专栏标题
        String info = info(Long.parseLong(cid));
        if (list.size() > 300) {
            log.error("======================= list.size()>200 =======================" + info);
            return;
        }
        //文件目录
        String mdPath = filePath + "md" + File.separator + cid + "_" + info + "-[" + list.size() + "]";
        String mp3Path = filePath + "mp3" + File.separator + cid + "_" + info + "-[" + list.size() + "]";

        String head = FileUtil.replace("000__目录") + ".md";
        String lowerCase = info.toLowerCase();
        boolean needmp3 = lowerCase.contains("kafka")||
                lowerCase.contains("redis")||
                lowerCase.contains("mysql")||
                lowerCase.contains("中间件")||
                lowerCase.contains("管理")||
                lowerCase.contains("消息");
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
                        sb.append("* ").append("[").append(fileName).append("]").append("(").append("./").append(fileName).append(")").append("\n");
                    });
            FileUtil.write(sb.toString(), mdPath, head);
            log.info("保存:{}成功!", head);
        }
        for (int i = 0; i < list.size(); i++) {
            saveArticle((JSONObject) list.get(i), mdPath, mp3Path, i, needmp3);
        }
        log.info("完成!");
    }

    private static void saveArticle(JSONObject obj, String mdPath, String mp3Path, int i, boolean needmp3) {
        try {
            String id = obj.getString("id");
            String article_title = obj.getString("article_title");
            article_title = removeDigitsFromFirstFourChars(article_title);
            String prefix = getPrefix(i);
            String fileName = prefix + "_" + getFileName(article_title);

            String audio_download_url = obj.getString("audio_download_url");
            String audio_title = obj.getString("audio_title");
            String mp3Name = prefix + "_" + getMp3Name(article_title);

            if (!FileUtil.isExist(mdPath, fileName)) {
                ThreadUtil.execute(() -> Html2MDUtil.convert(article(id), mdPath + File.separator + fileName, true));
            }
            if (!FileUtil.isExist(mp3Path, mp3Name) && StringUtils.isNotBlank(audio_download_url) && needmp3) {
                ThreadUtil.execute(() -> HttpUtil.downloadFile(audio_download_url, mp3Path + File.separator + mp3Name));
            }
        } catch (Exception e) {
            log.error("error ", e);
        }
    }

    private static String getPrefix(int i) {
        String prefix = "";
        if (i < 10) {
            prefix = "00" + i;
        } else if (i < 100) {
            prefix = "0" + i;
        } else {
            prefix = String.valueOf(i);
        }
        return prefix;
    }

    public static void saveMarkDown(JSONObject obj, String path, boolean needmp3) {
        try {
            String id = obj.getString("id");
            String article_title = obj.getString("article_title");
            String audio_download_url = obj.getString("audio_download_url");
            String audio_title = obj.getString("audio_title");

            //文件名
            String fileName = getFileName(article_title);
            String mp3Name = getMp3Name(article_title);
            String fileNamePath = path + File.separator + "md";
            if (!FileUtil.isExist(fileNamePath, fileName)) {
                String article = article(id);
                ThreadUtil.execute(() -> Html2MDUtil.convert(article, fileNamePath + File.separator + fileName, true));
            }
            String fileNamePath2 = path + File.separator + "mp3";
            if (!FileUtil.isExist(fileNamePath2, mp3Name) && StringUtils.isNotBlank(audio_download_url) && needmp3) {
                ThreadUtil.execute(() -> HttpUtil.downloadFile(audio_download_url, fileNamePath2 + File.separator + mp3Name));
            }
        } catch (Exception e) {
            log.error("error ", e);
        }
    }

    public static String getFileName(String title) {
        //去除关键字
        String fileName = FileUtil.replace(title) + ".md";
        return fileName;
    }

    public static String removeDigitsFromFirstFourChars(String input) {
        if (input == null || input.length() <= 4) {
            // 如果字符串为空或长度小于等于4，直接返回处理后的字符串
            return input == null ? "" : input.replaceAll("[\\d_ ]", "");
        }

        // 提取前4位和剩余部分
        String firstFourChars = input.substring(0, 6);
        String remainingChars = input.substring(6);

        // 使用正则表达式替换前4位中的数字、下划线和空格
        String modifiedFirstFourChars = firstFourChars.replaceAll("[\\d_ 第讲丨]", "");

        // 组合修改后的前4位和剩余部分
        return modifiedFirstFourChars + remainingChars;
    }

    public static String getMp3Name(String title) {
        //去除关键字
        String fileName = FileUtil.replace(title) + ".mp3";
        return fileName;
    }

    public static String article(String id) {
        Map data = new HashMap();
        data.put("id", id);
        data.put("include_neighbors", true);
        data.put("is_freelyread", true);
        String result = geekRequest(articleUrl, data);
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (jsonObject == null || jsonObject.getJSONObject("data") == null || jsonObject.getJSONObject("data").getString("article_content") == null) {
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
        JSONObject jsonObject = JSONObject.parseObject(result);
        JSONObject dataJSON = jsonObject.getJSONObject("data");
        String title = dataJSON.getString("title");
        JSONObject author = dataJSON.getJSONObject("author");
        String name = author.getString("name");
        title = title + "_" + name;
        log.info("获取专栏名称:{}", title);
        return title;
    }

    public static String geekRequest(String url, Map data) {
        Map header = new HashMap<>();
        header.put("Cookie", CommonConstant.cookies);
        header.put("Origin", "https://time.geekbang.org");
        header.put("Host", "time.geekbang.org");
        header.put("User-Agent", userAgent + v);
        header.put("Content-Type", "text/plain");
        header.put("Origin", "https://time.geekbang.org");
        String result = HttpUtil.postJson(url, header, data);
        log.info("geekRequest request:" + data + " response:" + result);
        sleep();
        return result;
    }

    public static void sleep() {
        try {
            Random random = new Random();
            Thread.sleep(random.nextInt(800) + interval);
        } catch (InterruptedException e) {
            log.error("异常:", e);
        }
    }
}
