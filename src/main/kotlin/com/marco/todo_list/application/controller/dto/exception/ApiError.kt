package com.marco.todo_list.application.controller.dto.exception
import org.springframework.http.HttpStatus

data class ApiError(
    var status: HttpStatus,
    var clientMessage: String,
    var errors: List<String>
) {
    constructor(status: HttpStatus, clientMessage: String, error: String) :
            this(status, clientMessage, arrayListOf<String>(error))
}
