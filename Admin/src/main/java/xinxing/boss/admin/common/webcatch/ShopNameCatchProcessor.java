//package xinxing.boss.admin.common.webcatch;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import us.codecraft.webmagic.Page;
//import us.codecraft.webmagic.Site;
//import us.codecraft.webmagic.Spider;
//import us.codecraft.webmagic.processor.PageProcessor;
//
///**
// * 天猫根据链接获取数据
// */
//public class ShopNameCatchProcessor implements PageProcessor {
//
//	private String shopName;
//	private Site site = Site.me().setRetryTimes(2)
//	// .setDomain("blog.sina.com.cn")
//			.setSleepTime(200);
//
//	@Autowired
//	@Override
//	public void process(Page page) {
//		this.shopName = page.getHtml()
//				.xpath("//*[@class='slogo-shopname']/strong/text()").toString();
//	}
//
//	@Override
//	public Site getSite() {
//		return site;
//	}
//
//	public static String getShopNameByUrl(String url) {
//		ShopNameCatchProcessor sncp = new ShopNameCatchProcessor();
//		Spider.create(sncp).addUrl(url).thread(10).run();
//		return sncp.shopName;
//	}
//}