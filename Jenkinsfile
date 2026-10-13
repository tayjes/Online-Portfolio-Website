pipeline {
    agent any

    // Auto-trigger: GitHub webhook (instant) with polling as a fallback
    triggers {
        githubPush()
        pollSCM('H/2 * * * *')
    }

    environment {
        IMAGE      = "portfolio-site:${env.BUILD_NUMBER}"
        CLUSTER    = "dev-cluster"
        SVC        = "portfolio-site-service"
        HOST_PORT  = "8081"
        // Uncomment if Jenkins runs as a different user than the one who owns the kind cluster:
        // KUBECONFIG = "/home/tayjes/.kube/config"
    }

    stages {
        stage('Checkout') {
            steps { checkout scm }
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
                sh 'kubectl set image deployment/portfolio-site portfolio-site=$IMAGE'
                sh 'kubectl rollout status deployment/portfolio-site --timeout=120s'
            }
        }
        stage('Expose Port') {
            steps {
                // Kill the old tunnel (still bound to a terminated pod), then start a fresh one
                // that survives after the build finishes.
                sh '''
                    pkill -f "[k]ubectl port-forward svc/$SVC" || true
                    sleep 1
                    JENKINS_NODE_COOKIE=dontKillMe BUILD_ID=dontKillMe \
                    setsid nohup kubectl port-forward svc/$SVC $HOST_PORT:80 --address 0.0.0.0 \
                        > /tmp/portfolio-port-forward.log 2>&1 &
                    sleep 4
                    curl -sf http://localhost:$HOST_PORT/ > /dev/null && echo "App reachable on port $HOST_PORT"
                '''
            }
        }
    }

    post {
        success { echo "Deployed ${env.IMAGE} -> http://localhost:${env.HOST_PORT}" }
        failure { echo "Pipeline failed - check the stage logs" }
    }
}