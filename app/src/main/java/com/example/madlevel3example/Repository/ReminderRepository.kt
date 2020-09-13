package com.example.madlevel3example.Repository

import android.content.Context
import com.example.madlevel3example.Model.Reminder
import com.example.madlevel3example.DAO.ReminderDao
import com.example.madlevel3example.ReminderRoomDatabase

class ReminderRepository(context: Context) {

    private var reminderDAO: ReminderDao

    init {
        val reminderRoomDatabase = ReminderRoomDatabase.getDatabase(context)
        reminderDAO = reminderRoomDatabase!!.reminderDao()
    }

    fun getAllReminders(): List<Reminder> {
        return reminderDAO.getAllReminders()
    }

    fun insertReminder(reminder: Reminder) {
        reminderDAO.insertReminder(reminder)
    }

    fun deleteReminder(reminder: Reminder) {
        reminderDAO.deleteReminder(reminder)
    }

    fun updateReminder(reminder: Reminder) {
        reminderDAO.updateReminder(reminder)
    }
}