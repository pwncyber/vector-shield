@echo off

color 0b

title Vector Shield


REM ==================================Imports CyPat.json============================================
set "File2Read=Lusrmgr.json"
rem This will read a file into an array of variables and populate it 
setlocal EnableExtensions EnableDelayedExpansion
for /f "delims=" %%a in ('Type "%File2Read%"') do (
    set /a count+=1
    set "Lusrmgr[!count!]=%%a"
)
rem Display array elements
For /L %%i in (1,1,%Count%) do (
    echo "Lusrmgr%%i" is assigned to ==^> "!Lusrmgr[%%i]!"
)
REM ==================================Imports LocalSecPol.json============================================
set "File2Read=LocalSecPol.json"
rem This will read a file into an array of variables and populate it 
setlocal EnableExtensions EnableDelayedExpansion
for /f "delims=" %%a in ('Type "%File2Read%"') do (
    set /a count+=1
    set "LocalSecPol[!count!]=%%a"
	echo %Count%
)
rem Display array elements
For /L %%i in (1,1,3) do (
    echo "LocalSecPol%%i" is assigned to ==^> "!LocalSecPol[%%i]!"
)
pause
