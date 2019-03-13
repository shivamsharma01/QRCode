pipeline {
  agent {
    node {
      label 'Jenkins_Master'
    }
  }
 
  triggers {
    GenericTrigger(
      genericVariables: [
        [key: 'ref', value: '$.ref'],
        [key: 'projectname', value: '$.project.name'],
        [key: 'triggeredby', value: '$.user_username'],
        [key: 'latestcommitid', value: '$.after']
      ],
      causeString: 'Triggered on $projectname - $ref - $latestcommitid',
      // Comment the below line in release branch
	  regexpFilterExpression: 'jpetstore#(refs/tags/(alpha|Alpha)/.+|refs/heads/develop)#[^0]+',
	  // Un-comment the below line in release branch
      // regexpFilterExpression: 'jpetstore#refs/tags/(beta|Beta)/.+#[^0]+',

      regexpFilterText: '${projectname}#${ref}#${latestcommitid}',
      printContributedVariables: true,
      printPostContent: true
    )
  }

  environment {
      pom = readMavenPom file: 'pom.xml'
      POM_VERSION = "${pom.version}"
      POM_ARTIFACT_ID = "${pom.artifactId}"
  }
  
  stages {
      
    stage('Clean') {
      steps {
        bat 'mvn clean -s c:/maven_settings/settings.xml'
      }
    }


    stage('Compile') {
      steps {
        bat 'mvn compile -s c:/maven_settings/settings.xml'
      }
    }
    
    stage('Static Code Analysis') {
      steps {
         bat 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.2:sonar -s c:/maven_settings/settings.xml'
      }
    }
    
    stage('Packaging') {
      steps {
        bat 'mvn deploy -DskipTests -s c:/maven_settings/settings.xml'
      }
    }
    
    stage('Deploy') {
      steps {
         bat 'cd "C:/Program Files/Apache Software Foundation/Tomcat 9.0/webapps/" && del jpetstore.war /f /q'
         bat 'cd target && copy jpetstore.war "C:/Program Files/Apache Software Foundation/Tomcat 9.0/webapps/"'
      }
    }

  }

  tools {
    maven 'maven-3.5.3'
  }
}
