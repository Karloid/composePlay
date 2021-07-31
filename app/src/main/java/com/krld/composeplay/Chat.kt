package com.krld.composeplay

class Chat {
    val messages = mutableListOf<Message>()

    lateinit var withUser: User
}
