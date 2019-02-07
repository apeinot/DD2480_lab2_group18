# DD2480_lab2_group18
Lab 2 of DD2480 (group 18): Implementation of a Continuous Integration server

Travis status: [![Build Status](https://travis-ci.org/apeinot/DD2480_lab2_group18.svg?branch=master)](https://travis-ci.org/apeinot/DD2480_lab2_group18)

## Description of the Project
Implements a small Continuous Integration (CI) server for GitHub using webhooks.

The CI server's job is to verify changes before they are made to the mainline, so as to reduce integration issues by detecting them as soon as possible. The server accomplishes this by fetching the commits, compiling them, and running their tests. If the tests pass and there are no conflicts between the branch and the mainline, the changes can be merged. The compilation and testing are triggered by a GitHub webhook, which is a HTTP POST JSON payload describing a GitHub event, e.g. a push, a pull-request or a commit. In our case it's triggered on a push to a branch.

## Implementation of the notifications

### GitHub notifications

For the GitHub notifications we used the JSON library org.json. Furthermore we created a throwaway GitHub account for the CI server so that the commits statuses can be set correctly. Then, we send a status POST request to the Github API.

To test the GitHub commit-set-status function, we set the status for a valid commit id (initial commit of the repo) and for an invalid one. The first test will succeed while the second one will fail.

### E-mail notifications

For the e-mail notifications, the dependencies JavaMail API and the Java Application Framework (JAF) were used. We used Gmail's SMTP server to host the mail and a throwaway Gmail account was created to send the e-mail notifications.

To test the mailbot we had it mail itself with a correct and a wrong password. We supplied it with an invalid recipient e-mail address and ensured that an error was thrown. Likewise we also entered an invalid sender e-mail address.

## History of project builds

The server stores the result of all builds. This history is easily accessible via a web interface. This interface will be linked in the github and email notification.

You can access the history directly by accessing the URL/IP: http://130.237.227.78:8018/. Each build is given an unique URL. in the form: http://130.237.227.78:8018/<user>/<repository>/<commit_id>.

## Running the Project

### Platform and Dependencies

*Platform*  
The project as been tested under Java 8 and 10 (other versions may also work).

*Dependencies*  
* ANT is needed to build the project ([APACHE ANT documentation](https://ant.apache.org/manual/))
* The following libraries are given in the lib directory:
  * JUnit 4.12 and harmcrest_core_1.3 for testing
  * activation and javax.mail for sending email notifications
  * Jetty 7.0.2 and servlet-api-2.5 for running the server
  * org.json for parsing and editing JSONobjects

### Compilation, Running and Testing

In a terminal (in the root folder of the project):
* run `ant compile` to compile the project (this just run the basic javac command and include the needed libraries)
* run `ant test-compile` to compile the project and the tests
* run `ant test` to compile and run the test of the project (JUnit is use to run the tests)
* run `ant run` to launch the CI server
* run `ant doc` to generate the API documentation of the project in HTML using Javadoc

(These commands are described in the [build.xml](build.xml) file. The result of this command indicate the success of the build)

When testing, we tried to create tests which satisfy all of the edge cases. Every important function was accompanied with at least two tests, one true and one false. We tried to create more complex input data and not just the bare minimum. All the tests implying an existing commit are done on the initial commit of the repository.

## Statement of Contributions

(Each feature addition is coupled with corresponding testing)

Every commit referenced an issue directly in the commit message title or body (e.g. "This function solves #5"), and was pushed on a branch which directly references a specific issue (e.g. a branch might be named "issue5" or "issue12").

* **Alexandre**
  * Set up the repository
  * Implement the command execution part
  * Help on combining everything in handle
  * Write and review README
  * Merge some pull requests into master
* **Emil**
  * Add E-Mail responder
  * Add web interface
  * Write and review README
* **Franz**
  * Implement GitHub responder
  * Write and review README
  * Add web interface
  * Do some clean up in the code
  * Merge some pull requests into master
* **Jonathan**
  * Set up Server
  * Add web interface.
  * Coordinate main control flow
  * Write and review README
* **Samuel**
  * Parse JSON arguments
  * Write and review README
