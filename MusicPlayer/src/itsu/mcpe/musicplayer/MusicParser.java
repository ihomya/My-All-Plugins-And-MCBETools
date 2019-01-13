package itsu.mcpe.musicplayer;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

/**
 * Ogg Vorbis (*.ogg) ファイル専用パーサークラス
 * 独自でOggファイルを解析して作ったため不完全な場合があります。
 *
 * @author itsu
 */

public class MusicParser {


    public static Music parseMusic(File file) throws IOException {

        List<String> data = new ArrayList<>();

        String year = "Unknown";
        String token = "Unknown";
        String artist = "Unknown";
        String composer = "Unknown";
        String genre = "Unknown";
        String title = "Unknown";

        DataInputStream st = new DataInputStream(new FileInputStream(file));

        /*106バイト分飛ばす*/
        for(int i = 0;i < 106;i++) {
            st.readByte();
        }

        /*データを読み込んで登録*/
        data.add(readString2(st));
        data.add(readString2(st));
        data.add(readString2(st));
        data.add(readString2(st));
        data.add(readString2(st));
        data.add(readString2(st));

        /*データ抽出*/
        for(String temp : data) {
            try {
                String src = temp.split("=")[0].toLowerCase();
                if(src.startsWith("date")) { /*年 オリジナル: DATE=XXXX*/
                    year = temp.split("=")[1];
                } else if(src.startsWith("album")) { /*アルバム名（このプラグインでは識別子として使います） オリジナル: ALBUM=XXXX*/
                    token = temp.split("=")[1];
                } else if(src.startsWith("artist")) { /*アーティスト オリジナル: ARTIST=XXXX*/
                    artist = temp.split("=")[1];
                } else if(src.startsWith("composer")) { /*作曲者 オリジナル: COMPOSER=XXXX*/
                    composer = temp.split("=")[1];
                } else if(src.startsWith("genre")) { /*ジャンル オリジナル: GENRE=XXXX*/
                    genre = temp.split("=")[1];
                } else if(src.startsWith("title")) { /*タイトル オリジナル: TITLE=XXXX*/
                    title = temp.split("=")[1];
                }
            } catch(ArrayIndexOutOfBoundsException e) {
                continue;
            }
        }

        /*ロードした音楽ファイルを一つのクラスオブジェクトとしてインスタンスを作成する*/
        return new Music(title, artist, year, token, composer, genre);
    }

    private static String readString2(DataInputStream in) {

        try{
            String data = "";

            while(true) {
                byte b = in.readByte();

                /*終了*/
                if(b == 0x00) {
                    break;
                }

                /*除外バイト*/
                if(b == 0xff || b == 0x0a || b == 0x0b || b == 0x0c || b == 0x0d || b == 0x0e || b == 0x0f
                        || b == 0x10|| b == 0x11 || b == 0x12 || b == 0x13 || b == 0x14 || b == 0x15 || b == 0x16 || b == 0x17 || b == 0x18 || b == 0x19
                            || b == 0x1f || b == 0x1a || b == 0x1b || b == 0x1c || b == 0x1d || b == 0x1e || b == 0x1f) {
                    continue;
                }

                /*一度Hex値へ変換(文字化け防止)*/
                char c = (char) b;
                data += Integer.toHexString(c);
            }

            /*2バイト分飛ばす*/
            in.readByte();
            in.readByte();

            /*ffを削除*/
            String src = data.replaceAll("ff", "");
            if((src.length() % 2) == 1 && src.endsWith("f")) {
                src = src.substring(0, src.length() - 1);
            }

            /*Hex値から文字列へ変換(UTF-8エンコード)*/
            return new String(DatatypeConverter.parseHexBinary(src), "UTF-8");

        }catch(IOException e) {
            return null;
        }
    }
}
