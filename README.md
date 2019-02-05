# DD2480_lab2_group18
Lab 2 of DD2480 (group 18): Implementation of a Continuous Integration server

Travis status: [![Build Status](https://travis-ci.org/apeinot/DD2480_lab2_group18.svg?branch=master)](https://travis-ci.org/apeinot/DD2480_lab2_group18)

## Description of the project
Implements a small Continuous Integration (CI) server for GitHub using webhooks.

The CI server's job is to verify changes before they're made to the mainline, so as to reduce integration issues by detecting them as soon as possible. The server accomplishes this by fetching the commits, compiling them, and running their tests. If the tests pass and there are no conflicts between the branch and the mainline, the changes can be merged. The compilation and testing are triggered by a GitHub webhook, which is a HTTP POST JSON payload describing a GitHub event, e.g. a push, a pull-request or a commit. In our case it's triggered on a push to a branch.

## Running the project

### Platform and dependencies

*Platform*  
The project as been tested under Java 8 and 10 (other versions may also work).

*Dependencies*  
* ANT is needed to build the project ([APACHE ANT documentation](https://ant.apache.org/manual/))
* The following libraries are given in the lib directory:
  * JUnit 4.12 and harmcrest_core_1.3 for testing
  * activation and javax.mail for sending email notifications
  * Jetty 7.0.2 and servlet-api-2.5 for running the server
  * org.json for parsing and editing JSONobjects

### Compilation, running and testing

In a terminal (in the root folder of the project):
* run `ant compile` to compile the project
* run `ant test-compile` to compile the project and the tests
* run `ant test` to compile and run the test of the project 
* run `ant run` to launch the CI server
* run `ant doc` to generate the API documentation of the project in HTML

(These commands are described in the [build.xml](build.xml) file.)

When testing, we tried to create tests which satisfy all of the edge cases. Every important function was accompanied with at least two tests, one true and one false. We tried to create more complex input data and not just the bare minimum.

## Statement of Contributions

(Each feature addition is coupled with corresponding testing)

Every commit referenced an issue directly in the commit message title or body (e.g. "This function solves #5"), and was pushed on a branch which directly references a specific issue (e.g. a branch might be named "issue5" or "issue12").

* **Alexandre**
  * Implement the testing process itself
  * Write and review README
  * Merge some pull requests into master
* **Emil**
  * Add E-Mail responder
  * Write and review README
* **Franz**
  * Implement GitHub responder
  * Write and review README
  * Merge some pull requests into master
* **Jonathan**
  * Set up Server
  * Coordinate main control flow
  * Write and review README
* **Samuel**
  * Parse JSON arguments
  * Write and review README
