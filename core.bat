@echo off

color 0b

title Vector Shield
REM Below is the file import section for batch files.
set "CyPatRead=Cypat.json"
If Not Exist "%CyPatRead%" (Goto :Error)
rem This will read a file into an array of variables and populate it 
setlocal EnableExtensions EnableDelayedExpansion
for /f "delims=" %%a in ('Type "%CyPatRead%"') do (
    set /a count+=1
    set "Line[!count!]=%%a"
)
rem Display array elements
For /L %%i in (1,1,%Count%) do (
    echo "CyPat%%i" is assigned to ==^> "!Line[%%i]!"
)
pause>nul
Exit
::***************************************************
:Error
cls & Color c
echo(
echo   The file "%CyPatRead%" does not exist, make sure that you have not modified or changed the .json in any way.
Pause>nul
exit /b
::***************************************************
