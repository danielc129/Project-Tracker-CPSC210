# My Personal Project: Project Tracker
## Description
This program allows you to keep track of tasks for various projects and tracks your progress.
Users will be able to create and name **projects**. Within each project, tasks and subtasks can be added.
Each **task/subtask** can be assigned a:
- Name
- Due date
- Description
- Progress weighting (as a percentage)

The program will list tasks and subtasks in order of due date. Subsequent tasks must be in chronological order of due date, and the last subtask of a task must have the same due date as the parent task. The progress weighting of all tasks and subtasks within a project must sum to 100%. The progress weighting of subtasks must sum to the progress weighting of the parent task. 

When a task is marked as complete, the percentage completion of the total project
increases depending on the *progress weighting* of the task. Additionally, the program keeps track of progress over time, and can show you that information textually and graphically. On program launch, 
a **summary** of the next tasks to work on (determined by due date) will be provided. 

This project is of interest to me because as a student, I have to manage multiple projects/responsibilities. A program like this would make it easier to manage my time, by breaking up projects into small, actionable chunks that are recommended to me. I can easily see how much progress I am making as well. This program can be used by anyone who wants to keep track of tasks that make up a larger whole. 

## User Stories

- As a user, I want to be able to create a project and specify a name and description
- As a user, I want to be able to add a task under a project and specify a name, due date, description, and completion weighting
- As a user, I want to be able to add multiple tasks under a 
project
- As a user, I want to be able to add multiple projects, and select a project to view details
- As a user, I want to be able to add subtasks to tasks 
- As a user, I want to be able to change the name, due date, description, and completion weighting of tasks
- As a user, I want to be able to view a list of all of the tasks and subtasks required to complete a project
- As a user, I want to be able to mark a task as completed, with overall project progress increasing by the completion weighting of the task
- As a user, I want to be able to delete a task, subtask, or project
- As a user, I want to be able to see a summary of the next tasks among all projects that I should work on
- As a user, I want to be able to see the overall project progress (expressed as a percentage) for each project
- As a user, I want to be able to see a report of the progress over time that I have made for a certain project
- As a user, I want to be able to save my changes when the program closes, so that my tasks and projects appear again when the program restarts

