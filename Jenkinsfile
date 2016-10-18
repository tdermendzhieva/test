#!groovy

node {
        env.APP_VERSION = "1.0."+env.BUILD_NUMBER
        env.GRADLE_USER_HOME = '~/.gradle'
        env.BUILD
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
              sh './gradlew deployOnTest --debug --stacktrace'
        }
        catch(error){
            err = error
            status = "Failed" 
        }
    finally{
          stage 'Send Status Email'
                    mail body: "Error" + ': ' + err,
                   from: 'nobody@nowhere',
                   replyTo: 'andrew.larsen@concordusa.com',
                   subject: env.BUILD_TAG + ' ' + status,
                   to: 'andrew.larsen@concordusa.com ben.barnard@concordusa.com david.cyr@concordusa.com jacob.headlee@concordusa.com'
        if(err){
            throw err
        }
    }
}