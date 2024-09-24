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
                sh './mvnw clean package'
            }
        }
        stage('Deploy to EC2') {
            steps {
                sshagent(['${SSH_CREDENTIALS_ID}']) {
                    sh """
                    scp -o StrictHostKeyChecking=no target/yourapp.jar ec2-user@${EC2_IP}:/home/ec2-user/
                    ssh -o StrictHostKeyChecking=no ec2-user@${EC2_IP} 'nohup java -jar /home/ec2-user/yourapp.jar &'
                    """
                }
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}
