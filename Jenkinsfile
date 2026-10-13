
pipeline {
    agent any

    triggers {
        githubPush()
        pollSCM('* * * * *')
    }

    options {
        disableConcurrentBuilds()
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

                    # Actual deployment and container names are "portfolio"
                    kubectl set image deployment/portfolio \
                        portfolio="$IMAGE"

                    kubectl rollout status deployment/portfolio \
                        --timeout=180s

                    kubectl get deployment portfolio
                    kubectl get pods -o wide
                    kubectl get endpoints portfolio-service
                '''
            }
        }

        
    stage('Smoke Test') {
        steps {
            sh '''
            set -e
            echo "Checking portfolio application..."
            curl --fail --silent --show-error http://localhost:8081/ -o /dev/null
            echo "Smoke test passed!"
        '''
    }
}
    }

    post {
        success {
            echo "Deployment successful: ${env.IMAGE}"
            echo "Application: ${env.APP_URL}"
        }
        failure {
            echo 'Pipeline failed. Review the failed stage logs.'
        }
    }
}
