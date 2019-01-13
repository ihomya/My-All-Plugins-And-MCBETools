package mine;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.Listener;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;

public class Main extends PluginBase implements Listener {
	
	String url = "http://news.yahoo.co.jp/pickup/rss.xml";
	String set = "pubDate";
	boolean b = false;

	public void onEnable(){
		getLogger().info("起動しました。");
	}

	public boolean onCommand(final CommandSender sender, Command command, String label, String[] args){
		switch(command.getName()){
		case "news":
			try{if(args[0] !=null){}}
			catch(Exception e){
				sender.sendMessage(TextFormat.RED + "種類を入力してください。");
				return true;
			}
			if(args[0].equals("world")) url = "http://news.yahoo.co.jp/pickup/world/rss.xml";
			if(args[0].equals("entertainment")) url = "http://news.yahoo.co.jp/pickup/entertainment/rss.xml";
			if(args[0].equals("japan")) url = "http://news.yahoo.co.jp/pickup/japan/rss.xml";
			if(args[0].equals("economy")) url = "http://news.yahoo.co.jp/pickup/economy/rss.xml";
			if(args[0].equals("sports")) url = "http://news.yahoo.co.jp/pickup/sports/rss.xml";
			if(args[0].equals("science")) url = "http://news.yahoo.co.jp/pickup/science/rss.xml";
			if(args[0].equals("local")) url = "http://news.yahoo.co.jp/pickup/local/rss.xml";
			if(args[0].equals("topix")) url = "http://news.yahoo.co.jp/pickup/rss.xml";
			if(args[0].equals("help")){
				sender.sendMessage(TextFormat.AQUA + "MineNews ヘルプ");
				sender.sendMessage(TextFormat.YELLOW + "/news <種類>" + TextFormat.WHITE + " 種類に応じたニュースを見れます。");
				sender.sendMessage(TextFormat.GREEN + "最新:topix 経済:economy 国際:world 国内:japan エンタメ:entertainment スポーツ:sports 科学:science 地域:local");
				sender.sendMessage(TextFormat.YELLOW + "/news weather <地域>" + TextFormat.WHITE + " 地域に応じた天気予報を見れます。");
				sender.sendMessage(TextFormat.LIGHT_PURPLE + "<北海道/東北>");
				sender.sendMessage(TextFormat.GREEN + "北海道:Hokkaido 青森:Aomori 岩手:Iwate 宮城:Miyagi 秋田:Aikita 山形:Yamagata 福島:Fukushima");
				sender.sendMessage(TextFormat.LIGHT_PURPLE + "<関東>");
				sender.sendMessage(TextFormat.GREEN + "栃木:Tochigi 群馬:Gunma 茨城:Ibaraki 埼玉:Saitama 千葉:Chiba 東京:Tokyo 神奈川:Kanagawa 山梨:Yamanashi");
				sender.sendMessage(TextFormat.LIGHT_PURPLE + "<信越>");
				sender.sendMessage(TextFormat.GREEN + "新潟:Nigata 長野:Nagano");
				sender.sendMessage(TextFormat.LIGHT_PURPLE + "<北陸>");
				sender.sendMessage(TextFormat.GREEN + "富山:Toyama 石川:Ishikawa 福井:Fukui");
				sender.sendMessage(TextFormat.LIGHT_PURPLE + "<東海>");
				sender.sendMessage(TextFormat.GREEN + "愛知:Aichi 岐阜:Gifu 静岡:Shizuoka 三重:Mie");
				sender.sendMessage(TextFormat.LIGHT_PURPLE + "<近畿>");
				sender.sendMessage(TextFormat.GREEN + "大阪:Osaka 兵庫:Hyogo 京都:Kyoto 滋賀:Shiga 奈良:Nara 和歌山:Wakayama");
				sender.sendMessage(TextFormat.LIGHT_PURPLE + "<中国>");
				sender.sendMessage(TextFormat.GREEN + "鳥取:Tottori 島根:Shimane 岡山:Okayama 広島:Hiroshima 山口:Yamaguchi");
				sender.sendMessage(TextFormat.LIGHT_PURPLE + "<四国>");
				sender.sendMessage(TextFormat.GREEN + "徳島:Tokushima 香川:Kagawa 愛媛:Ehime 高知:Kochi");
				sender.sendMessage(TextFormat.LIGHT_PURPLE + "<九州/沖縄>");
				sender.sendMessage(TextFormat.GREEN + "福岡:Fukuoka 佐賀:Saga 長崎:Nagasaki 熊本:Kumamoto 大分:Oita 長崎:Nagasaki 鹿児島:Kagoshima 沖縄:Okinawa");
				return true;
			}
			if(args[0].equals("weather")){
				try{if(args[1] !=null){}}
				catch(Exception e){
					sender.sendMessage(TextFormat.RED + "地域を入力してください。");
					return true;
				}
				set = "description";
				b = true;
				//Tohoku
				if(args[1].equals("Hokkaido")) url = "https://rss-weather.yahoo.co.jp/rss/days/1400.xml";
				if(args[1].equals("Aomori")) url = "https://rss-weather.yahoo.co.jp/rss/days/3110.xml";
				if(args[1].equals("Iwate")) url = "https://rss-weather.yahoo.co.jp/rss/days/3310.xml";
				if(args[1].equals("Miyagi")) url = "https://rss-weather.yahoo.co.jp/rss/days/3410.xml";
				if(args[1].equals("Akita")) url = "https://rss-weather.yahoo.co.jp/rss/days/3210.xml";
				if(args[1].equals("Yamagata")) url = "https://rss-weather.yahoo.co.jp/rss/days/3510.xml";
				if(args[1].equals("Fukushima")) url = "https://rss-weather.yahoo.co.jp/rss/days/3610.xml";
				//Kanto
				if(args[1].equals("Tokyo")) url = "https://rss-weather.yahoo.co.jp/rss/days/4410.xml";
				if(args[1].equals("Kanagawa")) url = "https://rss-weather.yahoo.co.jp/rss/days/4610.xml";
				if(args[1].equals("Saitama")) url = "https://rss-weather.yahoo.co.jp/rss/days/4310.xml";
				if(args[1].equals("Chiba")) url = "https://rss-weather.yahoo.co.jp/rss/days/4510.xml";
				if(args[1].equals("Ibaraki")) url = "https://rss-weather.yahoo.co.jp/rss/days/4010.xml";
				if(args[1].equals("Tochigi")) url = "https://rss-weather.yahoo.co.jp/rss/days/4110.xml";
				if(args[1].equals("Gunma")) url = "https://rss-weather.yahoo.co.jp/rss/days/4210.xml";
				if(args[1].equals("Yamanashi")) url = "https://rss-weather.yahoo.co.jp/rss/days/4910.xml";
				//Shinetsu
				if(args[1].equals("Niigata")) url = "https://rss-weather.yahoo.co.jp/rss/days/5410.xml";
				if(args[1].equals("Nagano")) url = "https://rss-weather.yahoo.co.jp/rss/days/4810.xml";
				//Hokuriku
				if(args[1].equals("Toyama")) url = "https://rss-weather.yahoo.co.jp/rss/days/5510.xml";
				if(args[1].equals("Ishikawa")) url = "https://rss-weather.yahoo.co.jp/rss/days/5610.xml";
				if(args[1].equals("Fukui")) url = "https://rss-weather.yahoo.co.jp/rss/days/5710.xml";
				//Tokai
				if(args[1].equals("Aichi")) url = "https://rss-weather.yahoo.co.jp/rss/days/5110.xml";
				if(args[1].equals("Gifu")) url = "https://rss-weather.yahoo.co.jp/rss/days/5210.xml";
				if(args[1].equals("Shizuoka")) url = "https://rss-weather.yahoo.co.jp/rss/days/5010.xml";
				if(args[1].equals("Mie")) url = "https://rss-weather.yahoo.co.jp/rss/days/5310.xml";
				//Kinki
				if(args[1].equals("Osaka")) url = "https://rss-weather.yahoo.co.jp/rss/days/6200.xml";
				if(args[1].equals("Hyogo")) url = "https://rss-weather.yahoo.co.jp/rss/days/6310.xml";
				if(args[1].equals("Kyoto")) url = "https://rss-weather.yahoo.co.jp/rss/days/6110.xml";
				if(args[1].equals("Shiga")) url = "https://rss-weather.yahoo.co.jp/rss/days/6010.xml";
				if(args[1].equals("Nara")) url = "https://rss-weather.yahoo.co.jp/rss/days/6410.xml";
				if(args[1].equals("Wakayama")) url = "https://rss-weather.yahoo.co.jp/rss/days/6510.xml";
				//Chugoku
				if(args[1].equals("Tottori")) url = "https://rss-weather.yahoo.co.jp/rss/days/6910.xml";
				if(args[1].equals("Shimane")) url = "https://rss-weather.yahoo.co.jp/rss/days/6810.xml";
				if(args[1].equals("Okayama")) url = "https://rss-weather.yahoo.co.jp/rss/days/6610.xml";
				if(args[1].equals("Hiroshima")) url = "https://rss-weather.yahoo.co.jp/rss/days/6710.xml";
				if(args[1].equals("Yamaguchi")) url = "https://rss-weather.yahoo.co.jp/rss/days/8120.xml";
				//Shikoku
				if(args[1].equals("Tokushima")) url = "https://rss-weather.yahoo.co.jp/rss/days/7110.xml";
				if(args[1].equals("Kagawa")) url = "https://rss-weather.yahoo.co.jp/rss/days/7200.xml";
				if(args[1].equals("Ehime")) url = "https://rss-weather.yahoo.co.jp/rss/days/7310.xml";
				if(args[1].equals("Kochi")) url = "https://rss-weather.yahoo.co.jp/rss/days/7410.xml";
				//Kyushu
				if(args[1].equals("Fukuoka")) url = "https://rss-weather.yahoo.co.jp/rss/days/8210.xml";
				if(args[1].equals("Saga")) url = "https://rss-weather.yahoo.co.jp/rss/days/8510.xml";
				if(args[1].equals("Nagasaki")) url = "https://rss-weather.yahoo.co.jp/rss/days/8410.xml";
				if(args[1].equals("Kumamoto")) url = "https://rss-weather.yahoo.co.jp/rss/days/8610.xml";
				if(args[1].equals("Oita")) url = "https://rss-weather.yahoo.co.jp/rss/days/8310.xml";
				if(args[1].equals("Nagasaki")) url = "https://rss-weather.yahoo.co.jp/rss/days/8710.xml";
				if(args[1].equals("Kagoshima")) url = "https://rss-weather.yahoo.co.jp/rss/days/8810.xml";
				//Okinawa
				if(args[1].equals("Okinawa")) url = "https://rss-weather.yahoo.co.jp/rss/days/9110.xml";
			}
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(url);

            // ドキュメントのrootを取得
            Element root = document.getDocumentElement();
            // root直下の"channel"に含まれるノードリストを取得
            NodeList channel = root.getElementsByTagName("channel");
            // "channel"直下の"title"に含まれるノードリストを取得
            NodeList title = ((Element)channel.item(0)).getElementsByTagName("title");

            // タイトルを表示
            String title1 = "§a"+ title.item(0).getFirstChild().getNodeValue();
            title1 = title1.replaceAll("Yahoo!天気・災害", "");
            sender.sendMessage(title1);
            sender.sendMessage("-----------------------------------------");
            // 各"item"内のノードリストを取得
            NodeList itemlist = root.getElementsByTagName("item");
            String result;
            // itemの数だけループさせて全てのitemを取得
            for(int i=0; i < itemlist.getLength(); i++){
                Element element = (Element)itemlist.item(i);
                // "title","link","pubDate"を取得
                NodeList item_title = element.getElementsByTagName("title");
                NodeList item_pdate = element.getElementsByTagName(set);
                
                result = TextFormat.YELLOW + item_title.item(0).getFirstChild().getNodeValue();
                result = result.replaceAll(" - Yahoo!天気・災害", "");
                // 各要素を表示
                sender.sendMessage(result);
                if(!b){
                	sender.sendMessage("/" + item_pdate.item(0).getFirstChild().getNodeValue());
                }
            }
            sender.sendMessage("-----------------------------------------");
            return true;
        } catch (ParserConfigurationException e) {
        	sender.sendMessage(TextFormat.RED + "エラーが発生しました。");
        	return true;
        } catch (SAXException e) {
        	sender.sendMessage(TextFormat.RED + "エラーが発生しました。");
        	return true;
        } catch (IOException e) {
        	sender.sendMessage(TextFormat.RED + "エラーが発生しました。");
        	return true;
        }
		}
		return true;
	}



}
