@echo off
SETLOCAL ENABLEDELAYEDEXPANSION
FOR /F  "delims=,= TOKENS=1,2" %%A IN (del_module_var.txt) DO (
	if %%A==root_path (
		set root_path=%%B
	)
	if %%A==app_name (
		set app_name=%%B
	)

	if %%A==module_dir (
		set module_dir=%%B
	)
	
	if %%A==module_name (
		set module_name=%%B
	)
	
	if %%A==upper_module_name (
		set upper_module_name=%%B
	)
	
	if %%A==upper_module_name (
		set upper_module_name=%%B
	)

	if %%A==del_dir (
		set del_dir=%%B
	)

	if %%A==module_root_dir (
		set module_root_dir=%%B
	)


	
	
)

if %del_dir%==1 (
	FOR /F "TOKENS=*" %%A IN (del_module_dir.txt) DO (
		set filepath=!root_path!%%A
		set filepath=!filepath:{app_name}=%app_name%!
		set filepath=!filepath:{module_root_dir}=%module_root_dir%!
		echo !filepath!
		rmdir /s /q !filepath!
	)
) else (

	FOR /F "TOKENS=*" %%A IN (del_module.txt) DO (
		set filepath=!root_path!%%A
		set filepath=!filepath:{app_name}=%app_name%!
		set filepath=!filepath:{module_dir}=%module_dir%!
		set filepath=!filepath:{module_name}=%module_name%!
		set filepath=!filepath:{upper_module_name}=%upper_module_name%!
		echo !filepath!
		del /f /s /q !filepath!
	)
)

ENDLOCAL
PAUSE