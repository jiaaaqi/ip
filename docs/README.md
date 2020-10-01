# User Guide

Duke is a scheduling platform where users can use it to keep a record of tasks that they have to do 
or has already done. User can classify their tasks into 3 types: Todo, Deadline and Event. 
The app is to be meant used on a Command Line Interface (CLI) with Java 11 installed in your computer.

#### Table of Contents
* [Quick start](#quick-start) 
* [Features](#features) 
    * [Add task](#add-task) `todo, deadline, event`
    * [Print list of tasks](#print-list-of-tasks) `list`
    * [Mark task as done](#mark-task-as-done) `done` 
    * [Delete task](#delete-task) `delete` 
    * [Save data](#save-data) 
    * [Find task using keywords](#find-task-using-keywords) `find` 
    * [View help message](#view-help-message) `help`
    * [Exit the app](#exit-the-app) `bye`
* [Things to note](#things-to-note)
* [Command summary](#command-summary)

## Quick Start

Step 1: Ensure you have `Java 11` or above installed in your computer. 
<br>Step 2: Download the latest `duke.jar` from here.
<br>Step 3: Copy the file into the folder that you want to use as your home folder. 
<br>Step 4: Copy the file path of the jar file
<br>Step 5: Open command line and key in the following commands to run the app: `java -jar filePath`.
<br>Step 6: Type in any commands to start. Please refer to [command summary](#command-summary) for help.
<br><br>If setup correctly, this will be displayed: 
```
Hello from
 ____        _        
|  _ \ _   _| | _____ 
| | | | | | | |/ / _ \
| |_| | |_| |   <  __/
|____/ \__,_|_|\_\___|

     Hello! I'm Duke.
     What can I do for you?
    ——————————————————————————————————————————————————
```

## Features 

### Add Task
Adds a _normal task_ to task list. 
<br>Format: `todo TASKDESCRIPTION`
<br>Example: `todo buy bread`
<br>Expected outcome: 
```
    ——————————————————————————————————————————————————
     Got it. I've added this task:
      [T][✘] buy bread
    ——————————————————————————————————————————————————
```

Add a _task with a deadline_ to task list.
<br>Format: `deadline TASKDESCRIPTION /by DATE`
<br>Example: `deadline project /by 2020-10-02`
<br>Expected outcome: 
```
    ——————————————————————————————————————————————————
     Got it. I've added this task:
      [D][✘] project (by: Oct 02 2020)
    ——————————————————————————————————————————————————
```

Add a _event_ to task list. 
<br>Format: `event TASKDESCRIPTION /on YYYY-MM-DD`
<br>Example: `event examination /on 2020-11-27`
<br>Expected outcome: 
```
    ——————————————————————————————————————————————————
     Got it. I've added this task:
      [E][✘] examination (on: Nov 27 2020)
    ——————————————————————————————————————————————————
```

### Print List of Tasks
Shows list of all tasks recorded. 
<br>Format : `list`
<br>Expected outcome: 
```
    ——————————————————————————————————————————————————
     1. [T][✘] buy bread
     2. [D][✘] project (by: Oct 02 2020)
     3. [E][✘] examination (on: Nov 27 2020)
    ——————————————————————————————————————————————————
```

### Mark Task as Done
Marks task at the index as done. 
<br>Format: `done INDEX`
<br>Example: `done 2`
<br>Expected outcome: 
```
    ——————————————————————————————————————————————————
     Nice! I've marked this task as done:
      [D][✓] project (by: Oct 02 2020)
    ——————————————————————————————————————————————————
```

### Delete Task 
Removes task at the index from the list. 
<br>Format: `delete INDEX`
<br>Example: `delete 1`
<br>Expected outcome: 
```
    ——————————————————————————————————————————————————
     Noted. I've removed this task:
      [T][✘] buy bread
     Now you have 2 tasks in the list.
    ——————————————————————————————————————————————————
```

### Save Data 
Whenever a change is made to the list of tasks, the app will automatically save the updated task list
into a text file (`duke.txt`) in the data directory of the folder. When the app restarts, the data
in the file will be loaded into the task list. 

### Find Task Using Keywords
Find task that contains the typed in words. 
<br>Format: `find KEYWORD`
<br>Example: `find left`, `find project`
<br>Expected outcome: 
```
find left
    ——————————————————————————————————————————————————
     Sorry, there are no matching tasks.
    ——————————————————————————————————————————————————
find project
    ——————————————————————————————————————————————————
     Here are the matching tasks in your list:
     1.[D][✓] project (by: Oct 02 2020)
    ——————————————————————————————————————————————————
```

### View Help Message 
Shows a message that gives location to user guide 
and some commands to start with 
<br>Format: `help`
<br>Expected outcome: 
```
    ——————————————————————————————————————————————————
     Here are some commands you can start with: 
      list: list all tasks
      done 2: mark 2nd task in current list as done
      delete 4: delete 4th task in current list
      bye: exit the app
     For more help: https://jiaaaqi.github.io/ip/
    ——————————————————————————————————————————————————
```

### Exit the App
Exits the program. 
<br>Format: `bye`
<br>Expected outcome: 
```
    ——————————————————————————————————————————————————
     Bye. Hope to see you again soon!
    ——————————————————————————————————————————————————
```

## Things to Note
* __Date__ for events and deadlines must be in the format YYYY-MM-DD. 

## Command Summary 
Action | Format
------------ | -------------
Add | todo: `todo TASKDESCRIPTION` <br>deadline: `deadline TASKDESCRIPTION /by DATE` <br>event: `event TASKDESCRIPTION /on DATE`
Mark task as done | `done INDEX`
Delete | `delete INDEX`
Find tasks | `find KEYWORDS`
List tasks | `list`
Help | `help`
Exit | `bye`

