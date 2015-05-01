# example-taskmanager
Simple library I created for one of my projects that needed a multi-threaded task manager for various background tasks running at specific time variants.

# Description
The concept of this library was to allow an application to assign 'Tasks' that will run in background threads, quickly do its required function(s), and then end, with the idea that these 'tasks' would run repeatedly at predetermined times, left up to the developer to decide what these 'tasks' entail. 

These tasks are managed by a task manager whose sole function is to monitor when each task needs to run, and to invoke those tasks, in a seperate thread, when the predetermined time has been reached. Tasks can be added or removed from the task manager without affecting any other tasks that are assigned.

Task types can be broken down as the following:

 * SecondsTask - These are tasks that need to be run every 5..59 seconds.
 * MinutesTask - These are tasks that need to be run every 1..59 minutes.
 * HoursTasks - These are tasks that need to be run every 1..23 hours.
 * DaysTasks - These are tasks that need be run every 1..7 days. This task is different in that you can specify a time of day when these tasks need to be ran. (example: Every 3 days at 10:00pm)

# Example 
I have created an example application sitting in root directory of the project, with its output, in the root directory of the project. In this test application, we create 2 of our own task classes, one set to run every 2 minutes, and another set to run every 45 seconds.

    Test.jar - Test application utilizing the TaskManager library
    test-output.txt - Output of our test application
    
    test-src/Test.java - Source code example of our test application

As you can see in from the test application, we can create our own task classes and schedule them to the task manager, letting the task manager handle invoking our tasks at their pre-determined time.
