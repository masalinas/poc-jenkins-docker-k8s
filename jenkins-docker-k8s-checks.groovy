pipeline {
    agent any  // Run on any available agent

    stages {
        stage('Get Docker Version') {
            steps {
                sh '''
                    docker --version
                '''
            }
        }
        
        stage('Get Docker Running Containers') {
            steps {
                sh '''
                    docker ps
                '''
            }
        }
        
        stage('Get Kubernetes Version') {
            steps {
                sh '''
                    kubectl version
                '''
            }
        }        
        
        stage('Get Kubernetes Namespaces') {
            steps {
                sh '''
                    kubectl get namespaces
                '''
            }
        }
        
        stage('Get Helm Version') {
            steps {
                sh '''
                    helm version
                '''
            }
        }
        
        stage('Get Helm Releases') {
            steps {
                sh '''
                    helm list
                '''
            }
        }        
    }
}