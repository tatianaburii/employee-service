pipeline {
    agent any

    stages {
      stage('Build') {
            steps {
                sh 'javac EmployeeServiceApplication.java'
            }
        }
        stage('Run') {
            steps {
                sh 'java EmployeeServiceApplication'
            }
        }
//         stage('Test'){
//             steps {
   
//                 junit 'reports/**/*.xml' 
//             }
//         }
        stage('Release') {
            steps {
                echo 'Hello from release stage'
            }
        }
    }
}
