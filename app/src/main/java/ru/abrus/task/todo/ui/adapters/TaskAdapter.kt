package ru.abrus.task.todo.ui.adapters

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_task.view.*
import ru.abrus.task.todo.R
import ru.abrus.task.todo.storage.TaskEntity

class TaskAdapter(
    private val onNextClicked: (Int) -> Unit,
    private val onActiveChanged: (Int, Boolean) -> Unit,
) : RecyclerView.Adapter<ViewHolder>() {

    private var tasks = arrayListOf<TaskEntity>()

    public fun setData(tasks: ArrayList<TaskEntity>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks[position]

        with(holder.itemView) {

            taskName.text = task.name
            taskDate.text = String.format("%s %s", task.date, task.time)
            taskActive.isChecked = task.isActive

            if (task.isActive) {
                taskName.setTextColor(getColor(context, R.color.color_white))
                taskDate.setTextColor(getColor(context, R.color.color_white))

                next.setColorFilter(getColor(context, R.color.color_white), android.graphics.PorterDuff.Mode.SRC_IN)
                taskActive.buttonTintList = ColorStateList.valueOf(getColor(context, R.color.color_white))
            } else {
                taskName.setTextColor(getColor(context, R.color.color_black))
                taskDate.setTextColor(getColor(context, R.color.color_black))

                next.setColorFilter(getColor(context, R.color.color_black), android.graphics.PorterDuff.Mode.SRC_IN)
                taskActive.buttonTintList = ColorStateList.valueOf(getColor(context, R.color.color_primary))
            }

            next.setOnClickListener {
                onNextClicked.invoke(task.uid)
            }

            taskActive.setOnCheckedChangeListener { _, isChecked ->
                onActiveChanged.invoke(task.uid, isChecked)
            }
        }
    }

    override fun getItemCount() = tasks.size

}

