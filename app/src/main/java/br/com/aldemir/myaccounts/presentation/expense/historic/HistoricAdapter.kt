package br.com.aldemir.myaccounts.presentation.expense.historic

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.aldemir.myaccounts.databinding.ItemListHistoricBinding
import br.com.aldemir.myaccounts.domain.model.ExpensePerMonth
import br.com.aldemir.myaccounts.util.toCurrency
import java.text.SimpleDateFormat
import java.util.*


class HistoricAdapter(
    private var values: MutableList<ExpensePerMonth>,
) : RecyclerView.Adapter<HistoricAdapter.ViewHolder>() {

    lateinit var mClickListener: ClickListener
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListHistoricBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }

    interface ClickListener {
        fun onClick(position: Int, aView: View)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(values[position])


    fun updateList(list: MutableList<ExpensePerMonth>) {
        values = list
        notifyDataSetChanged()
    }

    private fun dateFormat(date: Date): String {
        val myFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        return sdf.format(date)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(private val binding: ItemListHistoricBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        fun bind(item: ExpensePerMonth) {
            with(binding) {
                tvNameAccount.text = item.name
                tvValueAccount.text = item.description
                tvDueDate.text = String.format("%02d", item.due_date)
                tvExpenseValue.text = "${item.value.toCurrency()}"
            }
        }

        override fun onClick(v: View) {
            mClickListener.onClick(adapterPosition, v)
        }
        init {
            binding.root.setOnClickListener(this)
        }

    }
}