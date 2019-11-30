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
rem Display array elements, cant due to issue with GUI.
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

if %Networking[10]%==true (
DISM /online /Disable-feature /featurename:TelnetClient
)
if %Networking[11]%==true (
Dism /online /Disable-Feature /FeatureName:TFTP
)
REM ==================================Configures System Local Security Policy Settings==========================================

if %LocalSecPol[1]%==true (
echo Windows Firewall Enabled
netsh advfirewall set allprofiles state on
)
if %LocalSecPol[2]%==true (
echo Auditing Enabled
Auditpol /set /category:"Account Logon" /success:enable /failure:enable
Auditpol /set /category:"Account Management" /success:enable /failure:enable
Auditpol /set /category:"Detailed Tracking" /success:enable /failure:enable
Auditpol /set /category:"DS Access" /success:enable /failure:enable
Auditpol /set /category:"Logon/Logoff" /Success:enable /failure:enable
Auditpol /set /category:"Object Access" /success:enable /failure:enable
Auditpol /set /category:"Policy Change" /success:enable /failure:enable
Auditpol /set /category:"Privilege Use" /success:enable /failure:enable
Auditpol /set /category:"System" /success:enable /failure:enable
)
if %LocalSecPol[3]%==true (
echo Password and Logon Settings Changed
secedit.exe /export /cfg C:\secconfig.cfg
powershell -Command "(gc C:\secconfig.cfg) -replace 'PasswordComplexity = 1', 'PasswordComplexity = 0' | Out-File -encoding ASCII C:\secconfigupdated.cfg"
powershell -Command "(gc C:\secconfig.cfg) -replace 'DontDisplayLastUserName=4,1', 'DontDisplayLastUserName=4,0' | Out-File -encoding ASCII C:\secconfigupdated.cfg"
powershell -Command "(gc C:\secconfig.cfg) -replace 'LimitBlankPasswordUse=4,1', 'LimitBlankPasswordUse=4,0' | Out-File -encoding ASCII C:\secconfigupdated.cfg"
secedit.exe /configure /db %windir%\securitynew.sdb /cfg C:\secconfigupdated.cfg /areas SECURITYPOLICY
del c:\secconfig.cfg
del c:\secconfigupdated.cfg
)
if %LocalSecPol[3]%==true (
echo Default Guest Account Disabled and Renamed
net user guest /active:no
wmic useraccount where name='Guest' rename 'Potato'
)
if %LocalSecPol[3]%==true (
echo Default Administrator Account Disabled and Renamed
net user Administrator /active:no
wmic useraccount where name='Administrator' rename 'Tomato'
)
REM ==================================Configures Local User Manager Settings====================================================

if %Lusrmgr[1]%==true (
net user Guest /active no
)
if %Lusrmgr[2]%==true (
net user Administrator /active no
)
REM ==================================Configures System Services Security Settings==============================================

if %Services[1]%==true (
echo Bluetooth service stopped and disabled from startup.
sc stop BTAGService
sc stop bthserv
sc config BTAGService start= disabled
sc config bthserv start= disabled
)
if %Services[2]%==true (
sc stop MapsBroker
sc config MapsBroker start= disabled
)
if %Services[3]%==true (
sc stop lfsvc
sc config lfsvc start= disabled
)
if %Services[4]%==true (
sc stop IISADMIN
sc config IISADMIN start= disabled
)
if %Services[5]%==true (
sc stop irmon
sc config irmon start= disabled
)
if %Services[6]%==true (
net stop SharedAccess
sc config "SharedAccess" start= disabled
)
if %Services[18]%==true (
net stop RemoteRegistry
sc config "RemoteRegistry" start= disabled
)
if %Services[23]%==true (
net stop SSDPSRV
sc config "SSDPSRV" start= disabled
)
if %Services[24]%==true (
net stop upnphost
sc config "upnphost" start= disabled
)
REM ==================================Cypat Security Settings===================================================================




pause