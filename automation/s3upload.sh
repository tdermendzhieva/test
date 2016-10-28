#!/bin/bash

AWS="/usr/local/bin/aws"

APPLICATION_NAME="allie-data"

BUCKET_NAME="allstatedeployables"
FULL_BUCKET="s3://${BUCKET_NAME}/${APPLICATION_NAME}/"
SOURCE_FILE=$1

echo "APPLICATION_NAME: ${APPLICATION_NAME}"
echo "FULL BUCKET: ${FULL_BUCKET}"
echo "SOURCE FILE: ${SOURCE_FILE}"

$AWS s3 cp ${SOURCE_FILE} ${FULL_BUCKET}

if [ $? -eq 0 ]
then 
	echo "Copy successful"
else 
	echo "Copy Failed!"
	exit 1
fi


