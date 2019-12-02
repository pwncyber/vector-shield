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
if %Networking[1]%==true (
REG ADD HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Control\Lsa /v UseMachineId /t REG_DWORD /d 1 /f
)
if %Networking[2]%==true (
REG ADD HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Control\Lsa\MSV1_0 /v AllowNullSessionFallback /t REG_DWORD /d 0 /f
)
if %Networking[3]%==true (
REG ADD HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Control\Lsa\pku2u /v AllowOnlineID /t REG_DWORD /d 0 /f
)
if %Networking[4]%==true (
REG ADD HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System\Kerberos\Parameters /v SupportedEncryptionTypes /t REG_DWORD /d 2147483640 /f
)
if %Networking[5]%==true (
REG ADD HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Control\Lsa /v NoLMHash /t REG_DWORD /d 1 /f
)
if %Networking[7]%==true (
REG ADD HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Control\Lsa /v LmCompatibilityLevel /t REG_DWORD /d 5 /f
)
if %Networking[8]%==true (
REG ADD HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Services\LDAP /v LDAPClientIntegrity /t REG_DWORD /d 1 /f
)
if %Networking[9]%==true (
REG ADD HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Control\Lsa\MSV1_0 /v NtlmMinClientSec /t REG_DWORD /d 536870912 /f
REG ADD HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Control\Lsa\MSV1_0 /v NtlmMinServerSec /t REG_DWORD /d 536870912 /f
)
if %Networking[10]%==true (
DISM /online /Disable-feature /featurename:TelnetClient
)
if %Networking[11]%==true (
DISM /online /Disable-Feature /FeatureName:TFTP
)
if  %Networking[12]%==true (
sc stop Fax
sc config Fax start= disabled
)
if  %Networking[13]%==true (
REG ADD HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Services\Tcpip6\Parameters /v DisabledComponents /t REG_DWORD /d 0xff /f
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
echo Block All Microsoft Accounts
REG ADD HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System /v NoConnectedUser /t REG_DWORD /d 3 /f
)
if %LocalSecPol[4]%==true (
REG ADD HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Control\Lsa /v RestrictAnonymous /t REG_DWORD /d 1 /f
)
if %LocalSecPol[5]%==true (
REG ADD HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Services\LanManServer\Parameters /v requiresecuritysignature /t REG_DWORD /d 1 /f
)
if %LocalSecPol[6]%==true (
echo Do Not Display Last Username At Logon Screen Enabled
secedit.exe /export /cfg C:\secconfig.cfg
powershell -Command "(gc C:\secconfig.cfg) -replace 'DontDisplayLastUserName=4,0', 'DontDisplayLastUserName=4,1' | Out-File -encoding ASCII C:\secconfigupdated.cfg"
secedit.exe /configure /db %windir%\securitynew.sdb /cfg C:\secconfigupdated.cfg /areas SECURITYPOLICY
del c:\secconfig.cfg
del c:\secconfigupdated.cfg
)
if %LocalSecPol[7]%==true (
REG ADD HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Control\Lsa /v LmCompatibilityLevel /t REG_DWORD /d 1 /f
)
if %LocalSecPol[8]%==true (
REG ADD "HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Windows NT\CurrentVersion\Winlogon" /v CachedLogonsCount /t REG_SZ /d 0 /f
)
if %LocalSecPol[10]%==true (
REG ADD "HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Windows NT\CurrentVersion\Winlogon" /v ScRemoveOption /t REG_SZ /d 1 /f
)
if %LocalSecPol[12]%==true (
REG ADD "HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Control\Session Manager\Memory Management" /v ClearPageFileAtShutdown /t REG_DWORD /d 1 /f
)
if %LocalSecPol[13]%==true (
REG ADD "HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Control\Terminal Server\WinStations\RDP-Tcp" /v UserAuthentication /t REG_DWORD /d 1 /f
)
if %LocalSecPol[14]%==true (
echo Limit Local Use of Blank Passwords to Console Only
secedit.exe /export /cfg C:\secconfig.cfg
powershell -Command "(gc C:\secconfig.cfg) -replace 'LimitBlankPasswordUse=4,0', 'LimitBlankPasswordUse=4,1' | Out-File -encoding ASCII C:\secconfigupdated.cfg"
secedit.exe /configure /db %windir%\securitynew.sdb /cfg C:\secconfigupdated.cfg /areas SECURITYPOLICY
del c:\secconfig.cfg
del c:\secconfigupdated.cfg
)
if %LocalSecPol[15]%==true (
REG ADD HKEY_LOCAL_MACHINE\Software\Policies\Microsoft\Windows\WindowsUpdate /v AllowMUUpdateService /t REG_DWORD /d 1 /f
)
if %LocalSecPol[19]%==true (
REG ADD HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies /v EnableUIADesktopToggle /t REG_DWORD /d 0 /f
REG ADD HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System /v PromptOnSecureDesktop /t REG_DWORD /d 1 /f
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
sc stop SharedAccess
sc config "SharedAccess" start= disabled
)
if %Services[7]%==true (
sc stop lltdsvc
sc config lltdsvc start= disabled
)
if %Services[8]%==true (
sc stop LxssManager
sc config LxssManager start= disabled
)
if %Services[9]%==true (
sc stop FTPSVC
sc config FTPSVC start= disabled
)
if %Services[10]%==true (
sc stop MSiSCSI
sc config MSiSCSI start= disabled
)
if %Services[11]%==true (
sc stop InstallService
sc config InstallService start= disabled
)
if %Services[12]%==true (
sc stop sshd
sc config sshd start= disabled
)
if %Services[13]%==true (
sc stop PNRPsvc
sc config PNRPsvc start= disabled
sc stop p2psvc
sc config p2psvc start= disabled
sc stop p2pimsvc
sc config p2pimsvc start= disabled
sc stop PNRPAutoReg
sc config PNRPAutoReg start= disabled
)
if %Services[14]%==true (
sc stop wercplsupport
sc config wercplsupport start= disabled
)
if %Services[15]%==true (
sc stop RasAuto
sc config RasAuto start= disabled
)
if %Services[16]%==true (
sc stop SessionEnv
sc config SessionEnv start= disabled
sc stop TermService
sc config TermService start= disabled
sc stop UmRdpService
sc config UmRdpService start= disabled
)
if %Services[17]%==true (
sc stop RpcLocator
sc config RpcLocator start= disabled
)
if %Services[18]%==true (
sc stop RemoteRegistry
sc config "RemoteRegistry" start= disabled
)
if %Services[19]%==true (
sc stop RemoteAccess
sc config RemoteAccess start= disabled
)
if %Services[20]%==true (
sc stop LanmanServer
sc config LanmanServer start= disabled
)
if %Services[21]%==true (
sc stop simptcp
sc config simptcp start= disabled
)
if %Services[22]%==true (
sc stop SNMP
sc config SNMP start= disabled
)
if %Services[23]%==true (
sc stop SSDPSRV
sc config "SSDPSRV" start= disabled
)
if %Services[24]%==true (
sc stop upnphost
sc config "upnphost" start= disabled
)
if %Services[25]%==true (
sc stop WMSvc
sc config WMSvc start= disabled
)
if %Services[26]%==true (
sc stop WerSvc
sc config WerSvc start= disabled
)
if %Services[27]%==true (
sc stop Wecsvc
sc config Wecsvc start= disabled
)
if %Services[28]%==true (
sc stop WMPNetworkSvc
sc config WMPNetworkSvc start= disabled
)
if %Services[29]%==true (
sc stop icssvc
sc config icssvc start= disabled
)
if %Services[30]%==true (
sc stop WpnService
sc config WpnService start= disabled
)
if %Services[31]%==true (
sc stop PushToInstall
sc config PushToInstall start= disabled
)
if %Services[32]%==true (
sc stop WinRM
sc config WinRM start= disabled
)
if %Services[33]%==true (
sc stop XboxGipSvc
sc config XboxGipSvc start= disabled
sc stop XblAuthManager
sc config XblAuthManager start= disabled
sc stop XblGameSave
sc config XblGameSave start= disabled
sc stop XboxNetApiSvc
sc config XboxNetApiSvc start= disabled
)
if %Services[34]%==true (
REG ADD HKEY_LOCAL_MACHINE\SOFTWARE\Policies\Microsoft\Windows\WindowsUpdate\AU /v NoAutoUpdate /t REG_DWORD /d 0 /f
REG ADD	HKEY_LOCAL_MACHINE\SOFTWARE\Motive\M-Files\Common\Updates /v Enabled /t REG_DWORD /d 1 /f
)
if %Services[35]%==true (
sc stop Spooler
sc config Spooler start= disabled
)
REM ==================================Cypat Security Settings===================================================================

if %CyPat[2]%==true (
echo Deleting All Files With the .mp3 Extention
del /S /Q *.mp3
)
if %CyPat[4]%==true (
echo Default Administrator Account Renamed
wmic useraccount where name='Administrator' rename 'VS1'
)
if %CyPat[5]%==true (
echo Default Guest Account Renamed
wmic useraccount where name='Guest' rename 'VS2'
)



pause
