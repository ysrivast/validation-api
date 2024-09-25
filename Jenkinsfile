pipeline {
    agent any
    environment {
        EC2_IP = 'your_ec2_instance_ip'
        SSH_CREDENTIALS_ID = 'your_ssh_credentials_id'
    }
    stages {
        stage('Clone Repository') {
            steps {
                git branch: 'master', url: 'https://github.com/ysrivast/validation-api.git'
            }
        }
        stage('Build') {
            steps {
                sh "chmod +x -R ${env.WORKSPACE}"
                sh './mvnw clean package'
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}
