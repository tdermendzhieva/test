#!groovy

node {

		withCredentials([
            [
            $class          : 'UsernamePasswordMultiBinding',
            credentialsId   : 'Artifactory',
            passwordVariable: 'ARTIFACTORY_PASSWORD',
            usernameVariable: 'ARTIFACTORY_USER'
            ]
		]) {

        env.APP_VERSION = "1.0."+env.BUILD_NUMBER
        env.GRADLE_USER_HOME = '~/.gradle'
        env.BUILD
        env.DEPLOYMENT_ENVIRONMENT = '${ENVIRONMENT}'
       def err
       def status = "Success"
       try{
            checkout scm
            stage 'Build'
            sh 'chmod +x gradlew'
              sh './gradlew clean build'
            stage 'Publish Artifact to S3'
              sh './gradlew s3Upload --debug --stacktrace'
            stage 'Publish Artifact to Artifactory'
              sh './gradlew artifactoryPublish --debug --stacktrace'
            stage 'Deploy Dev'
              sh './gradlew deployToAWS --debug --stacktrace'
        }
        catch(error){
            err = error
            status = "Failed" 
        }
    finally{
          def statusMessage = (err) ? "Error : " + err : "Success"
    	  stage 'Send Status Email'
                   mail body: statusMessage + "\r\n\r\n" + env.BUILD_URL,
                   from: 'nobody@nowhere',
                   replyTo: 'andrew.larsen@concordusa.com',
                   subject: env.BUILD_TAG + ' ' + status,
                   to: 'andrew.larsen@concordusa.com ben.barnard@concordusa.com david.cyr@concordusa.com jacob.headlee@concordusa.com'
        if(err){
            throw err
        }
        }
    }
}