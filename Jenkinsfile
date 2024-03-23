/* Requires the Docker Pipeline plugin */
pipeline {
    agent { docker { image 'maven:3.9.6-eclipse-temurin-17-alpine'
                     args '-p 8080:8080'} }
    stages {
        stage('build') {
            steps {
                script {
                    def app = sh(script: 'mvn spring-boot:run &', returnStatus: true)
                    if (app == 0) {
                        echo 'La aplicación Spring Boot se ha ejecutado correctamente.'
                    } else {
                        error 'La aplicación Spring Boot no se ha podido ejecutar correctamente.'
                    }
                }
            }
        }
    }
}
