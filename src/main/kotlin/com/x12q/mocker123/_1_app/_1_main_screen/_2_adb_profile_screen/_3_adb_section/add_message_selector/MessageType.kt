package com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._3_adb_section.add_message_selector

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
