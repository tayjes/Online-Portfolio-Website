pipeline {
    agent any
    environment {
        IMAGE   = "portfolio:${env.BUILD_NUMBER}"
        CLUSTER = "dev-cluster"
    }
    stages {
        stage('Checkout') {
            steps { git branch: 'main', url: 'https://github.com/tayjes/Online-Portfolio-Website.git' }
        }
        stage('Maven Build') {
            steps { sh 'chmod +x mvnw && ./mvnw clean package -DskipTests' }
        }
        stage('Docker Build') {
            steps { sh 'docker build -t $IMAGE .' }
        }
        stage('Load Image into kind') {
            steps { sh 'kind load docker-image $IMAGE --name $CLUSTER' }
        }
        stage('Deploy to Kubernetes') {
            steps {
                sh 'kubectl apply -f k8s/'
                sh 'kubectl set image deployment/portfolio portfolio=$IMAGE'
                sh 'kubectl rollout status deployment/portfolio'
            }
        }
    }
}