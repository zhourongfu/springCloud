node('slave001') {

    stage('更新代码') {
        git 'https://github.com/weilus923/springCloud.git'
    }

    stage('编译') {
        def mvnHome = '${env.JENKINS_HOME}/tools/apache-maven-3.6.1'
        withEnv(["MVN_HOME=$mvnHome"]) {
            sh '"$MVN_HOME/bin/mvn" -Dmaven.test.failure.ignore clean package'
        }
    }

    stage('构建镜像') {
        def pom = readMavenPom file: 'gateway/pom.xml'
        sh "docker build -t weilus.cloud/${pom.artifactId}:${pom.version} ./gateway/"
        //sh "docker push weilus.cloud/${pom.artifactId}:${pom.version}"
    }

    stage('镜像部署') {
        sh "docker stack deploy -c docker-stack.yml cloud --with-registry-auth"
    }

}