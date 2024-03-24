/* Requires the Docker Pipeline plugin */
pipeline {
    agent { docker { image 'maven:3.9.6-eclipse-temurin-17-alpine'
                     args '-p 8080:8080'} }
    stages {
        stage('build') {
            steps {
                sh 'mvn spring-boot:run'
            }
        }
    }
}
