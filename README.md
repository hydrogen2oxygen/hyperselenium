# hyperselenium
A selenium tool for a better and easier testing of web applications

### Intention
- SNIPPET, every action of multiple actions can be reused as snippets
- HTML / PDF WRAPPER, every test case result can be transformed into a readable protocol with screenshots
- PARAMETERS are automatically detected and you can use different sets of them for different environments
- SCRIPT or plain JAVA, whatever you need (Script uses simple actions and snippets)
- DESCRIPTION, you can explain what you expect and this will be included in the HTML/PDF protocol
- YES, NO, MAYBE, not every assert requires 100%, sometimes it is ok to get "something"
- PRECONDITIONS, some tests results in yellow instead of red when some preconditions are not fulfilled
- PREPARATION and REPEATABLE, some environments require some sort of preparation for repeatable tests (JDBC, SFTP)
- STOP, EDIT and CONTINUE, stop the test run whenever you need and adjust the script, then just continue the test
- SERVER, manage your tests and environments from a server application, trigger the tests, observe the results
