#!/usr/bin/env bash
source /etc/environment
IAM=iam-$1
BUILD_NU=$3
case "$2" in
    iam)
	docker stop $IAM
	docker rm   $IAM
	docker run -d --security-opt seccomp:unconfined --restart=always   --name $IAM    -p 8081:8081  --label bys.logs.iam-`echo $HOSTNAME | tr '[A-Z]' '[a-z]'`=stdout --label bys.logs.iam-`echo $HOSTNAME | tr '[A-Z]' '[a-z]'`.format=json \
	-e PARAM="-DP_DB_SERVER_HOST=$P_DB_HOST -DP_DB_USER_NAME=$P_DB_USER -DP_DB_PASSWORD=$P_DB_PSWD -DPREFER_IP_ADDRESS=$PREFER_IP_ADDRESS  -DIP_ADDRESS=$LOCAL_IP  -DREDIS_HOST=$REDIS_HOST -DREDIS_PORT=$REDIS_PORT -DREDIS_PSWD=$REDIS_PSWD -DFASTDFS_SERVER_ADDRESS=$FASTDFS_SERVER_ADDRESS -DTRACKER_1=$TRACKER_1 -DFASTDFS_SERVER_PORT=$FASTDFS_SERVER_PORT -DENV=$ENV" \
	bys-iot.chinanorth2.cloudapp.chinacloudapi.cn/iotplatform/$IAM:$BUILD_NU
        ;;

    *)
        echo -e "Invalid parameters  registry gate crm payment"
esac