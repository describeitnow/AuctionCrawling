package AuctionCrawling;

import java.util.*;
import java.io.FileOutputStream;
/*import java.io.IOException;*/
import java.lang.Exception;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class AuctionCrawling {

	public static void main(String[] args) throws Exception
	{
		String keyword="겨울";
		OutputStreamWriter out
			=new OutputStreamWriter(new FileOutputStream("auction.txt"));
		for(int page=1;page<10;page++)
		{
			String url
				=String.format("http://browse.auction.co.kr/search?keyword=%s&p=%s"
						,keyword, Integer.toString(page));
			String contents=getItemsPage(url);
			String itemNames=getItemsName(contents);
			out.write(itemNames);
		}
		out.close();
	}
	
	public static String getItemsPage(String url) throws Exception
	{
		HttpClient client = HttpClients.createDefault();
		HttpGet get = new HttpGet(url);
		HttpResponse response=client.execute(get);
		String body = EntityUtils.toString(response.getEntity(), Charset.forName("euc-kr"));
		return body;
	}
	
	public static String getItemsName(String content)
	{
		String itemNames="";
		Document doc = Jsoup.parse(content);
		Elements items = doc.select("span.text--title");
		for(Element item:items) itemNames+=(item.text()+"\r\n");
		return itemNames;
	}

}
