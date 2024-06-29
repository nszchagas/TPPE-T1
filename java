#!/bin/bash 
sudo docker run -u "$(id -u):$(id -g)" --rm -v "$PWD":/app --workdir /app  openjdk:11 "$@"
