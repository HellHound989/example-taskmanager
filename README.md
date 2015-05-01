# example-taskmanager
Simple library I created for one of my projects that needed a multi-threaded task manager for various background tasks running at specific time variants.

# Description
The concept of this library was to allow an application to assign 'Tasks' that will run in background threads, quickly do its required function(s), and then end, with the idea that these 'tasks' would run repeatedly at predetermined times, and its left up to the developer to decide what the 'tasks' entail. 

These tasks are managed by a task manager whose sole function is to monitor when each task needs to run, and to invoke those tasks, in a seperate thread, when the predetermined time has been reached. Tasks can be added or removed from the task manager without affecting any other tasks that are assigned.

Task types can be broken down as the following:

 * SecondsTask - These are tasks that need to be run every 5..59 seconds.
 * MinutesTask - These are tasks that need to be run every 1..59 minutes.
 * HoursTasks - These are tasks that need to be run every 1..23 hours.
 * DaysTasks - These are tasks that need be run every 1..7 days. This task is different in that you can specify a time of day when these tasks need to be ran. (example: Every 3 days at 10:00pm)

# Example 
Say your application manages a simple database, and you need to purge this database every 5 hours. The idea is that you would create a Task class, extended with HoursTasks. You would then override the runTask() method, which is similiar in concept as overriding a Thread's run() method, with the code for purging a database. You would then schedule this task with the task manager, which will then, every 5 hours, invoke your code in the runTask() method.

There is no limit to how many seperate tasks you can assign, though as caveat, the most tasks we have had assigned was 13.
