@echo off
setlocal enabledelayedexpansion
rem Compile every .java file under src into the bin folder.
rem Usage: build.bat
cd /d "%~dp0"

where javac >nul 2>nul
if errorlevel 1 (
    echo javac not found. Install JDK 17 or later and add it to PATH.
    exit /b 1
)

if not exist bin mkdir bin

rem Write the source list with paths relative to the project root,
rem so the build does not depend on where the project folder is located.
set "ROOT=%CD%\"
> bin\sources.txt (
    for /r src %%f in (*.java) do (
        set "FILE=%%f"
        set "FILE=!FILE:%ROOT%=!"
        echo !FILE:\=/!
    )
)

rem --release 17 keeps the code compatible with the minimum JDK the team agreed on.
javac --release 17 -encoding UTF-8 -d bin @bin\sources.txt
if errorlevel 1 (
    echo Build failed.
    exit /b 1
)
echo Build succeeded.
