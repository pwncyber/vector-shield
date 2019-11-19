##############################################################################
#Imports the boolean values as separate array's from the settings.json files.#
##############################################################################
$Networking = Get-Content -Path .\Networking.json
$LocalSecPol = Get-Content -Path .\LocalSecPol.json
$Lusrmgr = Get-Content -Path .\Lusrmgr.json
$Services = Get-Content -Path .\Services.json
$SecPol = Get-Content -Path .\SecPol.json
$CyPat = Get-Content -Path .\CyPat.json
$Password = Get-Content -Path .\Password.json

################################
#General Networking in Windows.#
################################
if($Networking[0]){
#Refreshes DNS of the system.
}
if($Networking[1]){
}

#######################
#Local Security Policy#
#######################
if($LocalSecPol[0]){
}

####################
#Local User Manager#
####################
if($Lusrmgr[0]){
#Disable local Guest Account
#Disable local Administrator account.
}

##################
#Windows Services#
##################
if($Services[20]){
#Disables Remote Desktop services from startup.
}

##############
#Cyberpatriot#
##############
#Changes all user passwords to the one stored in the Password.json file.
if($CyPat[0]){
Get-LocalUser |
    Set-Localuser -Password $Password
}
