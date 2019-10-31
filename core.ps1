#Imports the boolean values as separate array's from the settings.json files.
$Networking = Get-Content -Path .\Networking.json
$LocalSecPol = Get-Content -Path .\LocalSecPol.json
$Lusrmgr = Get-Content -Path .\Lusrmgr.json
$Services = Get-Content -Path .\Services.json
$SecPol = Get-Content -Path .\SecPol.json


#General Networking in Windows.

if($Networking[0]){
#Refreshes DNS of the system.
}
if($Networking[1]){
}
#Local Security Policy
if($LocalSecPol[0]){
#Refreshes DNS of the system.
}
#Local User Manager
if($Lusrmgr[0]){
#Refreshes DNS of the system.
}
#Windows Services
if($Services[0]){
#Refreshes DNS of the system.
}
