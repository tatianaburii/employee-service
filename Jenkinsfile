pipeline {
    agent any
    tools {
        maven "3.8.6"
        jdk "java 11"
    }
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
    }
}