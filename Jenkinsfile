pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Unit Tests (Backend)') {
            steps {
                sh 'mvn test'
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
