# revere-encryption-utils
This is a packaged jar file that compiles the class CryptUtils.java in a jar file.  It gives you the ability to encrypt and decrypt using AES encryption.
NOTE: Since github no longer supports downloading binary files the jar repo is available here:

https://s3-us-west-1.amazonaws.com/revere-public-maven-jars/revere-encryption-utils/repo

Including in a maven pom.xml:

1. Add this section to pom.xml in the <repositories>...</repositories> section (you may need to create it)

		<repository>
			<id>cryptUtils</id>
			<name>cryptUtils</name>
			<url>https://s3-us-west-1.amazonaws.com/revere-public-maven-jars/revere-encryption-utils/repo</url>
		</repository>
    
2. Add this dependency in the <dependencies> .. </dependencies> section (you may need to create it it if you have no dependencies)

		<dependency>
			<groupId>ai.revere.monitor.utils</groupId>
			<artifactId>cryptUtils</artifactId>
			<version>1.0.0</version>
		</dependency>
