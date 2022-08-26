package cog;

import java.io.FileOutputStream;
import java.io.InputStream;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ImageCrawling {

	String url = "https://www.google.com/search?q=";
	String url2 = "&sxsrf=ALiCzsaOwslV6CvGCPeZUa5ruXQ1y2gblA:1660181747006&source=lnms&tbm=isch&sa=X&ved=2ahUKEwiPxqv60r35AhWCAd4KHUoADN4Q_AUoAnoECAIQBA&biw=1046&bih=750&dpr=1.25";
	String keyword = "부산"+url2;
	
	public ImageCrawling() {
		crawling(url);
	}
	
	public void crawling(String url) {
		try {
			Connection connection = Jsoup.connect(url+keyword);
			Document doc = connection.get();
			Elements els = doc.getElementsByClass("tvh9oe.BIB1wf");
			System.out.println(els);
			
//		InputStream is = ;
//		FileOutputStream fos = new FileOutputStream("text.html");
//		int b;
//		while((b = is.read())!= -1) {
//			fos.write(b);
//		}
//		fos.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	

	public static void main(String[] args) {
		new ImageCrawling();
	}

}
