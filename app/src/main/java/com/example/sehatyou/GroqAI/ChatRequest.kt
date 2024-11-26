package com.example.sehatyou.GroqAI

class ChatRequest( val model: String,  val messages: List<Message>)

class Message( val role: String,  val content: String)
