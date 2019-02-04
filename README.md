# DD2480_lab2_group18
Lab 2 of DD2480 (group 18): Implementation of a Continuous Integration server

Travis status: [![Build Status](https://travis-ci.org/apeinot/DD2480_lab2_group18.svg?branch=master)](https://travis-ci.org/apeinot/DD2480_lab2_group18)

## Description of the project
Implements a small Continuous Integration (CI) server for GitHub using webhooks.

The CI server's job is to verify changes before they're made to the mainline, so as to reduce integration issues by detecting them as soon as possible. The server accomplishes this by fetching the commits, compiling them, and running their tests. If the tests pass and there are no conflicts between the branch and the mainline, the changes can be merged. The compilation and testing are triggered by a GitHub webhook, which is a HTTP POST JSON payload describing a GitHub event, e.g. a push, a pull-request or a commit. In our case it's triggered on a push to a branch.

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
