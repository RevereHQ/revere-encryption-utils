cryptUtils is packaged as a mvn and  is deployed below to public url:

https://s3-us-west-1.amazonaws.com/revere-public-maven-jars/revere-encryption-utils/repo

to include in your maven as a remote dependency:

add remote repo to your pom.xml project:

		<repository>
			<id>cryptUtils</id>
			<name>cryptUtils</name>
			<url>https://s3-us-west-1.amazonaws.com/revere-public-maven-jars/revere-encryption-utils/repo</url>
		</repository>

add this as a dependency:

		<dependency>
			<groupId>ai.revere.monitor.utils</groupId>
			<artifactId>cryptUtils</artifactId>
			<version>1.0.0</version>
		</dependency>


Creating external libary cryptUtils -- in repo revere-encryption-utils packaging as mvn repo:

1. get clone revere-encryption-utils
2. cd ~/path/to/revere-encryption-utils
3. mvn package -- this will build the jar file that contains the class CryptUtils

mkdir repo

4. run this command to package the jar in this repo:

mvn deploy:deploy-file -Durl=file:////pathTo/revere-encryption-utils/repo/ -Dfile=/pathTo/revere-encryption-utils/target/cryptUtils.jar -DgroupId=ai.revere.monitor.utils -DartifactId=cryptUtils -Dpackaging=jar -Dversion=1.0.0

5. You can now reference this url locally within your maven build


