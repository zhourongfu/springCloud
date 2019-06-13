node('slave001') {

    stage('更新代码') {
        git pull 'https://github.com/weilus923/springCloud.git'
    }
    stage('test'){
        pom = readMavenPom file: 'pom.xml'
        docker_host = "docker.ryan-miao.com"
        img_name = "${pom.groupId}-${pom.artifactId}"
        docker_img_name = "${docker_host}/${img_name}"
        echo "group: ${pom.groupId}, artifactId: ${pom.artifactId}, version: ${pom.version}"
        echo "docker-img-name: ${docker_img_name}"
    }
    stage('编译') {
        def mvnHome = '${env.JENKINS_HOME}/tools/apache-maven-3.6.1'
        withEnv(["MVN_HOME=$mvnHome"]) {
            if (isUnix()) {
                sh '"$MVN_HOME/bin/mvn" -Dmaven.test.failure.ignore clean package'
            } else {
                bat(/"%MVN_HOME%\bin\mvn" -Dmaven.test.failure.ignore clean package/)
            }
        }
    }

    stage('构建镜像') {
        sh "docker build -t weilus.cloud/${pom.artifactId}:${pom.version} ./gateway/"
    }

}