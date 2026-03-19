# My Personal Project: Project Tracker
## Description
This program allows you to keep track of tasks for various projects and tracks your progress.
Users will be able to create and name **projects**. Within each project, tasks and subtasks can be added.
Each **task/subtask** can be assigned a:
- Name
- Due date
- Description
- Progress weighting (unitless)

The program will list tasks and subtasks in order of due date. Subsequent tasks must be in chronological order of due date, and the last subtask of a task must have the same due date as the parent task. If a task contains subtasks, its due date and progress weighting are determined by its subtasks. As a result, only tasks without subtasks can have their due date and progress weighting edited. Additionally, projects as well as tasks with subtasks will show a completion percentage, determined from the progress weighting and completion status of the subtasks.

When a task is marked as complete, the percentage completion of the total project
increases depending on the *progress weighting* of the task. Additionally, the program keeps track of progress over time, and can show you that information textually and graphically. On program launch, 
a **summary** of the next tasks to work on (determined by due date) will be provided. 

This project is of interest to me because as a student, I have to manage multiple projects/responsibilities. A program like this would make it easier to manage my time, by breaking up projects into small, actionable chunks that are recommended to me. I can easily see how much progress I am making as well. This program can be used by anyone who wants to keep track of tasks that make up a larger whole. 

## User Stories

- As a user, I want to be able to create a project and specify a name and description
- As a user, I want to be able to add a task under a project and specify a name, due date, description, and completion weighting
- As a user, I want to be able to add multiple tasks under a project
- As a user, I want to be able to add multiple projects, and select a project to view details
- As a user, I want to be able to add subtasks to tasks 
- As a user, I want to be able to change the name, due date, description, and completion weighting of tasks without subtasks
- As a user, I want to be able to change the name and description of tasks with subtasks
- As a user, I want to be able to view a list of all of the tasks and subtasks required to complete a project
- As a user, I want to be able to mark a task as completed, with overall project progress increasing by the completion weighting of the task
- As a user, I want to be able to delete a task, subtask, or project
- As a user, I want to be able to see a summary of the next tasks among all projects that I should work on
- As a user, I want to be able to see the overall project progress (expressed as a percentage) for each project
- As a user, I want to be able to see a report of the progress over time that I have made for a certain project
- As a user, I want to be able to save my projects and their tasks (if I so choose)
- As a user, I want to be able to load projects and their tasks from a file (if I so choose)

## Instructions for End User
- You can view the panel that display the tasks that have already been added to the project by selecting a project from the list and clicking the "Select Project" button. This will open up a panel that shows a list of tasks under the selected project.
- You can generate the first required action related to the user story "adding multiple tasks to a project" by first selecting a project, and then either clicking the "Add Project-Level Task" button or selecting a task and clicking the "Add Subtask" button. A popup will open allowing you to fill out the task name, task description, task weight, and task due date. Click the "Add" button on the popup to add the task. Use "Add Project-Level Task" or "Add Subtask" again to add more tasks to the selected project.
- You can generate the second required action related to the user story "mark a task and its subtasks as completed" by selecting a task in the project view that is marked incomplete and clicking the "Toggle Completion" button. This will mark the task and any of its descendant tasks as complete. 
- You can locate my visual component by starting the application without loading any projects from file or adding any projects. A splash image will be displayed. 
- You can save the state of my application by navigating to the menu bar, hovering over "Files", and selecting "Save projects"
- You can reload the state of my application by navigating to the menu bar, hovering over "Files", and selecting "Load saved projects"