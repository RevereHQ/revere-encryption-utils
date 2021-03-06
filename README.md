# revere-encryption-utils

Created by Andrew Stanton VP Engineering @ Revere, for Java 8 packages.

This is a packaged jar file that compiles the class CryptUtils.java in a jar file.  It gives you the ability to encrypt and decrypt using AES encryption.  

NOTE: You will need to use the Unlimited Strength Java(TM) Cryptography Extension Policy Files from sun to enable the AES CIPHER correctly.  The policy files only control the level of encryption.  They do not do the encryption themselves.

You can find details here: https://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html

Since github no longer supports downloading binary files the mvn jar repo is available here:

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

3. To use this effectively you will need to create a config.properties file that contains the public and private keys used for the encryption, with the values aes.key and aes.iv respectively.  You will need to put these files in the main/src/properties folder in the maven project you are building.  If you want to test it also you will need to copy to the test/resources section as well.  Example CryptUtilsTest.java is used here but it is not compiled into the jar file.

The config.properties would look like this:

aes.key=abc..

aes.iv=abc..

4. An example of how to use different key, iv combinations is shown in the CryptUtilsTest.java class.  

The aes.key should be 16, 24 or 32 bytes long

The aes.iv should be 16 bytes long.

