pipeline {
    agent any
    environment{
        DOCKER_REGISTER = 'registry.cn-hangzhou.aliyuncs.com'
        REGISTRY_NAMESPACE='my_docker_dev'
        IMAGE_NAME='docker'
    }
    stages{
      // 可以手动构建也可以配置自动构建，手动构建选择
      // stage('Source') {
      //      steps{
      //      git branch: 'main', url: 'https://gitee.com/zhao_weilong/spring-boot-matrix.git'
      //      }
      // }

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
                    sh './gradlew -Pdocker_repo_username=$BITBUCKET_COMMON_CREDS_USR  -Pdocker_repo_password=$BITBUCKET_COMMON_CREDS_PSW'
           }
        }
         stage("docker-run"){
                steps{
                      sh 'docker pull ${DOCKER_REGISTER}/${REGISTRY_NAMESPACE}/${IMAGE_NAME}:latest'
                      sh 'if test ! -z "$(docker ps -aq -f name=${IMAGE_NAME})";then \
                            echo "容器存在,停止并删除容器;" \
                            docker stop ${IMAGE_NAME};\
                            docker rm -f ${IMAGE_NAME};\
                            docker run -d -p 8888:8888 --name ${IMAGE_NAME} ${DOCKER_REGISTER}/${REGISTRY_NAMESPACE}/${IMAGE_NAME}:latest;\
                            fi'
                }
         }

        }
}