@echo off
rem Build and start a client. Usage: run-client.bat [host] [port]
cd /d "%~dp0"
call "%~dp0build.bat" || exit /b 1
java -cp bin tetris.client.ClientMain %*
