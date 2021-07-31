package com.krld.composeplay

class State {
    val chats = mutableListOf<Chat>()
    lateinit var currentUser: User

    companion object {
        fun generate(messagesInChat: Int): State {

            val texts = mutableListOf(
                "Hello",
                "Android",
                "How are you",
                "This code creates two text element",
                "Good thing that we still walking",
            )

            val testData = State()


            testData.currentUser = User(1, "Vasya", R.drawable.cat1)

            val allUsers = mutableListOf<User>()
            allUsers.add(testData.currentUser)
            allUsers.add(User(2, "Petya", R.drawable.cat2))
            allUsers.add(User(3, "Artem", R.drawable.cat3))
            allUsers.add(User(4, "Alex", R.drawable.cat4))


            allUsers.forEach { u ->
                val chat = Chat()

                chat.withUser = u

                val usersPair = mutableListOf(chat.withUser, testData.currentUser)
                val ts = System.currentTimeMillis()
                repeat(messagesInChat) { i ->

                    usersPair.shuffle()
                    val msgs = Message(usersPair.first(), usersPair.last(), ts + i * 5_999, texts.random())
                    chat.messages.add(msgs)
                }

                testData.chats.add(chat)
            }


            return testData
        }
    }
}
