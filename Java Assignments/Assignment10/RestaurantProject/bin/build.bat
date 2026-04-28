@echo off
REM ─────────────────────────────────────────────────────────────
REM  build.bat  —  compile & run on Windows
REM
REM  Configure JAVAFX_LIB and MYSQL_JAR below before running.
REM ─────────────────────────────────────────────────────────────

set JAVAFX_LIB=C:\Users\owner\Downloads\javafx-sdk-26.0.1\lib
set MYSQL_JAR=lib\mysql-connector-j-9.6.0.jar
set SRC=src
set OUT=bin

if not exist %OUT% mkdir %OUT%
if not exist lib mkdir lib

echo Compiling...
javac ^
  --module-path "%JAVAFX_LIB%" ^
  --add-modules javafx.controls,javafx.fxml ^
  -cp "%MYSQL_JAR%" ^
  -d "%OUT%" ^
  %SRC%\*.java

if %ERRORLEVEL% NEQ 0 (
  echo Compilation failed.
  pause
  exit /b 1
)

echo Running...
java ^
  --module-path "%JAVAFX_LIB%" ^
  --add-modules javafx.controls,javafx.fxml ^
  -cp "%OUT%;%MYSQL_JAR%" ^
  MainApp

pause
