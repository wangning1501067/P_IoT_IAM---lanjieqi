#!/usr/bin/env bash
source /etc/environment
IAM=iam-$1
BUILD_NU=$3

case "$2" in
    iam)
		docker tag  bys-cd.chinacloudapp.cn/iotplatform/iam   bys-iot.chinanorth2.cloudapp.chinacloudapi.cn/iotplatform/$IAM:$BUILD_NU
        docker push bys-iot.chinanorth2.cloudapp.chinacloudapi.cn/iotplatform/$IAM:$BUILD_NU
        ;;
    *)
        echo -e "Invalid parameters  registry gate crm payment"
esac