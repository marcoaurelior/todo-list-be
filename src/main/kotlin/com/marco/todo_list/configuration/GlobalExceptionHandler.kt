package com.marco.todo_list.configuration

import com.marco.todo_list.application.controller.dto.exception.ApiError
import com.marco.todo_list.application.exceptions.AlreadyExistsException
import com.marco.todo_list.application.exceptions.NotFoundException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY
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
}