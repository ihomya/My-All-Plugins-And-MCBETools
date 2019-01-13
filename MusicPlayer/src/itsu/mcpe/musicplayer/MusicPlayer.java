package itsu.mcpe.musicplayer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;

public class MusicPlayer extends PluginBase {

    private Map<String, Music> musics = new HashMap<>();

    public void onEnable() {
        getLogger().info(TextFormat.GREEN + "MusicPlayer for OGG Vorbis" + this.getDescription().getVersion());
        getLogger().info(TextFormat.GREEN + "Developed by Itsu(itsu020402)");
        getLogger().info(TextFormat.RED + "このプラグインにはGPL v3.0ライセンスが適用されます。（Jupiter準拠）");
        getLogger().info(TextFormat.YELLOW + "このプラグインの使用にはサウンドのリソースパックが必要です。");
        getLogger().info(TextFormat.YELLOW + "なおoggファイルのタグは正しく記述し、タイトル/年/アーティスト/作曲者/トラック番号/ジャンル/アルバムの指定が必須です。");
        getLogger().info(TextFormat.YELLOW + "また識別子は「アルバム名」のタグを使用するのでリソースパックで指定した音楽名をアルバム名として記述してください。");
        getLogger().info("");
        getLogger().info(TextFormat.GREEN + "起動しました");
        getLogger().info(TextFormat.AQUA + "音楽を読み込み中...");
        
        init();
        
        getLogger().info(TextFormat.GREEN + "音楽の読み込みが完了しました。");

        getServer().getPluginManager().registerEvents(new EventListener(), this);
    }

    @SuppressWarnings("unchecked")
	private void init() {
    	try {
    		
    		/*plugins/MusicPlayer/ がなければ作る*/
	        File root = new File("plugins/MusicPlayer/");
	        if(!root.exists()) {
	        	root.mkdirs();
	        }
	        
	        /*コンフィグをロード*/
	        Config conf = new Config(new File("plugins/MusicPlayer/Config.yml"), Config.YAML);
	        
	        /*コンフィグがなければ作成*/
	        if(!conf.load("plugins/MusicPlayer/Config.yml")) {
	        	conf.set("ResourceName", "NAME.mcpack");
	        	conf.save();
	        	getLogger().alert("コンフィグを作成しました。適宜設定して再起動してください。");
	        	getServer().getPluginManager().disablePlugin(this);/*安全のためプラグインを無効化*/
	        	return;
	        }
	        
	        /*コンフィグで指定されたリソースパックを取得*/
	        File resource = new File("resource_packs/" + conf.getString("ResourceName"));
	        if(!resource.exists()) {
	        	getLogger().alert("コンフィグで指定されたリソースパックが見つかりません。");
	        	return;
	        }
	        
	        /*リソースパック（*.zip/*.mcpack）解析*/
	        ZipFile zip = new ZipFile(resource);
	        List<ZipEntry> entries = new ArrayList<>();
	        List<String> pathes = new ArrayList<>();
	        
	        /*リソパ内のファイル/フォルダをすべて取得*/
	        entries = (List<ZipEntry>) Collections.list(zip.entries());
	        
	        /*取得したファイルの名前だけ取得して登録*/
	        entries.forEach(entry -> pathes.add(entry.getName()));
	        
	        /*sounds/music/ フォルダの中身だけ取得して音楽ファイルだけを抽出する*/
	        pathes.stream()
	        		.filter(str -> str.startsWith("sounds/music/"))/*条件にあてはまるものだけを取得する*/
	        		.forEach(str -> {
	        	        try {
		        			ZipEntry entry = zip.getEntry(str);
		        			InputStream in = zip.getInputStream(entry);
		        			File to = new File(root.toPath().toAbsolutePath() + "/" + str.replaceAll("sounds/music/", ""));
		        			
		        			/*取得した音楽ファイルをFileオブジェクトに変換するためにplugins/MusicPlayerにコピーする*/
							Files.copy(in, to.toPath().toAbsolutePath());
							
							/*終了処理*/
		                    in.close();
		                    to = null;
		                    in = null;
		                    entry = null;
							
						} catch(FileAlreadyExistsException e) {
							
						} catch (IOException e) {
							e.printStackTrace();
						}
	        		});
	        
	        zip.close();
	        
	        /*plugins/MusicPlayer 内のファイルをすべて取得する*/
	        File[] files = new File("plugins/MusicPlayer/").listFiles();
	        
	        /*取得したファイルの数だけループ*/
	        for(File file : files) {
	        	
	        	/* *.oggファイルだった場合*/
	        	if(file.getName().endsWith(".ogg")) {
		        	getLogger().info(TextFormat.LIGHT_PURPLE + file.getName() + TextFormat.RESET + "を読み込み中...");
	            	
		        	/*音楽ファイルを解析*/
	                Music music = MusicParser.parseMusic(file);
	                
	                if(music == null) {
	                	getLogger().alert(file.getName() + "の読み込みに失敗しました。");
	                	continue;
	                }
	                
	                /*プレイリストに登録*/
	                musics.put(music.getTitle(), music);
	                
	                file.delete();
	        	} else {
	        		continue;
	        	}
	        }
	        
	        ServiceProvider.setMusics(musics);
	        
    	} catch(IOException e) {
    		getLogger().alert("エラーが発生しました。");
        	return;
    	}
    }
}
