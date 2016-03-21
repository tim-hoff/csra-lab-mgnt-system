#This is the repository for the CSRA Lab Applicaiton

You can do pretty much everything throught the `activator` console.

There are several demonstration files available in the template. I'll keep them in until we hook up everything.

#Controllers

##- HomeController.java:

  Shows how to handle simple HTTP requests.

##- AsyncController.java:

  Shows how to do asynchronous programming when handling a request.

##- CountController.java:

  Shows how to inject a component into a controller and use the component when
  handling requests.

#Components

##- Module.java:

  Shows how to use Guice to bind all the components needed by your application.

##- Counter.java:

  An example of a component that contains state, in this case a simple counter.

##- ApplicationTimer.java:

  An example of a component that starts when the application starts and stops
  when the application stops.

#Filters

##- Filters.java:

  Creates the list of HTTP filters used by your application.

##- ExampleFilter.java

  A simple filter that adds a header to every response.