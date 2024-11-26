package com.example.sehatyou.GroqAI

data class ChatResponse(
    val choices: List<Choice> = emptyList()
)

data class Choice(
    val message: ResponseMessage? = null
)

data class ResponseMessage(
    val role: String? = null,
    val content: String? = null
)

