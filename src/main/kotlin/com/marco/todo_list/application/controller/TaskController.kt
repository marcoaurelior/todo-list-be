package com.marco.todo_list.application.controller

import com.marco.todo_list.application.controller.dto.task.request.CreateTaskRequest
import com.marco.todo_list.application.controller.dto.task.request.UpdateAllTasksRequest
import com.marco.todo_list.application.controller.dto.task.request.UpdateTaskRequest
import com.marco.todo_list.application.controller.dto.task.response.TasksResponse
import com.marco.todo_list.application.service.TaskService
import com.marco.todo_list.application.service.impl.TaskOrder
import jakarta.validation.Valid
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tasks")
class TaskController(
    private val taskService: TaskService,
) {
    @ResponseStatus(CREATED)
    @PostMapping
    fun create(
        @RequestBody @Valid request: CreateTaskRequest
    ): TasksResponse {
        val task = taskService.create(request.name, request.cost, request.dueDate)

        return TasksResponse.fromModel(task)
    }

    @GetMapping("/{id}")
    fun findOne(@PathVariable id: String): TasksResponse {

        return TasksResponse.fromModel(
            taskService.findOne(id)
        )
    }

    @GetMapping()
    fun findAll(): List<TasksResponse> {
        val tasks = taskService.findAll().sortedBy { it.displayOrder }

        return TasksResponse.fromModelList(
            tasks
        )
    }

    @PutMapping("/{id}")
    fun update(@RequestBody @Valid taskRequest: UpdateTaskRequest, @PathVariable id: String): TasksResponse {
        val updatedTask = taskService.update(
            id, taskRequest.name, taskRequest.cost, taskRequest.dueDate
        )

        return TasksResponse.fromModel(updatedTask)
    }

    @PutMapping()
    fun updateAllDisplayOrder(
        @RequestBody @Valid request: List<UpdateAllTasksRequest>
    ): List<TasksResponse> {
        val tasksToUpdate = request.map { TaskOrder(it.id, it.displayOrder) }
        val updatedTasks = taskService.updateAllDisplayOrder(tasksToUpdate)

        return TasksResponse.fromModelList(updatedTasks)
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String) {
        taskService.deleteTask(id)
    }
}