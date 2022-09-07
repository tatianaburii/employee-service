pipeline {
    agent any

    tools {
        maven "3.8.6"
    }

    stages {
        stage('Build') {
            steps {
             
                git 'https://github.com/tatianaburii/employee-service.git'

            
                sh "mvn -Dmaven.test.failure.ignore=true clean package"
            }

            post {
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
    }
}
