pipeline {
    agent any

    stages {
        stage('Checkout Backend') {
            steps {
                script {
                    checkout([$class: 'GitSCM', branches: [[name: 'master']], userRemoteConfigs: [[url: 'https://github.com/oueslatimohamed/devops_back_end.git']]])
                }
            }
        }

        stage('Build Backend') {
            steps {
                sh 'cd backend && mvn clean install'
            }
        }

        stage('Checkout Frontend') {
            steps {
                script {
                    checkout([$class: 'GitSCM', branches: [[name: 'master']], userRemoteConfigs: [[url: 'https://github.com/oueslatimohamed/devops_front_end.git']]])
                }
            }
        }

        stage('Build Frontend') {
            steps {
                sh 'cd frontend && npm install && ng build --prod'
            }
        }
    }
}

