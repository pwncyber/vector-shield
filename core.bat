@echo off

color 0b

echo[

type ASCII.txt

title Vector Shield

echo[

REM ==================================Imports Networking.json============================================
set "File2Read=Networking.json"
rem This will read a file into an array of variables and populate it 
setlocal EnableExtensions EnableDelayedExpansion
    set /a count = 0
for /f "delims=" %%a in ('Type "%File2Read%"') do (
    set /a count+=1
    set "Networking[!count!]=%%a"
)
rem Display array elements
For /L %%i in (1,1,%Count%) do (
    echo "Networking%%i" is assigned to ==^> "!Networking[%%i]!"
)
REM ==================================Imports LocalSecPol.json============================================
set "File2Read=LocalSecPol.json"
rem This will read a file into an array of variables and populate it 
setlocal EnableExtensions EnableDelayedExpansion
    set /a count = 0
for /f "delims=" %%a in ('Type "%File2Read%"') do (
    set /a count+=1
    set "LocalSecPol[!count!]=%%a"
)
rem Display array elements
For /L %%i in (1,1,%Count%) do (
    echo "LocalSecPol%%i" is assigned to ==^> "!LocalSecPol[%%i]!"
)
REM ==================================Imports Lurmgr.json============================================
set "File2Read=Lusrmgr.json"
rem This will read a file into an array of variables and populate it 
setlocal EnableExtensions EnableDelayedExpansion
    set /a count = 0
for /f "delims=" %%a in ('Type "%File2Read%"') do (
    set /a count+=1
    set "Lusrmgr[!count!]=%%a"
)
rem Display array elements
For /L %%i in (1,1,%Count%) do (
    echo "Lusrmgr%%i" is assigned to ==^> "!Lusrmgr[%%i]!"
)
REM ==================================Imports Services.json============================================
set "File2Read=Services.json"
rem This will read a file into an array of variables and populate it 
setlocal EnableExtensions EnableDelayedExpansion
    set /a count = 0
for /f "delims=" %%a in ('Type "%File2Read%"') do (
    set /a count+=1
    set "Services[!count!]=%%a"
)
rem Display array elements
For /L %%i in (1,1,%Count%) do (
    echo "Services%%i" is assigned to ==^> "!Services[%%i]!"
)
REM ==================================Imports CyPat.json============================================
set "File2Read=CyPat.json"
rem This will read a file into an array of variables and populate it 
setlocal EnableExtensions EnableDelayedExpansion
    set /a count = 0
for /f "delims=" %%a in ('Type "%File2Read%"') do (
    set /a count+=1
    set "CyPat[!count!]=%%a"
)
rem Display array elements
For /L %%i in (1,1,%Count%) do (
    echo "CyPat%%i" is assigned to ==^> "!CyPat[%%i]!"
)
REM ==================================Imports Password.json============================================
set "File2Read=Password.json"
rem This will read a file into an array of variables and populate it 
setlocal EnableExtensions EnableDelayedExpansion
    set /a count = 0
set /p Password=<Password.json
rem Display array elements
    echo "Password" is assigned to ==^> "!Password!"

REM ==================================Configures System Network Security Settings===============================================

if %Networking[1]%==true (
echo do your stuff here
)

REM ==================================Configures System Local Security Policy Settings==========================================



REM ==================================Configures Local User Manager Settings====================================================



REM ==================================Configures System Services Security Settings==============================================

if %Services[1]%==true (
echo Bluetooth service stopped and disabled from startup.
sc stop BTAGService
sc stop bthserv
sc config BTAGService start= disabled
sc config bthserv start= disabled
)

REM ==================================Cypat Security Settings===================================================================




pause
