package com.marco.todo_list.application.exceptions

class NotFoundException(message: String? = null, cause: Throwable? = null) : RuntimeException(message, cause)