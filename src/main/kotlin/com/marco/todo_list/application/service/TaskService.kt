package com.marco.todo_list.application.service

import com.marco.todo_list.domain.Task

interface TaskService {
    fun create(task: Task): Task
    fun findOne(id: String): Task
    fun findAll(): List<Task>
    fun update(task: Task): Task
}