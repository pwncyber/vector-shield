@echo off

color 0a

type D:\Zer0day_script\ASCII.txt

echo You are about to run the Windows 10 zer0day script, continue?

pause

REM This deactivates the default Administrator account and Guest account.

net user Administrator /active no

net user Guest /active no

pause

REM Disabling the telnet windows feature.

DISM /online /disable-feature /featurename:TelnetClient

echo Telnet successfully disabled.

pause

echo Deactivation completed -> Disabling services.

echo Starting to disable windows services, make sure to exclude critical services listed on readme.

pause

REM Starting to disable common windows services

REM Turning off remote desktop.

net stop TermService

sc config "TermService" start= disabled

REM Remote Registry

net stop RemoteRegistry

sc config "RemoteRegistry" start= disabled

REM SSDP Discovery

net stop SSDPSRV

sc config "SSDPSRV" start= disabled

REM Disabling Universal Plug And Play

net stop upnphost

sc config "upnphost" start= disabled

REM Internet Connection Sharing

net stop SharedAccess

sc config "SharedAccess" start= disabled

echo All services successfully disabled.

pause

REM Changing all user passwords. 
