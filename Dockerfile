FROM ubuntu:latest
LABEL authors="idevi"

ENTRYPOINT ["top", "-b"]