pipeline {
    agent any

    stages {
        stage('Build') { 
            steps { 
                sh 'make' 
            }
        }
        stage('Test'){
            steps {
                sh 'make check'
                junit 'reports/**/*.xml' 
            }
        }
        stage('Release') {
            steps {
                echo 'Hello from release stage'
            }
        }
    }
}
