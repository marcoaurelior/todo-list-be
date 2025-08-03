package com.marco.todo_list.application.service.impl

import com.marco.todo_list.application.exceptions.AlreadyExistsException
import com.marco.todo_list.application.exceptions.NotFoundException
import com.marco.todo_list.application.exceptions.PastDueDateException
import com.marco.todo_list.application.repository.TaskRepository
import com.marco.todo_list.application.service.TaskService
import com.marco.todo_list.domain.Task
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDate

@Service
class TaskServiceImpl(private val repository: TaskRepository) : TaskService {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun create(name: String, cost: BigDecimal, dueDate: LocalDate): Task {
        validateIfExistsByName(name)
        validateIfDueDateIsInThePast(dueDate)
        val maxDisplayOrder = repository.findMaxDisplayOrder() ?: -1
        val newTask = Task(name, cost, dueDate, maxDisplayOrder + 1)

        logger.info("createTask=$newTask")
        return Task.create(newTask, repository)
    }

    override fun findOne(id: String): Task =
        repository.findById(id)
            .orElseThrow { NotFoundException("Task not found with id: $id") }

    override fun findAll(): List<Task> =
        repository.findAll()

    override fun update(id: String, name: String, cost: BigDecimal, dueDate: LocalDate): Task {
        logger.info("updateTask=${id}")
        validateIfExistsByName(name, id)
        validateIfDueDateIsInThePast(dueDate)

        val existingTask = findOne(id)
        val updatedTask = Task(name, cost, dueDate, existingTask.displayOrder)

        return findOne(id).update(updatedTask, repository)
    }

    override fun updateAllDisplayOrder(newTasks: List<TaskOrder>): List<Task> {
        val currentTasks = repository.findAll()
        val updatedTasks = currentTasks.map { task ->
            task.copy(displayOrder = newTasks.first { it.id == task.id }.displayOrder)
        }

        logger.info("updateAllTasks=$updatedTasks")
        return Task.updateAllDisplayOrder(updatedTasks, repository)
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
            throw AlreadyExistsException("A task with name $name already exists")
        }
    }

    fun validateIfDueDateIsInThePast(date: LocalDate) {
        if (date.isBefore(LocalDate.now())) {
            throw PastDueDateException("A task cannot have a due date in the past")
        }
    }
}

data class TaskOrder(
    val id: String,
    val displayOrder: Int
)