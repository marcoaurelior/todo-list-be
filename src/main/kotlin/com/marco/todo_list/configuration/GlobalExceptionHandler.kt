package com.marco.todo_list.configuration

import com.marco.todo_list.application.controller.dto.exception.ApiError
import com.marco.todo_list.application.exceptions.AlreadyExistsException
import com.marco.todo_list.application.exceptions.NotFoundException
import com.marco.todo_list.application.exceptions.PastDueDateException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException, request: WebRequest): ResponseEntity<Any> {
        val apiError = ApiError(NOT_FOUND, "Not found exception", "Not found exception")
        logger.warn(apiError.toString(), ex)
        return ResponseEntity(apiError, HttpHeaders(), apiError.status)
    }

    @ExceptionHandler(AlreadyExistsException::class)
    fun handleAlreadyExistsException(ex: AlreadyExistsException, request: WebRequest): ResponseEntity<Any> {
        val apiError = ApiError(UNPROCESSABLE_ENTITY, "Already Exists", "Already Exists")
        return ResponseEntity(apiError, HttpHeaders(), apiError.status)
    }

    @ExceptionHandler(Exception::class)
    fun unhandledExceptions(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        val apiError = ApiError(INTERNAL_SERVER_ERROR, "Internal Server Error, try later", "Internal Server Error")
        return ResponseEntity(apiError, HttpHeaders(), apiError.status)
    }

    @ExceptionHandler(PastDueDateException::class)
    fun handlePastDateException(ex: PastDueDateException, request: WebRequest): ResponseEntity<Any> {
        val apiError = ApiError(BAD_REQUEST, "Invalid due date", ex.message ?: "Due date cannot be in the past")
        return ResponseEntity(apiError, HttpHeaders(), apiError.status)
    }

}