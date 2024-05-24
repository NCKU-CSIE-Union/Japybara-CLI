refresh-deps:
	mvn clean install

compile:
	mvn compile

run:
	mvn exec:java -Dexec.mainClass="com.ncku_csie_union.Main" -e