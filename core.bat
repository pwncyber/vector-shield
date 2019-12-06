@echo off

color 0b

echo[

type ASCII.txt

title Vector Shield

echo[

cd /D "%~dp0"

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


if %LocalSecPol[8]%==true (
REG ADD "HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Windows NT\CurrentVersion\Winlogon" /v CachedLogonsCount /t REG_SZ /d 0 /f
)
if %LocalSecPol[9]%==true (
REG ADD "HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Windows NT\CurrentVersion\Winlogon" /v AllocateCDRoms /t REG_SZ /d 1 /f
REG ADD "HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Windows NT\CurrentVersion\Winlogon" /v AllocateFloppies /t REG_SZ /d 1 /f
)
if %LocalSecPol[10]%==true (
REG ADD "HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Windows NT\CurrentVersion\Winlogon" /v ScRemoveOption /t REG_SZ /d 1 /f
)
if %LocalSecPol[11]%==true (
REG ADD "HKEY_LOCAL_MACHINE\Software\Microsoft\Driver Signing" /v Policy /t REG_BINARY /d 02 /f
REG ADD "HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Non-Driver Signing" /v Policy /t REG_BINARY /d 02 /f
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
if not exist %SystemRoot%\script-logs\ (
  mkdir %SystemRoot%\script-logs\
    )
echo (new-object -c "microsoft.update.servicemanager").addservice2("7971f918-a847-4430-9279-4a52d1efe18d",7,"") > %TEMP%\tempscript.ps1
powershell -ExecutionPolicy Unrestricted %TEMP%\tempscript.ps1 >> %SystemRoot%\script-logs\Computer-Turn-On-Application-Updates.log.txt
)

if %LocalSecPol[17]%==true (
REG ADD HKEY_LOCAL_MACHINE\Software\Microsoft\Windows\CurrentVersion\Policies\System /v DisableCAD /t REG_DWORD /d 0 /f
)
if %LocalSecPol[18]%==true (
REG ADD HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Services\LanManServer\Parameters /v RestrictNullSessAccess /t REG_DWORD /d 1 /f
)
if %LocalSecPol[19]%==true (
REG ADD HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System /v EnableUIADesktopToggle /t REG_DWORD /d 0 /f
REG ADD HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System /v PromptOnSecureDesktop /t REG_DWORD /d 1 /f
)
if %LocalSecPol[20]%==true (
REG ADD "HKEY_LOCAL_MACHINE\Software\Microsoft\Windows NT\CurrentVersion\Setup\RecoveryConsole" /v SecurityLevel /t REG_DWORD /d 0 /f
)


if %LocalSecPol[22]%==true (
REG ADD HKEY_LOCAL_MACHINE\System\CurrentControlSet\Services\LanmanWorkstation\Parameters /v EnablePlainTextPassword /t REG_DWORD /d 0 /f
)
if %LocalSecPol[23]%==true (
 REG ADD "HKEY_LOCAL_MACHINE\System\CurrentControlSet\Control\Print\Providers\LanMan Print Services\Servers" /v AddPrinterDrivers /t REG_DWORD /d 1 /f
)
REM ==================================Configures Local User Manager Settings====================================================

if %Lusrmgr[1]%==true (
net user Guest /active no
)
if %Lusrmgr[2]%==true (
net user Administrator /active no
)
REM --Installing Needed Packages to edit User Rights Assighnment--
if not exist C:\WINDOWS\system32\WindowsPowerShell\v1.0\Modules\UserRights mkdir C:\WINDOWS\system32\WindowsPowerShell\v1.0\Modules\UserRights
if not exist C:\WINDOWS\system32\WindowsPowerShell\v1.0\Modules\UserRights\UserRights.psm1 copy /-Y "%~dp0UserRights.psm1" "C:\WINDOWS\system32\WindowsPowerShell\v1.0\Modules\UserRights"
echo Setting User rights(Array:Lusrmgr). This will take awhile. Ignore any errors.
if %Lusrmgr[3]%==true (
Set "MyCmnd=Unblock-File -Path C:\WINDOWS\system32\WindowsPowerShell\v1.0\Modules\UserRights\UserRights.psm1;"
Set "MyCmnd=%MyCmnd% Import-Module  UserRights -Force;"
Set "MyCmnd=%MyCmnd% $Accounts=Get-AccountsWithUserRight -Right SeTrustedCredManAccessPrivilege;"
Set "MyCmnd=%MyCmnd% $Counter = $Counter = $($Accounts | measure).Count;"
Set "MyCmnd=%MyCmnd% For ($i=0; $i -lt $Counter; $i++)  {Revoke-UserRight -Account "$Accounts[$i].SID" -Right SeTrustedCredManAccessPrivilege};"
powershell -ExecutionPolicy Unrestricted -Command "%MyCmnd%"
)

if %Lusrmgr[4]%==true (
Set "MyCmnd=Unblock-File -Path C:\WINDOWS\system32\WindowsPowerShell\v1.0\Modules\UserRights\UserRights.psm1;"
Set "MyCmnd=%MyCmnd% Import-Module  UserRights -Force;"
Set "MyCmnd=%MyCmnd% $Accounts=Get-AccountsWithUserRight -Right SeNetworkLogonRight;"
Set "MyCmnd=%MyCmnd% $Counter = $Counter = $($Accounts | measure).Count;"
Set "MyCmnd=%MyCmnd% For ($i=0; $i -lt $Counter; $i++)  {Revoke-UserRight -Account "$Accounts[$i].SID" -Right SeNetworkLogonRight};"
Set "MyCmnd=%MyCmnd% Grant-UserRight -Account "Administrators" -Right SeNetworkLogonRight;"
Set "MyCmnd=%MyCmnd% Grant-UserRight -Account "S-1-5-32-555" -Right SeNetworkLogonRight;"
powershell -ExecutionPolicy Unrestricted -Command "%MyCmnd%"
)
if %Lusrmgr[5]%==true (
Set "MyCmnd=Unblock-File -Path C:\WINDOWS\system32\WindowsPowerShell\v1.0\Modules\UserRights\UserRights.psm1;"
Set "MyCmnd=%MyCmnd% Import-Module  UserRights -Force;"
Set "MyCmnd=%MyCmnd% $Accounts=Get-AccountsWithUserRight -Right SeTcbPrivilege;"
Set "MyCmnd=%MyCmnd% $Counter = $Counter = $($Accounts | measure).Count;"
Set "MyCmnd=%MyCmnd% For ($i=0; $i -lt $Counter; $i++)  {Revoke-UserRight -Account "$Accounts[$i].SID" -Right SeTcbPrivilege};"
powershell -ExecutionPolicy Unrestricted -Command "%MyCmnd%"
)
if %Lusrmgr[6]%==true (
Set "MyCmnd=Unblock-File -Path C:\WINDOWS\system32\WindowsPowerShell\v1.0\Modules\UserRights\UserRights.psm1;"
Set "MyCmnd=%MyCmnd% Import-Module  UserRights -Force;"
Set "MyCmnd=%MyCmnd% $Accounts=Get-AccountsWithUserRight -Right SeIncreaseQuotaPrivilege;"
Set "MyCmnd=%MyCmnd% $Counter = $Counter = $($Accounts | measure).Count;"
Set "MyCmnd=%MyCmnd% For ($i=0; $i -lt $Counter; $i++)  {Revoke-UserRight -Account "$Accounts[$i].SID" -Right SeIncreaseQuotaPrivilege};"
Set "MyCmnd=%MyCmnd% Grant-UserRight -Account "Administrators" -Right SeIncreaseQuotaPrivilege;"
Set "MyCmnd=%MyCmnd% Grant-UserRight -Account "S-1-5-19" -Right SeIncreaseQuotaPrivilege;"
Set "MyCmnd=%MyCmnd% Grant-UserRight -Account "S-1-5-20" -Right SeIncreaseQuotaPrivilege;"
powershell -ExecutionPolicy Unrestricted -Command "%MyCmnd%"
)
if %Lusrmgr[7]%==true (
Set "MyCmnd=Unblock-File -Path C:\WINDOWS\system32\WindowsPowerShell\v1.0\Modules\UserRights\UserRights.psm1;"
Set "MyCmnd=%MyCmnd% Import-Module  UserRights -Force;"
Set "MyCmnd=%MyCmnd% $Accounts=Get-AccountsWithUserRight -Right SeBackupPrivilege;"
Set "MyCmnd=%MyCmnd% $Counter = $Counter = $($Accounts | measure).Count;"
Set "MyCmnd=%MyCmnd% For ($i=0; $i -lt $Counter; $i++)  {Revoke-UserRight -Account "$Accounts[$i].SID" -Right SeBackupPrivilege};"
Set "MyCmnd=%MyCmnd% Grant-UserRight -Account "Administrators" -Right SeBackupPrivilege;"
powershell -ExecutionPolicy Unrestricted -Command "%MyCmnd%"
)
if %Lusrmgr[8]%==true (
Set "MyCmnd=Unblock-File -Path C:\WINDOWS\system32\WindowsPowerShell\v1.0\Modules\UserRights\UserRights.psm1;"
Set "MyCmnd=%MyCmnd% Import-Module  UserRights -Force;"
Set "MyCmnd=%MyCmnd% $Accounts=Get-AccountsWithUserRight -Right SeTimeZonePrivilege;"
Set "MyCmnd=%MyCmnd% $Counter = $Counter = $($Accounts | measure).Count;"
Set "MyCmnd=%MyCmnd% For ($i=0; $i -lt $Counter; $i++)  {Revoke-UserRight -Account "$Accounts[$i].SID" -Right SeTimeZonePrivilege};"
Set "MyCmnd=%MyCmnd% Grant-UserRight -Account "Administrators" -Right SeTimeZonePrivilege;"
Set "MyCmnd=%MyCmnd% Grant-UserRight -Account "S-1-5-19" -Right SeTimeZonePrivilege;"
Set "MyCmnd=%MyCmnd% Grant-UserRight -Account "Users" -Right SeTimeZonePrivilege;"
powershell -ExecutionPolicy Unrestricted -Command "%MyCmnd%"
)
if %Lusrmgr[9]%==true (
Set "MyCmnd=Unblock-File -Path C:\WINDOWS\system32\WindowsPowerShell\v1.0\Modules\UserRights\UserRights.psm1;"
Set "MyCmnd=%MyCmnd% Import-Module  UserRights -Force;"
Set "MyCmnd=%MyCmnd% $Accounts=Get-AccountsWithUserRight -Right SeCreatePagefilePrivilege;"
Set "MyCmnd=%MyCmnd% $Counter = $Counter = $($Accounts | measure).Count;"
Set "MyCmnd=%MyCmnd% For ($i=0; $i -lt $Counter; $i++)  {Revoke-UserRight -Account "$Accounts[$i].SID" -Right SeCreatePagefilePrivilege};"
Set "MyCmnd=%MyCmnd% Grant-UserRight -Account "Administrators" -Right SeCreatePagefilePrivilege;"

Set "MyCmnd=%MyCmnd% $Accounts=Get-AccountsWithUserRight -Right SeCreateTokenPrivilege;"
Set "MyCmnd=%MyCmnd% $Counter = $Counter = $($Accounts | measure).Count;"
Set "MyCmnd=%MyCmnd% For ($i=0; $i -lt $Counter; $i++)  {Revoke-UserRight -Account "$Accounts[$i].SID" -Right SeCreateTokenPrivilege};"

Set "MyCmnd=%MyCmnd% $Accounts=Get-AccountsWithUserRight -Right SeCreateGlobalPrivilege;"
Set "MyCmnd=%MyCmnd% $Counter = $Counter = $($Accounts | measure).Count;"
Set "MyCmnd=%MyCmnd% For ($i=0; $i -lt $Counter; $i++)  {Revoke-UserRight -Account "$Accounts[$i].SID" -Right SeCreateGlobalPrivilege};"
Set "MyCmnd=%MyCmnd% Grant-UserRight -Account "Administrators" -Right SeCreateGlobalPrivilege;"
Set "MyCmnd=%MyCmnd% Grant-UserRight -Account "S-1-5-19" -Right SeCreateGlobalPrivilege;"
Set "MyCmnd=%MyCmnd% Grant-UserRight -Account "S-1-5-20" -Right SeCreateGlobalPrivilege;"
Set "MyCmnd=%MyCmnd% Grant-UserRight -Account "S-1-5-6" -Right SeCreateGlobalPrivilege;"

Set "MyCmnd=%MyCmnd% $Accounts=Get-AccountsWithUserRight -Right SeCreatePermanentPrivilege;"
Set "MyCmnd=%MyCmnd% $Counter = $Counter = $($Accounts | measure).Count;"
Set "MyCmnd=%MyCmnd% For ($i=0; $i -lt $Counter; $i++)  {Revoke-UserRight -Account "$Accounts[$i].SID" -Right SeCreatePermanentPrivilege};"
powershell -ExecutionPolicy Unrestricted -Command "%MyCmnd%"
)
if %Lusrmgr[10]%==true (
Set "MyCmnd=Unblock-File -Path C:\WINDOWS\system32\WindowsPowerShell\v1.0\Modules\UserRights\UserRights.psm1;"
Set "MyCmnd=%MyCmnd% Import-Module  UserRights -Force;"
Set "MyCmnd=%MyCmnd% $Accounts=Get-AccountsWithUserRight -Right SeCreateSymbolicLinkPrivilege;"
Set "MyCmnd=%MyCmnd% $Counter = $Counter = $($Accounts | measure).Count;"
Set "MyCmnd=%MyCmnd% For ($i=0; $i -lt $Counter; $i++)  {Revoke-UserRight -Account "$Accounts[$i].SID" -Right SeCreateSymbolicLinkPrivilege};"
Set "MyCmnd=%MyCmnd% Grant-UserRight -Account "Administrators" -Right SeCreateSymbolicLinkPrivilege;"
powershell -ExecutionPolicy Unrestricted -Command "%MyCmnd%"
)
if %Lusrmgr[11]%==true (
Set "MyCmnd=Unblock-File -Path C:\WINDOWS\system32\WindowsPowerShell\v1.0\Modules\UserRights\UserRights.psm1;"
Set "MyCmnd=%MyCmnd% Import-Module  UserRights -Force;"
Set "MyCmnd=%MyCmnd% $Accounts=Get-AccountsWithUserRight -Right SeDebugPrivilege;"
Set "MyCmnd=%MyCmnd% $Counter = $Counter = $($Accounts | measure).Count;"
Set "MyCmnd=%MyCmnd% For ($i=0; $i -lt $Counter; $i++)  {Revoke-UserRight -Account "$Accounts[$i].SID" -Right SeDebugPrivilege};"
Set "MyCmnd=%MyCmnd% Grant-UserRight -Account "Administrators" -Right SeDebugPrivilege;"
powershell -ExecutionPolicy Unrestricted -Command "%MyCmnd%"
)
if %Lusrmgr[12]%==true (
Set "MyCmnd=Unblock-File -Path C:\WINDOWS\system32\WindowsPowerShell\v1.0\Modules\UserRights\UserRights.psm1;"
Set "MyCmnd=%MyCmnd% Import-Module  UserRights -Force;"
Set "MyCmnd=%MyCmnd% Grant-UserRight -Account "Guests" -Right SeDenyBatchLogonRight,SeDenyServiceLogonRight,SeDenyInteractiveLogonRight,SeDenyRemoteInteractiveLogonRight;"
powershell -ExecutionPolicy Unrestricted -Command "%MyCmnd%"
)
if %Lusrmgr[13]%==true (
Set "MyCmnd=Unblock-File -Path C:\WINDOWS\system32\WindowsPowerShell\v1.0\Modules\UserRights\UserRights.psm1;"
Set "MyCmnd=%MyCmnd% Import-Module  UserRights -Force;"
Set "MyCmnd=%MyCmnd% $Accounts=Get-AccountsWithUserRight -Right SeBatchLogonRight;"
Set "MyCmnd=%MyCmnd% $Counter = $Counter = $($Accounts | measure).Count;"
Set "MyCmnd=%MyCmnd% For ($i=0; $i -lt $Counter; $i++)  {Revoke-UserRight -Account "$Accounts[$i].SID" -Right SeBatchLogonRight};"
Set "MyCmnd=%MyCmnd% Grant-UserRight -Account "Administrators" -Right SeBatchLogonRight;"
powershell -ExecutionPolicy Unrestricted -Command "%MyCmnd%"
)
if %Lusrmgr[14]%==true (
Set "MyCmnd=Unblock-File -Path C:\WINDOWS\system32\WindowsPowerShell\v1.0\Modules\UserRights\UserRights.psm1;"
Set "MyCmnd=%MyCmnd% Import-Module  UserRights -Force;"
Set "MyCmnd=%MyCmnd% $Accounts=Get-AccountsWithUserRight -Right SeServiceLogonRight;"
Set "MyCmnd=%MyCmnd% $Counter = $Counter = $($Accounts | measure).Count;"
Set "MyCmnd=%MyCmnd% For ($i=0; $i -lt $Counter; $i++)  {Revoke-UserRight -Account "$Accounts[$i].SID" -Right SeServiceLogonRight};"
powershell -ExecutionPolicy Unrestricted -Command "%MyCmnd%"
)
if %Lusrmgr[15]%==true (
Set "MyCmnd=Unblock-File -Path C:\WINDOWS\system32\WindowsPowerShell\v1.0\Modules\UserRights\UserRights.psm1;"
Set "MyCmnd=%MyCmnd% Import-Module  UserRights -Force;"
Set "MyCmnd=%MyCmnd% $Accounts=Get-AccountsWithUserRight -Right SeEnableDelegationPrivilege;"
Set "MyCmnd=%MyCmnd% $Counter = $Counter = $($Accounts | measure).Count;"
Set "MyCmnd=%MyCmnd% For ($i=0; $i -lt $Counter; $i++)  {Revoke-UserRight -Account "$Accounts[$i].SID" -Right SeEnableDelegationPrivilege};"
powershell -ExecutionPolicy Unrestricted -Command "%MyCmnd%"
)
if %Lusrmgr[16]%==true (
Set "MyCmnd=Unblock-File -Path C:\WINDOWS\system32\WindowsPowerShell\v1.0\Modules\UserRights\UserRights.psm1;"
Set "MyCmnd=%MyCmnd% Import-Module  UserRights -Force;"
Set "MyCmnd=%MyCmnd% $Accounts=Get-AccountsWithUserRight -Right SeRemoteShutdownPrivilege;"
Set "MyCmnd=%MyCmnd% $Counter = $Counter = $($Accounts | measure).Count;"
Set "MyCmnd=%MyCmnd% For ($i=0; $i -lt $Counter; $i++)  {Revoke-UserRight -Account "$Accounts[$i].SID" -Right SeRemoteShutdownPrivilege};"
Set "MyCmnd=%MyCmnd% Grant-UserRight -Account "Administrators" -Right SeRemoteShutdownPrivilege;"
powershell -ExecutionPolicy Unrestricted -Command "%MyCmnd%"
)
if %Lusrmgr[17]%==true (
Set "MyCmnd=Unblock-File -Path C:\WINDOWS\system32\WindowsPowerShell\v1.0\Modules\UserRights\UserRights.psm1;"
Set "MyCmnd=%MyCmnd% Import-Module  UserRights -Force;"
Set "MyCmnd=%MyCmnd% $Accounts=Get-AccountsWithUserRight -Right SeAuditPrivilege;"
Set "MyCmnd=%MyCmnd% $Counter = $Counter = $($Accounts | measure).Count;"
Set "MyCmnd=%MyCmnd% For ($i=0; $i -lt $Counter; $i++)  {Revoke-UserRight -Account "$Accounts[$i].SID" -Right SeAuditPrivilege};"
Set "MyCmnd=%MyCmnd% Grant-UserRight -Account "S-1-5-19" -Right SeAuditPrivilege;"
Set "MyCmnd=%MyCmnd% Grant-UserRight -Account "S-1-5-20" -Right SeAuditPrivilege;"
powershell -ExecutionPolicy Unrestricted -Command "%MyCmnd%"
)
if %Lusrmgr[18]%==true (
Set "MyCmnd=Unblock-File -Path C:\WINDOWS\system32\WindowsPowerShell\v1.0\Modules\UserRights\UserRights.psm1;"
Set "MyCmnd=%MyCmnd% Import-Module  UserRights -Force;"
Set "MyCmnd=%MyCmnd% $Accounts=Get-AccountsWithUserRight -Right SeImpersonatePrivilege;"
Set "MyCmnd=%MyCmnd% $Counter = $Counter = $($Accounts | measure).Count;"
Set "MyCmnd=%MyCmnd% For ($i=0; $i -lt $Counter; $i++)  {Revoke-UserRight -Account "$Accounts[$i].SID" -Right SeImpersonatePrivilege};"
Set "MyCmnd=%MyCmnd% Grant-UserRight -Account "Administrators" -Right SeImpersonatePrivilege;"
Set "MyCmnd=%MyCmnd% Grant-UserRight -Account "S-1-5-20" -Right SeImpersonatePrivilege;"
Set "MyCmnd=%MyCmnd% Grant-UserRight -Account "S-1-5-19" -Right SeImpersonatePrivilege;"
Set "MyCmnd=%MyCmnd% Grant-UserRight -Account "S-1-5-6" -Right SeImpersonatePrivilege;"
powershell -ExecutionPolicy Unrestricted -Command "%MyCmnd%"
)
if %Lusrmgr[19]%==true (
Set "MyCmnd=Unblock-File -Path C:\WINDOWS\system32\WindowsPowerShell\v1.0\Modules\UserRights\UserRights.psm1;"
Set "MyCmnd=%MyCmnd% Import-Module  UserRights -Force;"
Set "MyCmnd=%MyCmnd% $Accounts=Get-AccountsWithUserRight -Right SeIncreaseBasePriorityPrivilege;"
Set "MyCmnd=%MyCmnd% $Counter = $Counter = $($Accounts | measure).Count;"
Set "MyCmnd=%MyCmnd% For ($i=0; $i -lt $Counter; $i++)  {Revoke-UserRight -Account "$Accounts[$i].SID" -Right SeIncreaseBasePriorityPrivilege};"
Set "MyCmnd=%MyCmnd% Grant-UserRight -Account "Administrators" -Right SeIncreaseBasePriorityPrivilege;"
Set "MyCmnd=%MyCmnd% Grant-UserRight -Account "S-1-5-90-0" -Right SeIncreaseBasePriorityPrivilege;"
powershell -ExecutionPolicy Unrestricted -Command "%MyCmnd%"
)
if %Lusrmgr[20]%==true (
Set "MyCmnd=Unblock-File -Path C:\WINDOWS\system32\WindowsPowerShell\v1.0\Modules\UserRights\UserRights.psm1;"
Set "MyCmnd=%MyCmnd% Import-Module  UserRights -Force;"
Set "MyCmnd=%MyCmnd% $Accounts=Get-AccountsWithUserRight -Right SeLoadDriverPrivilege;"
Set "MyCmnd=%MyCmnd% $Counter = $Counter = $($Accounts | measure).Count;"
Set "MyCmnd=%MyCmnd% For ($i=0; $i -lt $Counter; $i++)  {Revoke-UserRight -Account "$Accounts[$i].SID" -Right SeLoadDriverPrivilege};"
Set "MyCmnd=%MyCmnd% Grant-UserRight -Account "Administrators" -Right SeLoadDriverPrivilege;"
powershell -ExecutionPolicy Unrestricted -Command "%MyCmnd%"
)
if %Lusrmgr[21]%==true (
Set "MyCmnd=Unblock-File -Path C:\WINDOWS\system32\WindowsPowerShell\v1.0\Modules\UserRights\UserRights.psm1;"
Set "MyCmnd=%MyCmnd% Import-Module  UserRights -Force;"
Set "MyCmnd=%MyCmnd% $Accounts=Get-AccountsWithUserRight -Right SeLockMemoryPrivilege;"
Set "MyCmnd=%MyCmnd% $Counter = $Counter = $($Accounts | measure).Count;"
Set "MyCmnd=%MyCmnd% For ($i=0; $i -lt $Counter; $i++)  {Revoke-UserRight -Account "$Accounts[$i].SID" -Right SeLockMemoryPrivilege};"
powershell -ExecutionPolicy Unrestricted -Command "%MyCmnd%"
)
if %Lusrmgr[22]%==true (
Set "MyCmnd=Unblock-File -Path C:\WINDOWS\system32\WindowsPowerShell\v1.0\Modules\UserRights\UserRights.psm1;"
Set "MyCmnd=%MyCmnd% Import-Module  UserRights -Force;"
Set "MyCmnd=%MyCmnd% $Accounts=Get-AccountsWithUserRight -Right SeRelabelPrivilege;"
Set "MyCmnd=%MyCmnd% $Counter = $Counter = $($Accounts | measure).Count;"
Set "MyCmnd=%MyCmnd% For ($i=0; $i -lt $Counter; $i++)  {Revoke-UserRight -Account "$Accounts[$i].SID" -Right SeRelabelPrivilege};"
powershell -ExecutionPolicy Unrestricted -Command "%MyCmnd%"
)
if %Lusrmgr[23]%==true (
Set "MyCmnd=Unblock-File -Path C:\WINDOWS\system32\WindowsPowerShell\v1.0\Modules\UserRights\UserRights.psm1;"
Set "MyCmnd=%MyCmnd% Import-Module  UserRights -Force;"
Set "MyCmnd=%MyCmnd% $Accounts=Get-AccountsWithUserRight -Right SeSystemEnvironmentPrivilege;"
Set "MyCmnd=%MyCmnd% $Counter = $Counter = $($Accounts | measure).Count;"
Set "MyCmnd=%MyCmnd% For ($i=0; $i -lt $Counter; $i++)  {Revoke-UserRight -Account "$Accounts[$i].SID" -Right SeSystemEnvironmentPrivilege};"
Set "MyCmnd=%MyCmnd% Grant-UserRight -Account "Administrators" -Right SeSystemEnvironmentPrivilege;"
powershell -ExecutionPolicy Unrestricted -Command "%MyCmnd%"
)
if %Lusrmgr[24]%==true (
Set "MyCmnd=Unblock-File -Path C:\WINDOWS\system32\WindowsPowerShell\v1.0\Modules\UserRights\UserRights.psm1;"
Set "MyCmnd=%MyCmnd% Import-Module  UserRights -Force;"
Set "MyCmnd=%MyCmnd% $Accounts=Get-AccountsWithUserRight -Right SeManageVolumePrivilege;"
Set "MyCmnd=%MyCmnd% $Counter = $Counter = $($Accounts | measure).Count;"
Set "MyCmnd=%MyCmnd% For ($i=0; $i -lt $Counter; $i++)  {Revoke-UserRight -Account "$Accounts[$i].SID" -Right SeManageVolumePrivilege};"
Set "MyCmnd=%MyCmnd% Grant-UserRight -Account "Administrators" -Right SeManageVolumePrivilege;"
powershell -ExecutionPolicy Unrestricted -Command "%MyCmnd%"
)
if %Lusrmgr[25]%==true (
Set "MyCmnd=Unblock-File -Path C:\WINDOWS\system32\WindowsPowerShell\v1.0\Modules\UserRights\UserRights.psm1;"
Set "MyCmnd=%MyCmnd% Import-Module  UserRights -Force;"
Set "MyCmnd=%MyCmnd% $Accounts=Get-AccountsWithUserRight -Right SeProfileSingleProcessPrivilege;"
Set "MyCmnd=%MyCmnd% $Counter = $Counter = $($Accounts | measure).Count;"
Set "MyCmnd=%MyCmnd% For ($i=0; $i -lt $Counter; $i++)  {Revoke-UserRight -Account "$Accounts[$i].SID" -Right SeProfileSingleProcessPrivilege};"
Set "MyCmnd=%MyCmnd% Grant-UserRight -Account "Administrators" -Right SeProfileSingleProcessPrivilege;"

Set "MyCmnd=%MyCmnd% $Accounts=Get-AccountsWithUserRight -Right SeSystemProfilePrivilege;"
Set "MyCmnd=%MyCmnd% $Counter = $Counter = $($Accounts | measure).Count;"
Set "MyCmnd=%MyCmnd% For ($i=0; $i -lt $Counter; $i++)  {Revoke-UserRight -Account "$Accounts[$i].SID" -Right SeSystemProfilePrivilege};"
Set "MyCmnd=%MyCmnd% Grant-UserRight -Account "Administrators" -Right SeSystemProfilePrivilege;"
Set "MyCmnd=%MyCmnd% Grant-UserRight -Account "S-1-5-80" -Right SeSystemProfilePrivilege;"
powershell -ExecutionPolicy Unrestricted -Command "%MyCmnd%"
)
if %Lusrmgr[26]%==true (
Set "MyCmnd=Unblock-File -Path C:\WINDOWS\system32\WindowsPowerShell\v1.0\Modules\UserRights\UserRights.psm1;"
Set "MyCmnd=%MyCmnd% Import-Module  UserRights -Force;"
Set "MyCmnd=%MyCmnd% $Accounts=Get-AccountsWithUserRight -Right SeAssignPrimaryTokenPrivilege;"
Set "MyCmnd=%MyCmnd% $Counter = $Counter = $($Accounts | measure).Count;"
Set "MyCmnd=%MyCmnd% For ($i=0; $i -lt $Counter; $i++)  {Revoke-UserRight -Account "$Accounts[$i].SID" -Right SeAssignPrimaryTokenPrivilege};"
Set "MyCmnd=%MyCmnd% Grant-UserRight -Account "S-1-5-19" -Right SeAssignPrimaryTokenPrivilege;"
Set "MyCmnd=%MyCmnd% Grant-UserRight -Account "S-1-5-20" -Right SeAssignPrimaryTokenPrivilege;"
powershell -ExecutionPolicy Unrestricted -Command "%MyCmnd%"
)
if %Lusrmgr[27]%==true (
Set "MyCmnd=Unblock-File -Path C:\WINDOWS\system32\WindowsPowerShell\v1.0\Modules\UserRights\UserRights.psm1;"
Set "MyCmnd=%MyCmnd% Import-Module  UserRights -Force;"
Set "MyCmnd=%MyCmnd% $Accounts=Get-AccountsWithUserRight -Right SeRestorePrivilege;"
Set "MyCmnd=%MyCmnd% $Counter = $Counter = $($Accounts | measure).Count;"
Set "MyCmnd=%MyCmnd% For ($i=0; $i -lt $Counter; $i++)  {Revoke-UserRight -Account "$Accounts[$i].SID" -Right SeRestorePrivilege};"
Set "MyCmnd=%MyCmnd% Grant-UserRight -Account "Administrators" -Right SeRestorePrivilege;"
powershell -ExecutionPolicy Unrestricted -Command "%MyCmnd%"
)
if %Lusrmgr[28]%==true (
Set "MyCmnd=Unblock-File -Path C:\WINDOWS\system32\WindowsPowerShell\v1.0\Modules\UserRights\UserRights.psm1;"
Set "MyCmnd=%MyCmnd% Import-Module  UserRights -Force;"
Set "MyCmnd=%MyCmnd% $Accounts=Get-AccountsWithUserRight -Right SeShutdownPrivilege;"
Set "MyCmnd=%MyCmnd% $Counter = $Counter = $($Accounts | measure).Count;"
Set "MyCmnd=%MyCmnd% For ($i=0; $i -lt $Counter; $i++)  {Revoke-UserRight -Account "$Accounts[$i].SID" -Right SeShutdownPrivilege};"
Set "MyCmnd=%MyCmnd% Grant-UserRight -Account "Administrators" -Right SeShutdownPrivilege;"
Set "MyCmnd=%MyCmnd% Grant-UserRight -Account "Users" -Right SeShutdownPrivilege;"
powershell -ExecutionPolicy Unrestricted -Command "%MyCmnd%"
)
if %Lusrmgr[29]%==true (
Set "MyCmnd=Unblock-File -Path C:\WINDOWS\system32\WindowsPowerShell\v1.0\Modules\UserRights\UserRights.psm1;"
Set "MyCmnd=%MyCmnd% Import-Module  UserRights -Force;"
Set "MyCmnd=%MyCmnd% $Accounts=Get-AccountsWithUserRight -Right SeTakeOwnershipPrivilege;"
Set "MyCmnd=%MyCmnd% $Counter = $Counter = $($Accounts | measure).Count;"
Set "MyCmnd=%MyCmnd% For ($i=0; $i -lt $Counter; $i++)  {Revoke-UserRight -Account "$Accounts[$i].SID" -Right SeTakeOwnershipPrivilege};"
Set "MyCmnd=%MyCmnd% Grant-UserRight -Account "Administrators" -Right SeTakeOwnershipPrivilege;"
powershell -ExecutionPolicy Unrestricted -Command "%MyCmnd%"
)
REM -Uninstalling packages-
echo Lusrmgr finished.
if exist C:\WINDOWS\system32\WindowsPowerShell\v1.0\Modules\UserRights @RD /S /Q "C:\WINDOWS\system32\WindowsPowerShell\v1.0\Modules\UserRights"
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
sc config wuauserv start= auto
reg add "HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Windows\CurrentVersion\WindowsUpdate\Auto Update" /v AUOptions /t REG_DWORD /d 3 /f
)
if %Services[35]%==true (
sc stop Spooler
sc config Spooler start= disabled
)
REM ==================================Cypat Security Settings===================================================================

if %CyPat[1]%==true (
FOR /F %%F IN ('wmic useraccount get name') DO (Echo "%%F" | FIND /I "Name" 1>NUL) || (Echo "%%F" | FIND /I "DefaultAccount" 1>NUL) || (NET USER %%F "!Password!")
)
cd /D "C:\Users"
if %CyPat[2]%==true (
echo Deleting All Files With the .mp3 Extention
del /S /Q *.mp3
)
cd /D "%~dp0"
if %CyPat[4]%==true (
echo Default Administrator Account Renamed
wmic useraccount where name='Administrator' rename 'VS1'
)
if %CyPat[5]%==true (
echo Default Guest Account Renamed
wmic useraccount where name='Guest' rename 'VS2'
)



pause
