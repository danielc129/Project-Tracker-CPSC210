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

## Phase 4: Task 2
```
Thu Mar 26 15:47:35 PDT 2026
New project list instantiated
Thu Mar 26 15:48:56 PDT 2026
Project(#1) created with name CPSC210 Project, and description Make a project tracker program
Thu Mar 26 15:48:56 PDT 2026
Added project(#1) to project list : CPSC210 Project
Thu Mar 26 15:55:47 PDT 2026
Created leaf task(#2) with name Phase 1, at project root-level, and description Console application
Thu Mar 26 15:55:47 PDT 2026
Added project(#1) root-level task: Phase 1(#2)
Thu Mar 26 15:55:47 PDT 2026
Progress history for project #1 updated
Thu Mar 26 15:56:20 PDT 2026
Created leaf task(#3) with name Phase 2, at project root-level, and description Data persistence
Thu Mar 26 15:56:20 PDT 2026
Added project(#1) root-level task: Phase 2(#3)
Thu Mar 26 15:56:30 PDT 2026
Created leaf task(#4) with name Model, parent task Phase 1(#2), and description
Thu Mar 26 15:56:30 PDT 2026
Created branch task(#5) with name Phase 1, at project root-level, description Console application, and subtasks:
    [ ] Model (Due: February 15, 2026 | Weight: 5)
Thu Mar 26 15:56:30 PDT 2026
Set task Model(#4) parent task field to Phase 1(#5)
Thu Mar 26 15:56:30 PDT 2026
Removed project(#1) root-level task: Phase 1(#2)
Thu Mar 26 15:56:30 PDT 2026
Added project(#1) root-level task: Phase 1(#5)
Thu Mar 26 15:56:58 PDT 2026
Created leaf task(#6) with name Ttest, parent task Phase 1(#5), and description Ttest classes
Thu Mar 26 15:56:58 PDT 2026
Set leaf task Ttest(#6) depth field to 1
Thu Mar 26 15:56:58 PDT 2026
Set task Ttest(#6) parent task field to Phase 1(#5)
Thu Mar 26 15:56:58 PDT 2026
Added subtask Ttest(#6) to branch task Phase 1(#5)
Thu Mar 26 15:57:08 PDT 2026
Set task Test(#6) name to Test
Thu Mar 26 15:57:08 PDT 2026
Set task Test(#6) description to Test classes
Thu Mar 26 15:57:08 PDT 2026
Set leaf task Test(#6) weight to 5
Thu Mar 26 15:57:08 PDT 2026
Set leaf task Test(#6) due date to February 15, 2026
Thu Mar 26 15:57:10 PDT 2026
Set leaf task Test(#6) completion status to true
Thu Mar 26 15:57:10 PDT 2026
Progress history for project #1 updated
Thu Mar 26 15:57:12 PDT 2026
Set leaf task Model(#4) completion status to true
Thu Mar 26 15:57:12 PDT 2026
Set leaf task Test(#6) completion status to true
Thu Mar 26 15:57:12 PDT 2026
Set branch task Phase 1(#5) completion status to true
Thu Mar 26 15:57:12 PDT 2026
Progress history for project #1 updated
Thu Mar 26 15:57:15 PDT 2026
Set leaf task Phase 2(#3) completion status to true
Thu Mar 26 15:57:15 PDT 2026
Progress history for project #1 updated
Thu Mar 26 15:57:17 PDT 2026
Removed subtask Test(#6) from branch task Phase 1(#5)
Thu Mar 26 15:57:43 PDT 2026
Created leaf task(#7) with name Specify classes, parent task Model(#4), and description
Thu Mar 26 15:57:43 PDT 2026
Created branch task(#8) with name Model, parent task Phase 1(#5), description , and subtasks:
        [ ] Specify classes (Due: February 15, 2026 | Weight: 5)
Thu Mar 26 15:57:43 PDT 2026
Set task Specify classes(#7) parent task field to Model(#8)
Thu Mar 26 15:57:43 PDT 2026
Removed subtask Model(#4) from branch task Phase 1(#5)
Thu Mar 26 15:57:43 PDT 2026
Set leaf task Specify classes(#7) depth field to 2
Thu Mar 26 15:57:43 PDT 2026
Removed subtask Model(#4) from branch task Phase 1(#5)
Thu Mar 26 15:57:43 PDT 2026
Set leaf task Specify classes(#7) depth field to 2
Thu Mar 26 15:57:43 PDT 2026
Thu Mar 26 15:57:43 PDT 2026
Set leaf task Specify classes(#7) depth field to 2
Thu Mar 26 15:57:43 PDT 2026
Set leaf task Specify classes(#7) depth field to 2
Thu Mar 26 15:57:43 PDT 2026
Thu Mar 26 15:57:43 PDT 2026
Set branch task Model(#8) depth field to 1
Set branch task Model(#8) depth field to 1
Thu Mar 26 15:57:43 PDT 2026
Set task Model(#8) parent task field to Phase 1(#5)
Thu Mar 26 15:57:43 PDT 2026
Added subtask Model(#8) to branch task Phase 1(#5)
Thu Mar 26 15:57:43 PDT 2026
Progress history for project #1 updated
Thu Mar 26 15:57:49 PDT 2026
Removed project(#1) : CPSC210 Project
```