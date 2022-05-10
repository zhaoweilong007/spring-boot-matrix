pipeline {
    agent any
    stages{
        stage('Build') {
            steps {
                 sh 'sudo ./gradlew clean generateProto'
                 sh 'sudo ./gradlew build -x test'
            }
        }
        stage('Publish-release') {
        environment {
                               BITBUCKET_COMMON_CREDS = credentials('publisherID')
                           }
           steps {
                    sh 'sudo ./gradlew jib -Pdocker_repo_username=$BITBUCKET_COMMON_CREDS_USR  -Pdocker.repo.password=$BITBUCKET_COMMON_CREDS_PSW'
           }
           }
           }
}