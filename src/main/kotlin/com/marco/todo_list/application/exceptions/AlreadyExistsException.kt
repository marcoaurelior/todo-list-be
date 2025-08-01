package com.marco.todo_list.application.exceptions

class AlreadyExistsException(message: String? = null, cause: Throwable? = null) : RuntimeException(message, cause)