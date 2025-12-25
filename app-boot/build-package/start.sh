#!/bin/bash
cd /Users/premkumar/Desktop/handmade/app-boot/build-package

export APP_NAME=handmade-platform
export SPRING_PROFILES_ACTIVE=local

echo "Starting Handmade Platform with H2 database..."
mvn spring-boot:run
