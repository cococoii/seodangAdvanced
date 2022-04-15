package kr.spring.qna.controller;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import kr.spring.qna.vo.NewsVO;

@Controller
public class NewsController {
   @RequestMapping("/news/list.do")
   public String getNewsList(Model model) throws Exception{
      SimpleDateFormat origin_format =
               new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
      //q=취업(q=%EC%B7%A8%EC%97%85)
      //xml형식 데이터
      String news_url = "https://news.google.co.kr/news/feeds?q=%EC%B7%A8%EC%97%85&pz=1&cf=all&ned=kr&hl=ko&topic=e&output=rss";
      
      //xml형식 데이터를 -> 제이슨 ???
      URL url = new URL(news_url);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      conn.setRequestProperty("Content-type", "application/json");
      System.out.println("Response code: " + conn.getResponseCode());
      
      //xml 문서를 파싱 : xml을 메모리에 올림
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      dbf.setIgnoringElementContentWhitespace(false);
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document document = db.parse(conn.getInputStream());
      document.getDocumentElement().normalize();

      // item태그로 등록된 데이터 추리기
      NodeList articles = document.getElementsByTagName("item");

      // item태그의 하위태그들이 'title', 'description', 'pubDate'인 데이터들을 찾아서 VO에 담아주기
      // 각각의 VO에 담긴 데이터들을 list에 담아주기
      ArrayList<NewsVO> myRss = new ArrayList<NewsVO>();
      for(int i=0;i<articles.getLength();i++){
            NewsVO myNews = new NewsVO();
            NodeList article = articles.item(i).getChildNodes();
            for(int j=0;j<article.getLength();j++){
                Node n = article.item(j);

                if(n.getNodeName().equals("title")){
                    myNews.setTitle(n.getFirstChild().getNodeValue());
                }
                if(n.getNodeName().equals("description")){
                   myNews.setDescription(n.getFirstChild().getNodeValue());
                }
                if(n.getNodeName().equals("pubDate")){
                    Date date = origin_format.parse(n.getFirstChild().getNodeValue());
                    
                    myNews.setPubDate(new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").format(date));
                }
            }
            myRss.add(myNews);
        }

      conn.disconnect();
      
      // list 반환
      model.addAttribute("list",myRss);
      
      return "newsList";
   }
}