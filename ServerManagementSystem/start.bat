@echo off

rem ServerManagementSystem�p �N���o�b�`�t�@�C��(Windows��p)

rem �쐬: Itsu
rem �ŏI�X�V: 2017/8/30 13:51


setlocal enabledelayedexpansion

rem Autorestart�ϐ���0�̏ꍇ�͎����ċN���A����ȊO�͏I��
set Autorestart=0

rem count�ϐ��͋N���񐔂̕ێ��Ɏg���܂��B������Ȃ��ł��������B
set count=1

rem Soft�ϐ��͋N������jar�̖��O�ł��B
set Soft=nukkit-1.0-SNAPSHOT.jar



goto Main

:Main
	if not exist %Soft% (
		goto Finish
	)
	
	cls
	chcp 932 > nul
	
	echo !count!��ڂ̋N���ł��B
	
	if exist ExitChecker.dat del ExitChecker.dat
	java -Djline.terminal=jline.UnsupportedTerminal -jar %Soft% -Xmx4096m
	
	goto END

:Finish
	echo %Soft%������܂���B
	echo �L�[�������ďI�����Ă��������B
	pause > nul

:END
	if %Autorestart%==0 (
		set /a count=!count!+1
		goto Main
	)

	type nul > ExitChecker.dat