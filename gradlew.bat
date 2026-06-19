@echo off
SET DIRNAME=%~dp0
SET GRADLE_HOME=%DIRNAME%\.gradle-wrapper
SET GRADLE_VERSION=8.0.2
IF NOT EXIST "%GRADLE_HOME%\gradle-%GRADLE_VERSION%" (
  echo Downloading Gradle %GRADLE_VERSION%...
  powershell -Command "(New-Object System.Net.WebClient).DownloadFile('https://services.gradle.org/distributions/gradle-%GRADLE_VERSION%-bin.zip', '%GRADLE_HOME%\gradle-%GRADLE_VERSION%.zip')"
  powershell -Command "Expand-Archive -Path '%GRADLE_HOME%\gradle-%GRADLE_VERSION%.zip' -DestinationPath '%GRADLE_HOME%'"
  del "%GRADLE_HOME%\gradle-%GRADLE_VERSION%.zip"
)
"%GRADLE_HOME%\gradle-%GRADLE_VERSION%\bin\gradle" %*
