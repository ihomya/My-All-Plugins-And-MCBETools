@echo off

rem #######################################
rem #                                     #
rem # Windows�p�f�o�b�O���[�h�N���t�@�C�� #
rem #                                     #
rem #######################################

cls
chcp 932 > nul

echo MCServerGUI.jar���f�o�b�O���[�h�ŋN�����܂��B

goto Main

:Main
set JAR=MCServerGUI.jar
if not exist %JAR% (
	goto Finish
)


java -jar %JAR%

goto END

:Finish
	echo %JAR%������܂���B
	echo �L�[�������ďI�����Ă��������B
	pause > nul

:END
	timeout /t 1 > nul