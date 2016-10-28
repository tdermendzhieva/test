#!/bin/bash

AWS="/usr/local/bin/aws"

APPLICATION_NAME="allie-data"
APPLICATION_ENV=$1
DEPLOYMENT_GROUP="${APPLICATION_ENV}"
BUCKET_NAME="allstatedeployables"
BUNDLE_NAME=$2

echo "Application Name: ${APPLICATION_NAME}"
echo "Application Environment: ${APPLICATION_ENV}"
echo "Deployment Group: ${DEPLOYMENT_GROUP}"
echo "Bucket Name: ${BUCKET_NAME}"
echo "Bundle Name: ${BUNDLE_NAME}"

$AWS s3api head-object --bucket ${BUCKET_NAME} --key "${APPLICATION_NAME}/${BUNDLE_NAME}"

# Checks whether the distribution bundle exists on the expected S3 bucket
if [ $? -eq 0 ]; then
    # If the bundle exists...
    # Deploys it to the right application/deployment group
    $AWS deploy create-deployment \
        --application-name ${APPLICATION_NAME} \
        --deployment-group-name ${DEPLOYMENT_GROUP} \
        --deployment-config-name CodeDeployDefault.OneAtATime \
        --s3-location bucket="${BUCKET_NAME}",bundleType="zip",key="${APPLICATION_NAME}/${BUNDLE_NAME}" \
        --region us-west-2

else
    # Returns an error message
    echo "Distribution not found!"
    exit 1
fi