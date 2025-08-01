package com.marco.todo_list.application.service

import com.marco.todo_list.application.service.impl.TaskOrder
import com.marco.todo_list.domain.Task
import java.math.BigDecimal
import java.time.LocalDate

interface TaskService {
    fun create(name: String, cost: BigDecimal, dueDate: LocalDate): Task
    fun findOne(id: String): Task
    fun findAll(): List<Task>
    fun update(id: String, name: String, cost: BigDecimal, dueDate: LocalDate): Task
    fun updateAllDisplayOrder(newTasks: List<TaskOrder>): List<Task>
    fun deleteTask(id: String)
}