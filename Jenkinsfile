node{
    //curl -X POST http://user:password@192.168.198.128:8081/job/springCloud/build?token=TOKEN

    stage('更新代码') {
        checkout scm
    }

    stage('test'){
        echo '测试打印参数'$pkg
    }
//    def pkg = 'gateway'
//

//
//    stage('编译代码') {
//        withEnv(["MVN_HOME=/var/jenkins_home/tools/apache-maven-3.6.1"]) {
//            sh '"$MVN_HOME/bin/mvn" -Dmaven.test.failure.ignore clean package'
//        }
//    }
//
//    stage('构建镜像') {
//        // 安装Pipeline Utility Steps插件
//        def pom = readMavenPom file: '${pkg}/pom.xml'
//        dir (${pkg}) {
//            def customImage = docker.build("registry.cn-hangzhou.aliyuncs.com/weilus923/${pom.artifactId}:${pom.version}")
//            docker.withRegistry('https://registry.cn-hangzhou.aliyuncs.com/', 'registry.cn-hangzhou.aliyuncs.com') {
//                customImage.push()
//            }
//        }
//    }
//
//    stage('镜像部署') {
//        //sh "docker-compose up -d"
//        //sh "docker stack deploy -c docker-stack.yml cloud --with-registry-auth"
//    }

}
