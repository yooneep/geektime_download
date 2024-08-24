package com.geekdoc.download.constant;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CanlanderTool {

    static String cookies = "meituan.ee.ones.fe_ssoid=eAGFj78vA1EcwPNCpDE1NxlvMLQdLu-9ez9NetXG6FdILHI_vk9E3A1XsXRQGxFBGJhoKgYJaSRisFj9C9biFouIgUVFzPZPPvl8ciifvXaQ_fB4eL1HqbUCS_VVP3YAnCSG1DEwYvvSZWA4aEEZCyKjmAlLgVKGYVCUMO8LWcU5CKZDiKFRI1VNPY9KNUa44GWlNS_XSM3zhKzIqmvfvb99HNACov-K1U_aqDXe6u7c7NKJ7tn95zbdQKXBgcmpShKBZT21z7PO6fPmxctJM7tsZ1fNoX57_faoWPiF91HuL-wYOZhH3OUiFMbX0jUqNJrzSLEAR4JRLBeI7O0IjrmgirXQ8BoEDePySANRnBLMpHQ1dinIgGgVYgEc5m0fAkx8EhBZogxz8CFyfSDQgwFExDZQfhHq5TCENJ1JliGepVuoL02Tb-TefuM**eAENxsEBwCAIA8CVgm0Rxikk7D-C3ussPw5yafmmr76vqV-yAAHNBNpHHVXwojbaXj55DzEPWYAS0w**LX7xQm_-V-FY75y4wHu5MT4CNcdv22VnMXArAQNSDFV2w2WctIlFxXywGISdrhwTyxaphWmoKH8-jgAW2WnDbw**MjkxNDAyNCxwYW5nbmluZzAzLOW6nuWugSxwYW5nbmluZzAzQG1laXR1YW4uY29tLDEsMDMxNDY5MjcsMTcyMjY1NzYzMTY5MA";
    static String str = "{\n" +
            "    \"projectId\": \"16561\",\n" +
            "    \"type\": \"DEVTASK\",\n" +
            "    \"parentId\": 85821757,\n" +
            "    \"assigned\": \"pangning03\",\n" +
            "    \"subtypeId\": 48602,\n" +
            "    \"name\": \"需求评审\",\n" +
            "    \"customField13127\": 1114140,\n" +
            "    \"customField13962\": 3,\n" +
            "    \"unit\": \"datetime\",\n" +
            "    \"expectStart\": 1722182400000\n" +
            "}";

    static String url = "https://ones.sankuai.com/api/proxy/fastIssue";

    public static void main(String[] args) {
        post(str);
    }


    static void post(String jsonData) {
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // 设置请求方法为 POST
            con.setRequestMethod("POST");

            // 设置请求头
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Cookie", cookies);

            // 发送 POST 请求的主体
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(jsonData);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // 打印响应结果
            System.out.println(response.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
