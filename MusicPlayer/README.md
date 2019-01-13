# MusicPlayer
サウンドリソースパックの音楽をサーバー内で再生するプラグイン  
  
<img src = "https://github.com/itsu020402/MusicPlayer/blob/master/1.jpg">  
<img src = "https://github.com/itsu020402/MusicPlayer/blob/master/2.jpg">  
  
## 使い方
1.再生したい音楽(.ogg形式)を用意する  
2.タグを編集する  
3.plugins/MusicPlayer/Config.ymlを編集(リソースパックのファイル名を拡張子付きで書く)  
4.サーバー起動  
ブレイズロッドを持てば再生フォームが開きます。  
  
## サウンドリソースパックの作り方
基本的な作り方(manifest.jsonなど)は各自調べてください  
サンプルとして<a href="https://github.com/itsu020402/MusicPlayer/blob/master/SpecialMusicPack1.mcpack">クラシックの曲が入ったリソースパック</a>を用意しています。  
### ディレクトリ構造
(root.mcpack)  
├ manifest.json  
└ sounds  
&nbsp;&nbsp;├ sound_definitions.json  
&nbsp;&nbsp;└ music  
&nbsp;&nbsp;&nbsp;&nbsp;└ (.ogg形式音楽ファイル群)  
  
### sound_definitions.jsonの書き方
音楽の情報を書くファイルです。json方式で記述します。なお、プラグイン中で表示されるアーティストなどの情報はプラグインが音楽ファイルを直接解析しているため記述する必要はありません。  
タイトルは識別子となるもの、"name"にはルートディレクトリから音楽ファイルまでのパス（拡張子なし）を記述してください。それ以外は特に弄る必要はありません。  
### 例  
{  
&nbsp;"music.zenzenzense": {  
&nbsp;&nbsp;&nbsp;&nbsp;"category": "music",  
&nbsp;&nbsp;&nbsp;&nbsp;"sounds": [  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"name": "sounds/music/zenzenzense",  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"volume": 1  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}  
&nbsp;&nbsp;&nbsp;&nbsp;]  
&nbsp;},  
  
&nbsp;"music.sparkle": {  
&nbsp;&nbsp;&nbsp;&nbsp;"category": "music",  
&nbsp;&nbsp;&nbsp;&nbsp;"sounds": [  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"name": "sounds/music/sparkle",  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"volume": 1  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}  
&nbsp;&nbsp;&nbsp;&nbsp;]  
&nbsp;}  
}  
  
## 音楽ファイルのタグについて
音楽ファイルは.ogg形式のみです。  
タグエディタがない場合AIMPやAudacityなどを使って編集してください。  
<a href="https://convertio.co/ja/mp3-ogg/">.mp3から.oggに変換する</a>/<a href="https://convertio.co/ja/wav-ogg/">.wavから.oggに変換する</a>  
### 必ず記述するもの
- 年(DATE)  
- タイトル(TITLE)  
- アーティスト(ARTIST)  
- 作曲者(COMPOSER)  
- アルバム名（識別子となるのでリソースパックで指定したタイトルとなります）(ALBUM)  
- ジャンル(GENLE)  
  
## 開発者向け
### OGG Vorbis(.ogg)ファイル解析技術
独自に開発した専用パーサ(MusicParser.java)を使用して解析を行っています。  
まず、最初の106バイト分を飛ばし、そこから各種データを取得しています。文字列は0x00が来るまで読み込み、そこまでの区切りを一つのデータとしています。  
データはXXXX=YYYYのように保存され、かつ2バイトおきに保持されているためこのパーサもデータを読み取った後2バイト進めています。  
データ方式のは前述のとおりすべて[=]によって区切られているため[=]でに分割し、後ろのデータを実際のデータとして読み込んでいます。  
  
  
