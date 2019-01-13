@echo off

rem ServerManagementSystem用 起動バッチファイル(Windows専用)

rem 作成: Itsu
rem 最終更新: 2017/8/30 13:51


setlocal enabledelayedexpansion

rem Autorestart変数が0の場合は自動再起動、それ以外は終了
set Autorestart=0

rem count変数は起動回数の保持に使われます。いじらないでください。
set count=1

rem Soft変数は起動するjarの名前です。
set Soft=nukkit-1.0-SNAPSHOT.jar



goto Main

:Main
	if not exist %Soft% (
		goto Finish
	)
	
	cls
	chcp 932 > nul
	
	echo !count!回目の起動です。
	
	if exist ExitChecker.dat del ExitChecker.dat
	java -Djline.terminal=jline.UnsupportedTerminal -jar %Soft% -Xmx4096m
	
	goto END

:Finish
	echo %Soft%がありません。
	echo キーを押して終了してください。
	pause > nul

:END
	if %Autorestart%==0 (
		set /a count=!count!+1
		goto Main
	)

	type nul > ExitChecker.dat