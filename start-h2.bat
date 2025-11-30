@echo off
cd /d "%~dp0"
call mvnw spring-boot:run -Dspring.profiles.active=h2
