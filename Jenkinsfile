pipeline {
    agent any

    parameters {
        gitParameter (  branch: '', 
                        branchFilter: 'origin/(.*)', 
                        defaultValue: 'master', 
                        description: '', 
                        name: 'BRANCH', 
                        quickFilterEnabled: true, 
                        selectedValue: 'TOP', 
                        sortMode: 'DESCENDING', 
                        tagFilter: '*', 
                        type: 'PT_BRANCH', 
                        useRepository: 'https://github.com/tatianaburii/employee-service.git')
    }

    stages {
        stage('Delete workspace before build starts') {
            steps {
                echo 'Deleting workspace'
                deleteDir()
            }
        }
        stage('Env print') {
            steps {
                sh '''
                    echo $BRANCH
                '''
            }
        }
        stage('Checkout') {
            steps{
                    git branch: "${params.BRANCH}", url: 'https://github.com/tatianaburii/employee-service.git'      
                }
        }
        stage('Build') {
            steps {
                sh "mvn clean install"
            }
        }

    }
}
