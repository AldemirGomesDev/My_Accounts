package br.com.aldemir.myaccounts.ui.expensedetail

import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.data.domain.model.Expense
import br.com.aldemir.myaccounts.data.domain.model.MonthlyPayment
import br.com.aldemir.myaccounts.databinding.ItemExpenseDetailBinding
import br.com.aldemir.myaccounts.util.toCurrency


class ExpenseDetailAdapter(
    private var values: List<MonthlyPayment>
) : RecyclerView.Adapter<ExpenseDetailAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            ItemExpenseDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    fun updateList(list: List<MonthlyPayment>) {
        val pattern = """\d+""".toRegex()
        values = list.sortedWith(compareBy {
            pattern.find(it.month)?.value?.toInt() ?: 0
        })
        notifyDataSetChanged()
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.dateView.text = "${item.month} de ${item.year}"
        holder.valueView.text = "Valor ${item.value.toCurrency()}"
        if (item.situation) {
            holder.situationView.text = "Pago"
            holder.situationView.setTextColor(ContextCompat.getColor(context, R.color.green))
        } else {
            holder.situationView.text = "Não pago"
            holder.situationView.setTextColor(ContextCompat.getColor(context, R.color.red))
        }

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: ItemExpenseDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val dateView: TextView = binding.tvDates
        val valueView: TextView = binding.tvValue
        val situationView: TextView = binding.tvSituation

        override fun toString(): String {
            return super.toString() + " '" + dateView.text + "'"
        }
    }

}