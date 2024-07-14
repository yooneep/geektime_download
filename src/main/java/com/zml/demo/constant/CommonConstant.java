package com.zml.demo.constant;

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
    public static final String articlesUrl = "https://time.geekbang.org/serv/v1/column/articles";
    public static final String infoUrl = "https://time.geekbang.org/serv/v3/column/info";
    public static final String productList = "https://time.geekbang.org/serv/v4/pvip/product_list";
    public static final String filePath = "/Users/pangning/geek/";
    public static String cookies = "LF_ID=310e745-2a320d1-867cd8a-ec4350d; _tea_utm_cache_20000743={%22utm_source%22:%22geektime_search%22%2C%22utm_medium%22:%22geektime_search%22%2C%22utm_campaign%22:%22geektime_search%22%2C%22utm_term%22:%22geektime_search%22%2C%22utm_content%22:%22geektime_search%22}; mantis5539=b71f223a32ed49939ab24b8717903ed8@5539; MEIQIA_TRACK_ID=2ZTseLqD1fxLIiPi7inKACDWlPi; MEIQIA_VISIT_ID=2ZTseJmvDkLEeC6gSr7IF9XZBds; _gcl_au=1.1.573150852.1706874801; ERID=69920d7-1f9f067-82611dd-19999ac; _ga_WK6J6CS6FN=GS1.1.1706874800.1.0.1706874806.54.0.0; _ga=GA1.2.1204572027.1702457242; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%2218d69aaa1e6a9b-06f1d019d88768c-1f525637-1930176-18d69aaa1e72bd9%22%2C%22first_id%22%3A%22%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%2C%22%24latest_landing_page%22%3A%22https%3A%2F%2Fwww.geekbang.org%2F%22%7D%2C%22%24device_id%22%3A%2218d69aaa1e6a9b-06f1d019d88768c-1f525637-1930176-18d69aaa1e72bd9%22%7D; GCID=4cf4487-90c793e-a085da3-a209576; GRID=4cf4487-90c793e-a085da3-a209576; gksskpitn=f2fe13b5-b445-4e2c-926a-b9614cdfc15f; Hm_lvt_022f847c4e3acd44d4a2481d9187f1e6=1706497944,1707202734,1708497475,1708585957; Hm_lvt_59c4ff31a9ee6263811b23eb921a5083=1706497944,1707202734,1708497475,1708585957; _gid=GA1.2.1293862370.1708915389; GCESS=BgYERcL0QwwBAQsCBgAIAQcBCJX1HgAAAAAABQQAAAAACQEBDQEBAwQKPd1lBwRT2mLcAgQKPd1lCgQAAAAABAQAjScA; tfstk=etWyXd_6fpbfnZo1aIvegNt3wa9JKdU_Y9TBxMjHVUYuwzLF8G_jdBX72vzF8wRCPMvWt28coU_5NMiRMNsiFTNJdvJJpp4_5Rw_2gppK3UNlrSJf4PMiPw_CgYTYLag5unAn7IHms3oSDd_GiLc3PSRZdaNNEknQD-JZey9SvDh3K6ySgViKvX24QSzg2K0ttBdpJheZnK25orVrZsnMR_vpDGK9I2D0FZXIXhpZnK25orq9Xd0nn8_cdf..; _gat=1; SERVERID=3431a294a18c59fc8f5805662e2bd51e|1709002590|1709001995; Hm_lpvt_59c4ff31a9ee6263811b23eb921a5083=1709002590; Hm_lpvt_022f847c4e3acd44d4a2481d9187f1e6=1709002590; __tea_cache_tokens_20000743={%22web_id%22:%227337940780423587588%22%2C%22user_unique_id%22:%222028949%22%2C%22timestamp%22:1709002590091%2C%22_type_%22:%22default%22}; gk_process_ev={%22referrer%22:%22https://time.geekbang.org/course/intro/100027801?tab=catalog%22%2C%22target%22:%22page_course_video_detail%22%2C%22utime%22:1709002590461%2C%22referrerTarget%22:%22page_course_video_detail%22}";
    public static final String request = "{\"tag_ids\":[],\"product_type\":0,\"product_form\":1,\"pvip\":0,\"prev\":9,\"size\":20,\"sort\":4,\"with_articles\":true}";

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
