# Read Me

To run the test cases all you need to do is execute following command from the root directory where pom.xml exists and this will execute all the test files present:
```console
your-test-directory>mvn clean test
```

To execute all cases present inside specific class i.e. tests.TestUserPosts, you can execute following command:
```console
your-test-directory> mvn -Dtest=tests.TestUserPosts test
```

You can also directly execute testng.xml for running current tests

Test reports are generated in directory `test-output/ExtentReportsTestNG/` after execution.

In case of failure, Testcases are automatically retried once using retryAnalyzer.