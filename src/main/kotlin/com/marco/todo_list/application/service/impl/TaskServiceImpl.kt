package com.marco.todo_list.application.service.impl

import com.marco.todo_list.application.exceptions.NotFoundException
import com.marco.todo_list.application.repository.TaskRepository
import com.marco.todo_list.application.service.TaskService
import com.marco.todo_list.domain.Task
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class TaskServiceImpl(private val repository: TaskRepository) : TaskService {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun create(task: Task): Task {
        val maxDisplayOrder = repository.findMaxDisplayOrder() ?: -1
        val newTask = task.copy(displayOrder = maxDisplayOrder + 1)

        logger.info("createTask=$newTask")
        return Task.create(newTask, repository)
    }

    override fun findOne(id: String): Task =
        repository.findById(id)
            .orElseThrow { NotFoundException("Task not found with id: $id") }
}