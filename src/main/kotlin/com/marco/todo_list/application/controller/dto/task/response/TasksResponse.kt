package com.marco.todo_list.application.controller.dto.task.response

import com.marco.todo_list.domain.Task
import java.time.LocalDate

data class TasksResponse(
    val id: String,
    val name: String?,
    val dueDate: LocalDate?,
) {
    companion object {
        fun fromModel(task: Task) = TasksResponse(
            id = task.id,
            name = task.name,
            dueDate = task.dueDate,
        )

        fun fromModelList(tasks: List<Task>) = tasks.map { fromModel(it) }
    }
}