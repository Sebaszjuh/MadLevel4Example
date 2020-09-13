package com.example.madlevel4example

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel4example.Model.Reminder
import com.example.madlevel2example.ReminderAdapter
import com.example.madlevel4example.Repository.ReminderRepository
import kotlinx.android.synthetic.main.fragment_reminders.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class RemindersFragment : Fragment() {

    private lateinit var reminderRepository: ReminderRepository

    private val reminders = arrayListOf<Reminder>()
    private val reminderAdapter = ReminderAdapter(reminders)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reminders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeAddReminderResult()

        reminderRepository = ReminderRepository(requireContext())
        getRemindersFromDatabase()

    }

    private fun initViews() {
        // Initialize the recycler view with a linear layout manager, adapter
        rvReminders.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvReminders.adapter = reminderAdapter
        rvReminders.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        //adds reminder to view
        createItemTouchHelper().attachToRecyclerView(rvReminders)
    }

    private fun getRemindersFromDatabase() {
        val reminders = reminderRepository.getAllReminders()
        this@RemindersFragment.reminders.clear()
        this@RemindersFragment.reminders.addAll(reminders)
        reminderAdapter.notifyDataSetChanged()
    }


    private fun observeAddReminderResult() {
        setFragmentResultListener(REQ_REMINDER_KEY) { key, bundle ->
            
            bundle.getString(BUNDLE_REMINDER_KEY)?.let {
                val reminder = Reminder(it)

                // Adds into db en retrieves it from db
                reminderRepository.insertReminder(reminder)
                getRemindersFromDatabase()

            } ?: Log.e("ReminderFragment", "Request triggered, but empty reminder text!")

        }
    }

    private fun createItemTouchHelper(): ItemTouchHelper {

        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            //Not used right now
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            //Removes on swipe left
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                reminderRepository.deleteReminder(reminders[position])
                getRemindersFromDatabase()

            }
        }
        return ItemTouchHelper(callback)
    }

}