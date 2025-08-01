package com.marco.todo_list.application.controller.dto.task.request

import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.FutureOrPresent
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.math.BigDecimal
import java.time.LocalDate

data class CreateTaskRequest(
    @field:Size(max = 255, message = "{validation.size}")
    val name: String,

    @field:NotNull(message = "{validation.required}")
    @field:DecimalMin(value = "0.0", inclusive = false, message = "{validation.positive}")
    val cost: BigDecimal,

    @field:NotNull(message = "{validation.required}")
    @field:FutureOrPresent(message = "{validation.futureOrPresent}")
    val dueDate: LocalDate,
    )
