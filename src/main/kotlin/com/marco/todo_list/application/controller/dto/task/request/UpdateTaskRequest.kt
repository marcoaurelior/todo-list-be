package com.marco.todo_list.application.controller.dto.task.request

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
    val dueDate: LocalDate,
)