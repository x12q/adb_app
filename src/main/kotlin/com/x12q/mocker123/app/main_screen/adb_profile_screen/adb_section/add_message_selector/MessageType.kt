package com.x12q.mocker123.app.main_screen.adb_profile_screen.adb_section.add_message_selector

enum class MessageType {
    TITLE, BODY, JSON, XML, PLAIN_TEXT
    ;

    companion object{
        fun allEntriesInOrder():List<MessageType>{
            return entries
        }
    }
}
