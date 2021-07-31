package com.krld.composeplay

class State {
    var selectedChat: Chat? = null
    val chats = mutableListOf<Chat>()
    lateinit var currentUser: User

    companion object {

        private val avatarResId = mutableListOf(
            R.drawable.cat1,
            R.drawable.cat2,
            R.drawable.cat3,
            R.drawable.cat4,
            R.drawable.cat5,
            R.drawable.cat6,
        )
        val texts = mutableListOf(
            "Hello",
            "Android",
            "How are you",
            "This code creates two text element",
            "Good thing that we still walking",
            "Import this sample dataset into your project to help bootstrap the conversation quickly:",
        )

        val names = mutableListOf(
            "Vasya",
            "Petya",
            "Artem",
            "Alex",
            "Gena",
            "Stewart",
            "John",
            "Boris",
        )

        fun generate(messagesInChat: Int): State {

            val testData = State()

            val allUsers = mutableListOf<User>()
            repeat(50) {
                allUsers.add(User(it.toLong(), names.random(), avatarResId.random()))
            }

            testData.currentUser = allUsers.random()

            allUsers.forEach { u ->
                val chat = Chat()

                chat.withUser = u

                val usersPair = mutableListOf(chat.withUser, testData.currentUser)
                val ts = System.currentTimeMillis()
                repeat(messagesInChat) { i ->

                    usersPair.shuffle()
                    val msgs =
                        Message(usersPair.first(), usersPair.last(), ts + i * 5_999, texts.random())
                    chat.messages.add(msgs)
                }

                testData.chats.add(chat)
            }


            return testData
        }
    }
}
