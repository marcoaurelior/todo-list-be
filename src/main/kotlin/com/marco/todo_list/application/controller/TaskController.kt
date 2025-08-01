package com.marco.todo_list.application.controller

import com.marco.todo_list.application.controller.dto.task.request.CreateTaskRequest
import com.marco.todo_list.application.controller.dto.task.request.UpdateTaskRequest
import com.marco.todo_list.application.controller.dto.task.response.TasksResponse
import com.marco.todo_list.application.service.TaskService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus.CREATED
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tasks")
class TaskController(
    private val taskService: TaskService,
) {
    @ResponseStatus(CREATED)
    @PostMapping
    fun createProduct(
        @RequestBody @Valid request: CreateTaskRequest
    ): TasksResponse {
        val task = taskService.create(request.toModel())
        return TasksResponse.fromModel(task)
    }

    @GetMapping("/{id}")
    fun findOne(@PathVariable id: String): TasksResponse {
        return TasksResponse.fromModel(
            taskService.findOne(id)
        )
    }

    @GetMapping("/all")
    fun findAll(): List<TasksResponse> {
        return TasksResponse.fromModelList(
            taskService.findAll()
        )
    }

    @PutMapping("/{id}")
    fun edit(@RequestBody @Valid taskRequest: UpdateTaskRequest, @PathVariable id: String): TasksResponse {
        val taskToUpdate = taskRequest.toModel(id)
        val updatedTask = taskService.update(taskToUpdate)

        return TasksResponse.fromModel(updatedTask)
    }
}