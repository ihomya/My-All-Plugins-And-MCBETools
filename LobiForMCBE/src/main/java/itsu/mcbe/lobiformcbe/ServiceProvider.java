package itsu.mcbe.lobiformcbe;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.nao20010128nao.BloodyGarden.LobiServices;
import com.nao20010128nao.BloodyGarden.structures.Chat;
import com.nao20010128nao.BloodyGarden.structures.Group;
import com.nao20010128nao.BloodyGarden.structures.Notification;

import cn.nukkit.Player;
import cn.nukkit.utils.TextFormat;
import cn.nukkit.window.CustomFormWindow;
import cn.nukkit.window.SimpleFormWindow;
import cn.nukkit.window.element.Button;
import cn.nukkit.window.element.Element;
import cn.nukkit.window.element.Input;
import cn.nukkit.window.element.Label;

public class ServiceProvider {

    public static final int STATE_LOGIN = 0;
    public static final int STATE_MENU = 1;
    public static final int STATE_PUBLIC_GROUP_LIST = 2;
    public static final int STATE_PRIVATE_GROUP_LIST = 3;
    public static final int STATE_GROUP_THREADS = 4;
    public static final int STATE_NOTIFICATIONS = 5;

    private int state;
    private int id;
    private boolean loggedIn = false;

    private LobiServices lobi;
    private List<String> gids = new ArrayList<>();
    private int groupIdIndex;

    private Random rand = new Random();

    public ServiceProvider() {
        lobi = new LobiServices();
    }

    /*WindowAPI*/
    /*ログイン画面*/
    public void sendLoginWindow(Player player) {
        if(loggedIn) {
            sendMainMenu(player);
            return;
        }
        Element elements[] = {new Label("Lobi機能を使用するにはログインが必要です。"), new Input("メールアドレス", "", ""), new Input("パスワード", "", "")};
        CustomFormWindow window = new CustomFormWindow(id = getRandom(), "Lobi for MCBE", elements);
        state = STATE_LOGIN;
        player.sendWindow(window);
    }

    /*メインメニュー*/
    public void sendMainMenu(Player player) {
        Button buttons[] = {new Button("公開グループ"), new Button("プライベートグループ"), new Button("通知"), new Button("ログアウト")};
        SimpleFormWindow window = new SimpleFormWindow(id = getRandom(), "Lobi for MCBE - メニュー", "",  buttons);
        state = STATE_MENU;
        player.sendWindow(window);
    }

    /*公開グループリスト*/
    public void sendPublicGroupsWindow(Player player) throws MalformedURLException, IOException, URISyntaxException {
        List<Button> btns = new ArrayList<>();
        for(Group group : lobi.getPublicGroupList().items) {
            Timestamp ts = new Timestamp(Long.parseLong(group.last_chat_at) * 1000);
            Date date = new Date(ts.getTime());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            btns.add(new Button(TextFormat.WHITE + group.name + "\n" + TextFormat.YELLOW + "人数: " + TextFormat.WHITE + group.total_users + "人" + TextFormat.YELLOW + "/最終更新: " + TextFormat.WHITE + sdf.format(date), "url", group.icon));
            gids.add(group.uid);
        }
        SimpleFormWindow window = new SimpleFormWindow(id = getRandom(), "Lobi for MCBE - 公開グループ", "", (Button[]) btns.toArray(new Button[0]));
        state = STATE_PUBLIC_GROUP_LIST;
        player.sendWindow(window);
    }

    /*プライベートグループリスト*/
    public void sendPrivateGroupsWindow(Player player) throws MalformedURLException, IOException, URISyntaxException {
        List<Button> btns = new ArrayList<>();
        for(Group group : lobi.getPrivateGroupList().items) {
            Timestamp ts = new Timestamp(Long.parseLong(group.last_chat_at) * 1000);
            Date date = new Date(ts.getTime());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            btns.add(new Button(TextFormat.WHITE + group.name + "\n" + TextFormat.YELLOW + "人数: " + TextFormat.WHITE + group.total_users + "人" + TextFormat.YELLOW + "/最終更新: " + TextFormat.WHITE + sdf.format(date), "url", group.icon));
            gids.add(group.uid);
        }
        SimpleFormWindow window = new SimpleFormWindow(id = getRandom(), "Lobi for MCBE - プライベートグループ", "", (Button[]) btns.toArray(new Button[0]));
        state = STATE_PRIVATE_GROUP_LIST;
        player.sendWindow(window);
    }

    /*チャット画面*/
    public void sendThreads(Player player, int index) throws MalformedURLException, IOException, URISyntaxException {
        List<Element> elements = new ArrayList<>();
        int count = 0;
        if(!(index == -1)) groupIdIndex = index;

        elements.add(new Input("新規チャットを投稿...", "", ""));

        for(Chat chat : lobi.getGroup(gids.get(index)).chats) {
            Timestamp ts = new Timestamp(chat.created_date * 1000);
            Date date = new Date(ts.getTime());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");

            StringBuffer sb = new StringBuffer();
            sb.append(TextFormat.AQUA + chat.user.name + "\n");
            sb.append(TextFormat.GRAY + sdf.format(date) + "\n");
            sb.append(TextFormat.WHITE + chat.message + "\n");
            sb.append(TextFormat.BLUE + "ぐー: " + TextFormat.WHITE + chat.liked);
            sb.append(TextFormat.RED + " ぶー: " + TextFormat.WHITE + chat.boos_count);
            sb.append(TextFormat.YELLOW + " ブクマ: " + TextFormat.WHITE + chat.bookmarks_count + "\n\n");

            elements.add(new Label(sb.toString()));
            count++;
            if(count == 11) break;
        }

        CustomFormWindow window = new CustomFormWindow(id = getRandom(), "Lobi for MCBE - " + lobi.getGroup(gids.get(index)).name, (Element[]) elements.toArray(new Element[0]));
        state = STATE_GROUP_THREADS;
        player.sendWindow(window);
    }

    /*通知画面*/
    public void sendNotices(Player player) throws MalformedURLException, IOException, URISyntaxException {
        List<Element> elements = new ArrayList<>();
        int count = 0;

        for(Notification notice : lobi.getNotifications().notifications) {
            Timestamp ts = new Timestamp(notice.created_date * 1000);
            Date date = new Date(ts.getTime());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            elements.add(new Label(TextFormat.AQUA + notice.user.name + "\n" + TextFormat.GRAY + sdf.format(date) + "\n" + TextFormat.WHITE + notice.title.template.replaceAll("\\{\\{p1\\}\\}", notice.user.name) + "\n" + notice.message + "\n\n"));
            count++;
            if(count == 11) break;
        }

        CustomFormWindow window = new CustomFormWindow(id = getRandom(), "Lobi for MCBE - 通知", (Element[]) elements.toArray(new Element[0]));
        state = STATE_NOTIFICATIONS;
        player.sendWindow(window);
    }

    /*LobiAPI*/
    public void makeThread(Player player, String message) throws MalformedURLException, URISyntaxException, IOException {
        lobi.newThread(gids.get(groupIdIndex), message, false);
    }

    public void login(Player player, String mail, String password) throws MalformedURLException, URISyntaxException, IOException {
        loggedIn = lobi.login(mail, password);
    }

    public void logout(Player player) {
        loggedIn = false;
    }

    /*Getter*/
    private int getRandom() {
        return rand.nextInt(300000);
    }

    public int getWindowId() {
        return id;
    }

    public int getState() {
        return state;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

}
