# Hyperselenium
A selenium enhancer for a better and easier browser automation.

## Intention
Why Hyperselenium? Selenium is just a library, but Hyperselenium is a library and server for entire scenarios.

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

### ACTION
During a "scenario" we have different actions. Not all actions interacts with the browser. Some actions save data, 
runs SQL scripts, push data on a SFTP folder, delete stuff, asserts variables, or just print notes into the HTML/PDF protocol.

The interactions with the browser are simple Selenium commands.

### PROTOCOL
A protocol of a scenario saves the result of each step and some additional notes and screenshots. They can be rendered into HTML/PDF.

### SCENARIO
A scenario is a test case or a use case. Selenium is not just used to test something, but it is also a tool to prepare something. If you scenario acts as a test case, then it will contain a series of asserts.

### JGIT
Deploy hyperselenium inside a docker or on a server, then let it load a git repository with the tests. You can use multiple instances for performance tests.
