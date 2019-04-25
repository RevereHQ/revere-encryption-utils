Creating external libary cryptUtils -- in repo revere-encryption-utils packaging as mvn repo:

1. get clone revere-encryption-utils
2. cd ~/path/to/revere-encryption-utils
3. mvn package -- this will build the jar file that contains the class CryptUtils

mkdir repo

1. run this command to package the jar in this repo:

mvn deploy:deploy-file -Durl=file:////pathTo/revere-encryption-utils/repo/ -Dfile=/pathTo/revere-encryption-utils/target/cryptUtils.jar -DgroupId=ai.revere.monitor.utils -DartifactId=cryptUtils -Dpackaging=jar -Dversion=1.0.0
