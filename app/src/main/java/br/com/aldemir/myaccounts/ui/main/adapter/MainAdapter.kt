package br.com.aldemir.myaccounts.ui.main.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.domain.model.Expense
import com.google.android.material.card.MaterialCardView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainAdapter(
    private var values: MutableList<Expense>,
    private var _status: ArrayList<Boolean>,
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private val rowStates = mutableMapOf<Int, LazyListState>()

    lateinit var mClickListener: ClickListener
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_compose, parent, false)
        return ViewHolder(view)
    }

    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }

    interface ClickListener {
        fun onClick(position: Int, aView: View)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        val status = _status[position]
//        holder.nameAccount.text = item.name
//        holder.valueAccount.text = "${item.description}"
//        if(item.due_date != null) {
//            holder.dueDate.text = String.format("%02d", item.due_date)
//        }
//        if (status) {
//            holder.status.setTextColor(ContextCompat.getColor(context, R.color.green))
//            holder.status.text = "Pago"
//        } else {
//            holder.status.setTextColor(ContextCompat.getColor(context, R.color.orange))
//            holder.status.text = "Pendente"
//        }

        val state = rowStates.getOrPut(position) { LazyListState() }
        holder.itemRow.index = position
        holder.itemRow.rowState = state
    }

    fun updateList(list: MutableList<Expense>, status: ArrayList<Boolean>) {
        values = list
        _status = status
        notifyDataSetChanged()
    }

    private fun dateFormat(date: Date): String {
        val myFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        return sdf.format(date)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
//        val containerItem: MaterialCardView = view.findViewById(R.id.material_cardView)
//        val nameAccount: TextView = view.findViewById(R.id.tv_name_account)
//        val valueAccount: TextView = view.findViewById(R.id.tv_value_account)
//        val dueDate: TextView = view.findViewById(R.id.tv_due_date)
//        val status: TextView = view.findViewById(R.id.tv_status)

        val itemRow: ComposeItemRow = view.findViewById(R.id.greeting) as ComposeItemRow

        override fun onClick(v: View) {
            mClickListener.onClick(bindingAdapterPosition, v)
        }
        init {
            view.setOnClickListener(this)
        }

    }

    class ComposeItemRow @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
    ) : AbstractComposeView(context, attrs, defStyle) {
        var index by mutableStateOf(0)
        var rowState: LazyListState? by mutableStateOf(null)

        @Composable
        override fun Content() {
//            ItemRow(index, rowState!!)
        }
    }
}