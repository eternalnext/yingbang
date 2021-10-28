package com.example.yingbang.datadownload;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.yingbang.entity.Movie;
import com.example.yingbang.service.MovieService;
import com.example.yingbang.util.HttpUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class DataDownload {
    @Autowired
    private MovieService movieService;

    @Scheduled(fixedRate = 43200000)//每12小时执行一次
    public void movieDownload() throws URISyntaxException, IOException, InterruptedException {
        //请求地址
        //https://movie.douban.com/j/search_subjects?type=movie&tag=热门&sort=recommend&page_limit=20&page_start=0
        String url = "https://movie.douban.com/j/search_subjects";
        Map<String, String> map = new HashMap<>();
        Map<String, String> mapTitle = new HashMap<>();
        System.out.println("start");
        //设置请求参数
        map.put("type", "movie");
        map.put("tag", "热门");
        map.put("sort", "recommend");
        map.put("page_limit", "20");
        //设置请求头
        mapTitle.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        mapTitle.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:71.0) Gecko/20100101 Firefox/71.0");
        mapTitle.put("Cookie", "bid=QNoG_zn4mZY; _pk_id.100001.4cf6=6209709719896af7.1575619506.2.1575940374.1575621362.; __utma=30149280.1889677372.1575619507.1575619507.1575940335.2; __utmz=30149280.1575619507.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __utma=223695111.986359939.1575619507.1575619507.1575940335.2; __utmz=223695111.1575619507.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __yadk_uid=QVSP2uvzzDBrpnvHKzZpZEWJnuARZ4aL; ll=\"118259\"; _vwo_uuid_v2=D1FC45CAE50CF6EE38D245C68D7CECC4F|e8d1db73f4c914f0b0be7ed85ac50d14; trc_cookie_storage=taboola%2520global%253Auser-id%3D690a21c0-9ad9-4f8d-b997-f0decb3cfc9b-tuct4e39874; _pk_ses.100001.4cf6=*; ap_v=0,6.0; __utmb=30149280.0.10.1575940335; __utmc=30149280; __utmb=223695111.0.10.1575940335; __utmc=223695111; __gads=ID=2f06cb0af40206d0:T=1575940336:S=ALNI_Ma4rv9YmqrkIUNXsIt5E7zT6kZy2w");
        //获取前100条数据，可以自行更改
        for(int i = 0; i < 360; i+=20){
            if( i != 0 && i % 40 ==0){
                Thread.sleep(60000);
            }
            map.put("page_start", i+"");
            String html = HttpUtils.doGetHtml(url, map, mapTitle);
            if(html == ""){
                break;
            }
            JSONObject jsonObject = JSONObject.parseObject(html);
            JSONArray jsonArray = jsonObject.getJSONArray("subjects");
            for(int j = 0; j < jsonArray.size(); j++){  //循环遍历每页数据
                Movie movie = new Movie();
                JSONObject json = (JSONObject) jsonArray.get(j);
                int id = json.getInteger("id");
                if(movieService.cheackID(id)){
                    continue;
                }
                movie.setId(id);
                movie.setRate(json.getDouble("rate"));
                movie.setTitle(json.getString("title"));

                //下载保存图片
                HttpUtils.doGetImage(json.getString("cover"), id);

                String url2 = json.getString("url");
                Map<String, String> map2 = new HashMap<>();
                String html2 = HttpUtils.doGetHtml(url2, map2, mapTitle);
                //解析HTML获取DOM对象
                Document doc = Jsoup.parse(html2);
                String protagonist = "";
                //获取导演名称
                Element element = doc.select("div#info a[rel=v:directedBy]").first();
                movie.setDirector(element.text());
                //编辑
                element = doc.select("#info").first();
                String str = element.text();
                protagonist = "";
                // 正则表达式screenwriter截取str的编辑
                String screenwriter = "(编剧: )(.*?)( 主演:)";
                Pattern pattern_screenwriter = Pattern.compile(screenwriter);
                Matcher matcher = pattern_screenwriter.matcher(str);
                if (matcher.find()){
                    protagonist = matcher.group().substring(4, matcher.group().length() - 4);
                    movie.setScreenwriter(protagonist);
                }else{
                    movie.setScreenwriter(protagonist);
                }
                //主演
                Elements elements = doc.select("div#info a[rel=v:starring]");
                protagonist = "";
                for (Element e : elements) {
                    protagonist += e.text()+" / ";
                }
                if(!protagonist.equals("")){
                    protagonist = protagonist.substring(0, protagonist.length()-3);
                }
                movie.setProtagonist(protagonist);
                //类型
                int property_number = 0;
                elements = doc.select("div#info span[property=v:genre]");
                protagonist = "";
                for (Element e : elements) {
                    protagonist += e.text()+" / ";
                }
                if(!protagonist.equals("")){
                    protagonist = protagonist.substring(0, protagonist.length()-3);
                }
                movie.setType(protagonist);
                //国家
                element = doc.select("#info").first();
                str = element.text();
                protagonist = "";
                // 正则表达式country截取str的国家
                String country = "(制片国家/地区: )(.*?)( 语言:)";
                Pattern pattern_country = Pattern.compile(country);
                matcher = pattern_country.matcher(str);
                if (matcher.find()){
                    protagonist = matcher.group().substring(9, matcher.group().length() - 4);
                    movie.setCountry(protagonist);
                }else{
                    movie.setCountry(protagonist);
                }
                //语言
                element = doc.select("#info").first();
                str = element.text();
                protagonist = "";
                // 正则表达式language截取str的语言
                String language = "(语言: )(.*?)( 上映日期:)";
                Pattern pattern_language = Pattern.compile(language);
                matcher = pattern_language.matcher(str);
                if (matcher.find()){
                    protagonist = matcher.group().substring(4, matcher.group().length() - 6);
                    movie.setLanguage(protagonist);
                }else{
                    movie.setCountry(protagonist);
                }
                //上映日期
                elements = doc.select("div#info span[property=v:initialReleaseDate]");
                protagonist = "";
                for (Element e : elements) {
                    protagonist += e.text()+" / ";
                }
                if(!protagonist.equals("")){
                    protagonist = protagonist.substring(0, protagonist.length()-3);
                }
                movie.setInitialReleaseDate(protagonist);
                //获取电影时长
                protagonist = "";
                element = doc.select("div#info span[property=v:runtime]").first();
                if(element == null){
                    movie.setDateTime(protagonist);
                }else {
                    movie.setDateTime(element.text());
                }
                //别名
                element = doc.select("#info").first();
                str = element.text();
                protagonist = "";
                // 正则表达式language截取str的语言
                String alias = "(又名: )(.*?)( IMDb:)";
                Pattern pattern_alias = Pattern.compile(alias);
                matcher = pattern_alias.matcher(str);
                if (matcher.find()){
                    protagonist = matcher.group().substring(4, matcher.group().length() - 6);
                    movie.setAlias(protagonist);
                }else{
                    movie.setAlias(protagonist);
                }
                movieService.save(movie);
            }
        }
        System.out.println("数据获取完成。。。");
    }
}
