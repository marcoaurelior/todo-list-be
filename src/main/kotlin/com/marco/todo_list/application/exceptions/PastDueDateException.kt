package com.marco.todo_list.application.exceptions

class PastDueDateException(message: String? = null, cause: Throwable? = null) : RuntimeException(message, cause)