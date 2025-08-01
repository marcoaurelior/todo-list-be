package com.marco.todo_list.application.service.impl

import com.marco.todo_list.application.exceptions.AlreadyExistsException
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
        task.name?.let { validateIfExistsByName(it) }
        val maxDisplayOrder = repository.findMaxDisplayOrder() ?: -1
        val newTask = task.copy(displayOrder = maxDisplayOrder + 1)

        logger.info("createTask=$newTask")
        return Task.create(newTask, repository)
    }

    override fun findOne(id: String): Task =
        repository.findById(id)
            .orElseThrow { NotFoundException("Task not found with id: $id") }

    override fun findAll(): List<Task> =
        repository.findAll()

    override fun update(task: Task): Task {
        logger.info("updateTask=$task")
        task.name?.let { validateIfExistsByName(it, task.id) }
        return findOne(task.id).update(task, repository)
    }

    override fun deleteTask(id: String) {
        logger.info("deleting task $id")
        val task = findOne(id)
        task.delete(repository)
    }

    fun validateIfExistsByName(name: String, idToExclude: String? = null) {
        val exists = if (idToExclude == null) {
            repository.existsByName(name)
        } else {
            repository.existsByNameAndIdNot(name, idToExclude)
        }

        if (exists) {
            throw AlreadyExistsException("A task with this name already exists")
        }
    }
}