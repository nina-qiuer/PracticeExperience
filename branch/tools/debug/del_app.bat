@echo off
SETLOCAL ENABLEDELAYEDEXPANSION
FOR /F  "delims=,= TOKENS=1,2" %%A IN (del_app_var.txt) DO (
	if %%A==root_path (
		set root_path=%%B
	)
	if %%A==app_name (
		set app_name=%%B
	)

	
)

FOR /F "TOKENS=*" %%A IN (del_app.txt) DO (
	set filepath=!root_path!%%A
	set filepath=!filepath:{app_name}=%app_name%!
	echo !filepath!
	rmdir /s /q !filepath!
)
ENDLOCAL
PAUSE