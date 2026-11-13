@echo off
rem Build and start the server. Usage: run-server.bat [port]
cd /d "%~dp0"
call "%~dp0build.bat" || exit /b 1
java -cp bin tetris.server.ServerMain %*
