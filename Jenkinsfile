node{
    // http://192.168.198.128:8081/job/springCloud/build?token=123456789
    stage('更新代码') {
        git 'https://github.com/weilus923/springCloud.git'
    }

    stage('编译代码') {
        def mvnHome = '${env.JENKINS_HOME}/tools/apache-maven-3.6.1'
        withEnv(["MVN_HOME=$mvnHome"]) {
            sh '"$MVN_HOME/bin/mvn" -Dmaven.test.failure.ignore clean package'
        }
    }

    stage('构建镜像') {
        def pom = readMavenPom file: 'gateway/pom.xml'
        build_image = "registry.cn-hangzhou.aliyuncs.com/weilus923/${pom.artifactId}:${pom.version}"
        sh "docker build -t ${build_image} ./gateway/"
        sh "docker push ${build_image}"
    }

    stage('镜像部署') {
        sh "docker stack deploy -c docker-stack.yml cloud --with-registry-auth"
    }

}