package com.example.madlevel4example.Repository

import android.content.Context
import com.example.madlevel4example.Model.Reminder
import com.example.madlevel4example.DAO.ReminderDao

class ReminderRepository(context: Context) {

    private var reminderDAO: ReminderDao

    init {
        val reminderRoomDatabase = ReminderRoomDatabase.getDatabase(context)
        reminderDAO = reminderRoomDatabase!!.reminderDao()
    }

    suspend fun getAllReminders(): List<Reminder> {
        return reminderDAO.getAllReminders()
    }

    suspend fun insertReminder(reminder: Reminder) {
        reminderDAO.insertReminder(reminder)
    }

    suspend fun deleteReminder(reminder: Reminder) {
        reminderDAO.deleteReminder(reminder)
    }

    suspend fun updateReminder(reminder: Reminder) {
        reminderDAO.updateReminder(reminder)
    }
}