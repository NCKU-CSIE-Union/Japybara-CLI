refresh-deps:
	mvn clean install

compile: refresh-deps
	mvn compile package

run:
	mvn exec:java -Dexec.mainClass="com.ncku_csie_union.Main" -e run

example-run:
	java -cp "picocli-4.7.6.jar:target/japybara-cli-1.0.0.jar" com.ncku_csie_union.Main run --uri 'http://localhost:8888' --rate 1000 --duration '5s' --vu 50