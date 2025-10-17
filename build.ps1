<#
Simple build script for Windows PowerShell.
It compiles Java sources, packages a minimal JAR, and runs the console Main.

Usage:
  .\build.ps1        # compiles, packages, runs
#>
param()

Write-Host "Cleaning..."
Remove-Item -Recurse -Force -ErrorAction SilentlyContinue target
New-Item -ItemType Directory -Path target\classes | Out-Null

Write-Host "Compiling sources (excluding banking.ui)..."
$files = Get-ChildItem -Path .\src\main\java -Recurse -Filter *.java | Where-Object { $_.FullName -notmatch '\\banking\\ui\\' } | ForEach-Object { $_.FullName }
javac --release 21 -d target\classes $files
if ($LASTEXITCODE -ne 0) { Write-Error "Compilation failed"; exit $LASTEXITCODE }

Write-Host "Packaging jar..."
New-Item -ItemType Directory -Path target\lib | Out-Null
$manifest = "Main-Class: banking.Main`r`n"
$manifestPath = Join-Path -Path $PWD -ChildPath "target\classes\META-INF\MANIFEST.MF"
New-Item -ItemType Directory -Path (Split-Path $manifestPath) -Force | Out-Null
[System.IO.File]::WriteAllText($manifestPath,$manifest)
Push-Location target\classes
if (Test-Path ..\banking-system.jar) { Remove-Item ..\banking-system.jar }
Compress-Archive -Path * -DestinationPath ..\banking-system.jar -Force
Pop-Location

Write-Host "Running application..."
java -cp target\banking-system.jar banking.Main

Write-Host "Done. Jar at target\banking-system.jar"
