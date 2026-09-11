#!/bin/bash

dnf update -y
dnf install -y docker

systemctl enable docker
systemctl start docker

docker pull ghcr.io/sanjanac02/finsight-cloud:main

docker run -d \
  --name finsight-app \
  --restart unless-stopped \
  -p 8080:8080 \
  -e DB_URL="jdbc:mysql://${db_host}:3306/finsight_cloud" \
  -e DB_USERNAME="${db_username}" \
  -e DB_PASSWORD="${db_password}" \
  -e JWT_SECRET="${jwt_secret}" \
  ghcr.io/sanjanac02/finsight-cloud:main