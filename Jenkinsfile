pipeline {
    agent any
    options {
        ansiColor('xterm')
        timestamps()
        disableConcurrentBuilds()
        buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '5'))
    }
    stages {
        stage('Test') {
            steps {
                sh "./gradlew clean test check pitest"
            }

            post {
                success {
                    junit 'build/test-results/test/*.xml'
                    jacoco()
                    recordIssues enabledForFailure: true, tool: pmdParser(pattern: 'build/reports/pmd/*.xml')
                    recordIssues(tools: [pit(pattern: 'build/reports/pitest/*.xml')])
                }
            }
        }

        stage('Build') {
            steps {
                sh "./gradlew assemble"
            }
            post {
                success {
                    archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true, followSymlinks: false
                }
            }
        }

        stage('SonarQube analysis') {
            withSonarQubeEnv(credentialsId: 'adc7cf79-f4fb-4fe4-a7ae-f4f53cac631a') { // Will pick the global server connection you have configured
                sh './gradlew sonarqube'
            }
        }

        stage('Publish') {
            steps {
                sshagent(['ssh-gitkey']) {
                    sh 'git tag BUILD-1.0.${BUILD_NUMBER}'
                    sh 'git push --tags'
                }
            }
        }
    }
}