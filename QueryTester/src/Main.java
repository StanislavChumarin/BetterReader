import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Hello World!");
		Document doc = Jsoup.connect("http://www.ex.ua/ru/video/foreign?r=23775").get();
		System.out.println(doc.select(".include_0 ~ table td:has(img[src*=arr_r]) a").attr("href"));

		doc = Jsoup.connect("http://www.ex.ua/ru/video/foreign?r=23775&p=1").get();
		System.out.println(doc.select(".include_0 ~ table td:has(img[src*=arr_r]) a").attr("href"));

		doc = Jsoup.connect("http://www.ex.ua/ru/video/foreign?r=23775&p=2851").get();
		System.out.println(doc.select(".include_0 ~ table td:has(img[src*=arr_r]) a").attr("href").isEmpty());
	}
}
