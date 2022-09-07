pipeline {
    agent any

    tools {
        maven "3.8.6"
        jdk "java 11"
    }

    stages {
        stages {
              stage('Build') {
                  steps {
                      sh 'make'
                      archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
                  }
              }
          }
          stage('Test') {
                      steps {
                          sh 'make check || true'
                          junit '**/target/*.xml'
                      }
                  }

        post {
            always {
                junit(
                    allowEmptyResults: true,
                    testResults: '*/test-reports/.xml'
                )
            }
        }
    }
}