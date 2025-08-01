package com.marco.todo_list.application.controller.dto.task.request

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

data class UpdateAllTasksRequest(
    val id: String,

    @field:NotNull(message = "{validation.required}")
    @field:Min(value = 0, message = "{validation.min}")
    val displayOrder: Int
)
