@echo off

rem #######################################
rem #                                     #
rem # Windows用デバッグモード起動ファイル #
rem #                                     #
rem #######################################

cls
chcp 932 > nul

echo MCServerGUI.jarをデバッグモードで起動します。

goto Main

:Main
set JAR=MCServerGUI.jar
if not exist %JAR% (
	goto Finish
)


java -jar %JAR%

goto END

:Finish
	echo %JAR%がありません。
	echo キーを押して終了してください。
	pause > nul

:END
	timeout /t 1 > nul