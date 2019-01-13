package itsu.java.msgfe.discordconsole.core;

import java.util.HashMap;
import java.util.Map;

import itsu.java.msgfe.core.Controller;
import itsu.java.msgfe.plugin.EventListener;
import itsu.java.msgfe.plugin.Plugin;
import itsu.java.msgfe.utils.IOUtils;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

public class MainClass extends Plugin implements EventListener {
	
	private static String TOKEN;
	private static final String PREFIX = "/";
	
	private static IDiscordClient client;

	private final Map<IGuild, IChannel> lastChannel = new HashMap<>();
	
	@Override
	public void onLoad() {
		
		Controller.appendPluginConsoleText("起動しました。");
		
		MainClass main = new MainClass();
		
		Controller.addListener(main);
		Controller.appendPluginConsoleText("Discord botのログイン中です...");
		
		try {
			TOKEN = IOUtils.readFile("./MCServerGUI/plugins/DiscordToken.txt");
			
			client = new ClientBuilder().withToken(TOKEN).build();
			client.getDispatcher().registerListener(main);
			client.login();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@EventSubscriber
	public void onReady(ReadyEvent event) {
		Controller.appendPluginConsoleText("Discord botの準備完了！");
	}

	@EventSubscriber
	public void onMessage(MessageReceivedEvent event) throws RateLimitException, DiscordException, MissingPermissionsException {
		IMessage message = event.getMessage();
		IUser user = message.getAuthor();
		if (user.isBot()) return;

		IChannel channel = message.getChannel();
		String mes = message.getContent();

		if (mes.startsWith(PREFIX)) {
			String command = mes.replaceFirst(PREFIX, "");
			if(command.equals("start")) {
				Controller.startServer();
				Controller.appendPluginConsoleText("[DiccordBot] コマンドを受信しました。: " + command);
				channel.sendMessage("[RESPONSE] サーバーへ送信されました。: " + command);
				return;
				
			} else if(command.equals("stop")) {
				Controller.stopServer();
				Controller.appendPluginConsoleText("[DiccordBot] コマンドを受信しました。: " + command);
				channel.sendMessage("[RESPONSE] サーバーへ送信されました。: " + command);
				return;
				
			} else {
				Controller.sendMessage(command);
				Controller.appendPluginConsoleText("[DiccordBot] コマンドを受信しました。: " + command);
				channel.sendMessage("[RESPONSE] サーバーへ送信されました。: " + command);
			}
		}
	}

	@Override
	public void onStartServer() {
		for(IChannel channel : client.getChannels()) {
			channel.sendMessage("サーバーが起動しました。 - " + Controller.getServerInfo().get("motd"));
		}
	}

	@Override
	public void onStopServer() {
		for(IChannel channel : client.getChannels()) {
			channel.sendMessage("サーバーが終了しました。");
		}
	}

	@Override
	public void onSendConsole(String text) {
		
	}

	@Override
	public void onSendCommand(String command) {
		
	}

	@Override
	public void onClickStartButton() {
		
	}

	@Override
	public void onClickStopButton() {
		
	}


}
