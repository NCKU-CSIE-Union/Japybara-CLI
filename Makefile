refresh-deps:
	mvn clean install

compile:
	mvn compile package

run:
	mvn exec:java -Dexec.mainClass="com.ncku_csie_union.Main" -e