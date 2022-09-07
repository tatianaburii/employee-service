pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'make' 
                archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true 
            }
        }
        stage('Test') {
            steps {
                sh 'make check || true' 
                junit '**/target/*.xml' 
            }
        }
        stage('Release') {
            steps {
                echo 'Hello from release stage'
            }
        }
    }
}
