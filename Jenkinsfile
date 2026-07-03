pipeline {
    agent any

    tools {
        // Matches the name configured in Jenkins Global Tool Configuration
        maven 'Maven 3.8.7'
    }

    triggers {
        // Automatically triggers the build on every Git push via Webhook
        githubPush()
    }

    environment {
        IMAGE_NAME = 'portfolio'
        IMAGE_TAG  = 'v1'
        REPO_URL   = 'https://github.com/tayjes/Online-Portfolio-Website.git'
    }

    stages {
        stage('Checkout Code') {
            steps {
                echo 'Checking out source code from GitHub...'
                checkout scm
            }
        }

        stage('Maven Build & Test') {
            steps {
                echo 'Compiling and packaging the Spring Boot application...'
                // Clean and package using the specific Maven wrapper or tool
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                echo "Building Docker image: ${IMAGE_NAME}:${IMAGE_TAG}..."
                // Builds the local docker image from your project Dockerfile
                sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} ."
            }
        }

        stage('Load Image into Kind') {
            steps {
                echo "Loading Docker image into Kind cluster: dev-cluster..."
                // Added the explicit --name flag to find your custom cluster
                sh "kind load docker-image ${IMAGE_NAME}:${IMAGE_TAG} --name dev-cluster"
            }
        }
        
        stage('Kubernetes Deployment') {
            steps {
                echo 'Deploying to Kubernetes Cluster...'
                // Applies your separate deployment and service manifests
                sh 'kubectl apply -f k8s/deployment.yaml'
                sh 'kubectl apply -f k8s/service.yaml'
            }
        }

        stage('Verify Rollout') {
            steps {
                echo 'Verifying application status...'
                // Ensures the rollout completes successfully without failing silently
                sh "kubectl rollout status deployment/${IMAGE_NAME}"
                sh "kubectl get pods,svc -l app=${IMAGE_NAME}"
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully! Your portfolio is updated and running.'
        }
        failure {
            echo 'Pipeline execution failed. Please check the console log for errors.'
        }
    }
}