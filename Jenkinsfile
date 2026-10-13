
pipeline {
    agent any

    triggers {
        githubPush()
        pollSCM('H/2 * * * *')
    }

    environment {
        IMAGE   = "portfolio-site:${BUILD_NUMBER}"
        CLUSTER = "dev-cluster"
        APP_URL = "http://localhost:8081"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Maven Build') {
            steps {
                sh 'chmod +x mvnw && ./mvnw clean package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t "$IMAGE" .'
            }
        }

        stage('Load Image into Kind') {
            steps {
                sh 'kind load docker-image "$IMAGE" --name "$CLUSTER"'
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh '''
                    set -eu

                    kubectl apply -f k8s/

                    kubectl set image deployment/portfolio-site \
                        portfolio-site="$IMAGE"

                    kubectl rollout status deployment/portfolio-site \
                        --timeout=180s

                    kubectl get pods
                    kubectl get deployment portfolio-site
                '''
            }
        }

        stage('Smoke Test') {
            steps {
                sh '''
                    set -eu
                    curl --fail --silent --show-error \
                        --retry 10 --retry-delay 3 --retry-connrefused \
                        "$APP_URL/" -o /dev/null
                    echo "Portfolio application is responding"
                '''
            }
        }
    }

    post {
        success {
            echo "Deployment successful: ${env.IMAGE}"
            echo "Application URL: ${env.APP_URL}"
        }
        failure {
            echo 'Pipeline failed. Review the failed stage logs.'
        }
    }
}
