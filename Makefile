refresh-deps:
	mvn clean install

compile:
	mvn compile package

run:
	mvn exec:java -Dexec.mainClass="com.ncku_csie_union.Main" -e

example-run:
	java -cp "picocli-4.7.6.jar:target/japybara-cli-1.0.0.jar" com.ncku_csie_union.Main run --uri 'https://example.com' --rate 100000 --duration '1m' --vu 500 --verbose