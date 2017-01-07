Preparations
1.download JDK 1.8.0, set JAVA_HOME
2.download eclipse EE
3.download Maven 
4.download Git

Import Project
1.open Eclipse
2.File -> Import -> Maven -> Existing Maven Projects
3.select the project root folder as Root directory

Include dropwizard into Project
1.run the following mvn commonds in the Project root folder
mvn archetype:generate -DarchetypeGroupId=io.dropwizard.archetypes -DarchetypeArtifactId=java-simple DarchetypeVersion=[1.0.2]

it will take sometime download dropwizard related jars

2. run the following command to build project in the Project root folder
mvn package

3. run the following command to run the application in the Project root folder
java -Xmx3g -jar target/ineuron-0.0.1-SNAPSHOT.jar server INeuron.yml

4.access the api
http://localhost:8080/hello-world?name=Successful+Dropwizard+User


mvn install:install-file -Dfile=c:\lib\ejml-0.23.jar -DgroupId=nlp  -DartifactId=ejml -Dversion=0.23 -Dpackaging=jar
mvn install:install-file -Dfile=c:\lib\javax.json.jar -DgroupId=nlp  -DartifactId=javax.json -Dversion=0.23 -Dpackaging=jar
mvn install:install-file -Dfile=c:\lib\joda-time.jar -DgroupId=nlp  -DartifactId=joda-time -Dversion=0.23 -Dpackaging=jar
mvn install:install-file -Dfile=c:\lib\jollyday.jar -DgroupId=nlp  -DartifactId=jollyday -Dversion=0.23 -Dpackaging=jar
mvn install:install-file -Dfile=c:\lib\protobuf.jar -DgroupId=nlp  -DartifactId=protobuf -Dversion=0.23 -Dpackaging=jar
mvn install:install-file -Dfile=c:\lib\stanford-chinese-corenlp-models.jar -DgroupId=nlp  -DartifactId=stanford-chinese-corenlp-models -Dversion=3.6.0 -Dpackaging=jar
mvn install:install-file -Dfile=c:\lib\stanford-corenlp-3.7.0.jar -DgroupId=nlp  -DartifactId=stanford-corenlp -Dversion=3.7.0 -Dpackaging=jar
mvn install:install-file -Dfile=c:\lib\xom.jar -DgroupId=nlp  -DartifactId=xom -Dversion=0.23 -Dpackaging=jar
mvn install:install-file -Dfile=c:\lib\stanford-corenlp-3.7.0-models.jar -DgroupId=nlp  -DartifactId=models -Dversion=3.7.0 -Dpackaging=jar
