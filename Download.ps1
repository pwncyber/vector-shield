#Requires -RunAsAdministrator

$web = Invoke-WebRequest https://www.microsoft.com/en-us/download/confirmation.aspx?id=45520

$MachineOS= (Get-WmiObject Win32_OperatingSystem).Name

#Check for Windows Server 2012 R2
IF($MachineOS -like "*Microsoft Windows Server*") {
    
    Add-WindowsFeature RSAT-AD-PowerShell
    Break}

        IF ($ENV:PROCESSOR_ARCHITECTURE -eq "AMD64"){
            Write-host "x64 Detected" -foregroundcolor yellow
            $Link=(($web.AllElements |where class -eq "multifile-failover-url").innerhtml[0].split(" ")|select-string href).tostring().replace("href=","").trim('"')
            }ELSE{
            Write-host "x86 Detected" -forgroundcolor yellow
            $Link=(($web.AllElements |where class -eq "multifile-failover-url").innerhtml[1].split(" ")|select-string href).tostring().replace("href=","").trim('"')
            }

$DLPath= ($ENV:USERPROFILE) + "\Downloads\" + ($link.split("/")[8])

Write-Host "Downloading RSAT MSU file" -foregroundcolor yellow
Start-BitsTransfer -Source $Link -Destination $DLPath

$Authenticatefile=Get-AuthenticodeSignature $DLPath


$WusaArguments = $DLPath + " /quiet"
if($Authenticatefile.status -ne "valid") {write-host "Can't confirm download, exiting";break}
Write-host "Installing RSAT for Windows 10 - please wait" -foregroundcolor yellow
Start-Process -FilePath "C:\Windows\System32\wusa.exe" -ArgumentList $WusaArguments -Wait