pipeline {
    agent any
    stages{
        stage('Build') {
            steps {
                 sh './gradlew clean generateProto'
                 sh './gradlew build -x test'
            }
        }
        stage('Publish-release') {
        environment {
                               BITBUCKET_COMMON_CREDS = credentials('publisherID')
                           }
           steps {
                    sh ' ./gradlew jib -Pdocker_repo_username=$BITBUCKET_COMMON_CREDS_USR  -Pdocker.repo.password=$BITBUCKET_COMMON_CREDS_PSW'
           }
           }
           }
}