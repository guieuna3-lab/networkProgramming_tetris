@echo off
rem Compile all sources into the bin folder.
cd /d "%~dp0"
if not exist bin mkdir bin
javac -encoding UTF-8 -d bin -sourcepath src src\tetris\server\ServerMain.java src\tetris\client\ClientMain.java
if errorlevel 1 (
    echo Build failed.
    exit /b 1
)
echo Build succeeded.
