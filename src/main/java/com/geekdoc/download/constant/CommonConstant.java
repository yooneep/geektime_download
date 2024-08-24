package com.geekdoc.download.constant;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author: zhumlu@yonyou.com
 * @date: 2022/2/28 16:18
 * @description:
 */
public class CommonConstant {
    public static final String articleUrl = "https://time.geekbang.org/serv/v1/article";

    public static final String chapterUrl = "https://time.geekbang.org/serv/v1/column/chapter";
    public static final String articlesUrl = "https://time.geekbang.org/serv/v1/column/articles";
    public static final String infoUrl = "https://time.geekbang.org/serv/v3/column/info";
    public static final String productList = "https://time.geekbang.org/serv/v4/pvip/product_list";

    public static final String chapters = "https://time.geekbang.org/serv/v1/chapters";
    public static final String filePath = "/Users/pangning/geektime/";
    public static String cookies;
    public static final String request = "{\"tag_ids\":[],\"product_type\":0,\"product_form\":1,\"pvip\":0,\"prev\":9,\"size\":20,\"sort\":1,\"with_articles\":true}";

    static {
        URL resource = CommonConstant.class.getClassLoader().getResource("cookies");
        try {
            List<String> lines = Files.readAllLines(Paths.get(resource.toURI()));
            cookies = lines.get(0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static final String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36";
}
