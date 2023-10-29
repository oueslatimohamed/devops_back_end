pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build Backend') {
            steps {
                sh 'mvn package'
            }
        }
        stage('Build Frontend') {
            steps {
                sh 'npm install'
                sh 'ng build'
            }
        }
    }
}
