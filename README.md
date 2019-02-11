# Background

The goal of this project is to convert a simple Java stopwatch to an
Android application.  The original java code can be found
[here](https://github.com/concurrency-cs-luc-edu/simplestopwatch-java).

# Learning Objectives

## Modeling

* Modeling state-dependent behavior with state machine diagrams
  (see also [here](/loyolachicagocs_comp313/stopwatch-android-java/src/default/doc))
* Distinguishing between view states and (behavioral) model states

## Semantics

* Event-driven/asynchronous program execution
* User-triggered input events
* Internal events from background timers
* Concurrency issues: single-thread rule of accessing/updating the view in the GUI thread

## Architecture and Design

* Key architectural issues and patterns
    * Simple dependency injection (DI)
    * Model-view-adapter (MVA) architectural pattern
    * Mapping MVA to Android
    * Difference between MVA and model-view-controller (MVC)
    * Distinguishing among dumb, reactive, and autonomous model components
* Key design patterns
    * Implementing event-driven behavior using the Observer pattern
    * Implementing state-dependent behavior using the State pattern
    * Command pattern for representing tasks as objects
    * Façade pattern for hiding complexity in the model from the adapter
* Relevant class-level design principles
    * Dependency Inversion Principle (DIP)
    * Single Responsibility Principle (SRP)
    * Interface Segregation Principle (ISP)
* Package-level architecture and relevant principles
    * Dependency graph
      (see also [here](/loyolachicagocs_comp313/stopwatch-android-java/src/default/doc))
    * Stable Dependencies Principle (SDP)
    * Acyclic Dependencies Principle (ADP)
* [Architectural journey](/stopwatch-android-java/commits)

## Testing

* Different types of testing
    * Component-level unit testing
    * System testing
    * Instrumentation testing
* Mock-based testing
* Testcase Superclass pattern (uses Template Method pattern)
* Test coverage




