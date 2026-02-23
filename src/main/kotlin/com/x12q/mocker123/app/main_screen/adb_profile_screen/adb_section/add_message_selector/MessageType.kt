package com.x12q.mocker123.app.main_screen.adb_profile_screen.adb_section.add_message_selector

enum class MessageType {
    TITLE, BODY, OTHER,
    ;

    companion object{
        fun allEntriesInOrder():List<MessageType>{
            val l0 = listOf(OTHER)
            return l0 + (entries - l0)
        }
    }
}
