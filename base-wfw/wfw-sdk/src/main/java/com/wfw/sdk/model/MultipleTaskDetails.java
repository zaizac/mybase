package com.wfw.sdk.model;


import java.util.List;


public class MultipleTaskDetails {

	private TaskDetails currentTask;

	private List<TaskDetails> newTaskList;


	public MultipleTaskDetails() {
	}


	public MultipleTaskDetails(TaskDetails currentTask, List<TaskDetails> newTaskList) {
		this.currentTask = currentTask;
		this.newTaskList = newTaskList;
	}


	public TaskDetails getCurrentTask() {
		return currentTask;
	}


	public void setCurrentTask(TaskDetails currentTask) {
		this.currentTask = currentTask;
	}


	public List<TaskDetails> getNewTaskList() {
		return newTaskList;
	}


	public void setNewTaskList(List<TaskDetails> newTaskList) {
		this.newTaskList = newTaskList;
	}

}
