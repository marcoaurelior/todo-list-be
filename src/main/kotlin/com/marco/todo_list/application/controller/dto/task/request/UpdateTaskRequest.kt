package com.marco.todo_list.application.controller.dto.task.request

import com.marco.todo_list.domain.Task
import jakarta.validation.constraints.*
import java.math.BigDecimal
import java.time.LocalDate

data class UpdateTaskRequest(
    @field:Size(min = 3, max = 255, message = "{validation.size}")
    @field:NotBlank(message = "{validation.required}")
    val name: String,

    @field:NotNull(message = "{validation.required}")
    @field:DecimalMin(value = "0.0", inclusive = false, message = "{validation.positive}")
    val cost: BigDecimal,

    @field:NotNull(message = "{validation.required}")
    @field:FutureOrPresent(message = "{validation.futureOrPresent}")
    val dueDate: LocalDate,

    ) {
    fun toModel(id: String): Task = Task(
        id = id,
        name = name,
        cost = cost,
        dueDate = dueDate
    )
}
