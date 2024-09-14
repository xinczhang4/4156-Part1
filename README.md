# 4156 Individual Project 1
### Xinchen Zhang (xz3052)

## Prerequisites
To run and develop this project on MacOS platform, you will need:

1. Java 17
2. Maven 3.9.8
3. Spring Boot 2.x.x
4. JUnit 5 and Mockito for testing
5. PMD for static code analysis

## Data Setup
`mvn spring-boot:run -Dspring-boot.run.arguments="setup"`

## Test Cases and Coverage
Run `mvn clean test`: all test cases passed  
Tests run: 59, Failures: 0, Errors: 0, Skipped: 0

Run `mvn jacoco:report`: Report generated under IndividualProject/target/site/jacoco/index.html  
Instruction Coverage: 93%, Branch coverage: 79%

## PMD Installation and Check
### Installation command:  
`$ cd $HOME`  
`$ curl -OL https://github.com/pmd/pmd/releases/download/pmd_releases%2F7.5.0/pmd-dist-7.5.0-bin.zip`  
`$ unzip pmd-dist-7.5.0-bin.zip`  
`$ alias pmd="$HOME/pmd-bin-7.5.0/bin/pmd"`  
### Check command:   
`cd IndividualProject`  
`pmd check -d src -R rulesets/java/quickstart.xml -f text `
### Bug Report Location
`IndividualProject/bugs.txt`
## CodeStyle Check
`mvn checkstyle:check`  
All codestyle and PMD error cleared.