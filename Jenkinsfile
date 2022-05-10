final String jenkinsContainerImageBuildArgs =
'--build-arg HOST_UID=$UID --build-arg HOST_GID=$UID --build-arg HOST_TZ=$TZ --build-arg HOST_DOCKER_VERSION=$(docker version -f \'{{.Server.Version}}\') '

pipeline {
    agent {
        dockerfile {
          label 'docker'
          filename 'Jenkins.Dockerfile'
          //  Always attempt to pull a newer version of the base image using '--pull'.
          additionalBuildArgs jenkinsContainerImageBuildArgs + ' --pull'
          // reuseNode true
        }
    }
    stages{
        stage('Checkout'){
            steps{
                step([$class: 'StashNotifier'])
                checkout scm
            }
        }
        stage('Build') {
            steps {
                 sh './gradlew clean build -x test'
            }
        }
        stage('Test') {
            steps {
                 sh './gradlew test'
            }
        }
        stage('Publish-release') {
           steps {
             withCredentials([usernamePassword(credentialsId: 'publisherID',
             usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                   script {
                    sh ' ./gradlew jib -Pdocker_repo_username=$USERNAME  -Pdocker.repo.password=$PASSWORD'
                }
             }
           }
        }
    }
    post {
        always {
            script {
                // In Declarative Pipelines, where Jenkins sets currentBuild.result = null for SUCCESS builds,
                // the current value can be modified via a script.
                currentBuild.result = currentBuild.result ?: 'SUCCESS'
            }
            step([$class: 'StashNotifier'])
        }
    }
}