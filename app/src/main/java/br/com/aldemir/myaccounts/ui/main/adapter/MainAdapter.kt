package br.com.aldemir.myaccounts.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.data.database.Account
import com.google.android.material.card.MaterialCardView


class MainAdapter(
    private var values: MutableList<Account>,
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    lateinit var mClickListener: ClickListener
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_accounts, parent, false)
        return ViewHolder(view)
    }

    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }

    interface ClickListener {
        fun onClick(position: Int, aView: View)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.nameAccount.text = item.name
        holder.valueAccount.text = "${item.value}"
        if (item.type) {
            holder.typeAccount.text = "Entrada"
            holder.typeAccount.setTextColor(ContextCompat.getColor(context, R.color.green))
        }else {
            holder.typeAccount.text = "Saída"
            holder.typeAccount.setTextColor(ContextCompat.getColor(context, R.color.red))
        }
    }

    fun updateList(list: MutableList<Account>) {
        values = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val containerItem: MaterialCardView = view.findViewById(R.id.material_cardView)
        val nameAccount: TextView = view.findViewById(R.id.tv_name_account)
        val valueAccount: TextView = view.findViewById(R.id.tv_value_account)
        val typeAccount: TextView = view.findViewById(R.id.tv_type_account)

        override fun onClick(v: View) {
            mClickListener.onClick(adapterPosition, v)
        }
        init {
            view.setOnClickListener(this)
        }

    }
}