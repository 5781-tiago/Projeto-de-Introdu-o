@echo off
REM Copia de seguranca da base de dados do Portal ECL (Windows / XAMPP)
REM Uso: backup.bat   (pede a password do root do MySQL)

set MYSQL_BIN=C:\xampp\mysql\bin
set DESTINO=%~dp0..\backups
if not exist "%DESTINO%" mkdir "%DESTINO%"

for /f %%i in ('powershell -NoProfile -Command "Get-Date -Format yyyy-MM-dd_HHmm"') do set DATA=%%i

"%MYSQL_BIN%\mysqldump.exe" -u root -p --single-transaction --databases ecl > "%DESTINO%\ecl_%DATA%.sql"

echo Copia criada: %DESTINO%\ecl_%DATA%.sql
echo Para repor: "%MYSQL_BIN%\mysql.exe" -u root -p ^< "%DESTINO%\ecl_%DATA%.sql"
pause
