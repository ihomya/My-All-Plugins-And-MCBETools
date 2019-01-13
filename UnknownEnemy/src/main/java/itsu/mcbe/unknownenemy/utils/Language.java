package itsu.mcbe.unknownenemy.utils;

import cn.nukkit.utils.Config;

public class Language {
	
	public static final int LANG_JPN = 0;
	public static final int LANG_ENG = 1;
	
	public static String get(String key, int langCode, String... args) {
		String result = "";
		Config conf = new Config(Config.YAML);
		
		conf.load(Language.class.getClassLoader().getResourceAsStream("lang/" + (langCode == Language.LANG_JPN ? "jpn" : "eng") + ".yml"));
		result = conf.getString(key);
		
		int count = 0;
		for (String arg : args) {
			result = result.replaceAll("\\{%" + count + "}", arg);
			count++;
		}
		
		return result;
	}

}
