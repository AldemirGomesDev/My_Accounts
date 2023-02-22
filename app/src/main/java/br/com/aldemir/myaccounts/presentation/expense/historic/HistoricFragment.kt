package br.com.aldemir.myaccounts.presentation.expense.historic

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.databinding.HistoricFragmentBinding
import br.com.aldemir.myaccounts.domain.model.ExpensePerMonth
import br.com.aldemir.myaccounts.domain.model.MonthlyPayment
import br.com.aldemir.myaccounts.util.toCurrency
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoricFragment : Fragment(), HistoricAdapter.ClickListener {

    companion object {
        fun newInstance() = HistoricFragment()
        private const val TAG = "HistoricFragment"
    }

    private var _binding: HistoricFragmentBinding? = null
    private val binding get() = _binding!!
    private var _list = ArrayList<ExpensePerMonth>()
    private val years = mutableListOf<String>()
    private var _months = mutableListOf<String>()
    private var _monthlyPayments = mutableListOf<MonthlyPayment>()
    private lateinit var _month: String
    private lateinit var _year: String
    private lateinit var mContext: Context
    private lateinit var adapter: HistoricAdapter
    private var _valueTotal: Double = 0.0

    private val viewModel: HistoricViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HistoricFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllMonthlyPayment()

        listenersViewModel()

        binding.dropMenuYearHistoric.addTextChangedListener { year ->
            binding.dropMenuMonthHistoric.setText("")
            binding.tvTotalMonth.visibility = View.GONE
            _months = mutableListOf()
            val months = mutableListOf<String>()
            if (year.toString().isNotEmpty()) {
                _year = year.toString()
                for (monthlyPayment in _monthlyPayments) {
                    if (_year == monthlyPayment.year) {
                        months.add(monthlyPayment.month)
                    }
                }
                _months.addAll(months.distinct())
                setDropDownMonth()
                binding.tilMonthHistoric.visibility = View.VISIBLE
                binding.recyclerViewListHistoric.visibility = View.GONE
            } else {
                binding.tilMonthHistoric.visibility = View.GONE
            }
        }

        binding.dropMenuMonthHistoric.addTextChangedListener {
            if (it.toString().isNotEmpty()) {
                _month = it.toString()
                getAllMonthExpenses()
            }
        }

        setupRecyclerView(_list)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    private fun setupRecyclerView(list: MutableList<ExpensePerMonth>) {
        adapter = HistoricAdapter(list)
        binding.recyclerViewListHistoric.adapter = adapter
        val layoutManager = LinearLayoutManager(mContext)
        binding.recyclerViewListHistoric.layoutManager = layoutManager
        adapter.setOnItemClickListener(this)
    }

    private fun setDropDownYear() {
        val adapter = ArrayAdapter(requireContext(), R.layout.item_dropmenu, years.sorted())
        binding.dropMenuYearHistoric.setAdapter(adapter)
        binding.dropMenuYearHistoric.setDropDownBackgroundDrawable(
            ColorDrawable(ContextCompat.getColor(requireContext(), R.color.white))
        )
    }

    private fun setDropDownMonth() {
        val pattern = """\d+""".toRegex()
        val list = _months.sortedWith(compareBy {
            pattern.find(it)?.value?.toInt() ?: 0
        })
        val adapter = ArrayAdapter(requireContext(), R.layout.item_dropmenu, list)
        binding.dropMenuMonthHistoric.setAdapter(adapter)
        binding.dropMenuMonthHistoric.setDropDownBackgroundDrawable(
            ColorDrawable(ContextCompat.getColor(requireContext(), R.color.white))
        )
    }

    @SuppressLint("SetTextI18n")
    private fun listenersViewModel() {
        viewModel.monthlyPayment.observe(viewLifecycleOwner) { monthlyPayments ->
            if (monthlyPayments.isNotEmpty()) {
                _monthlyPayments = monthlyPayments as MutableList<MonthlyPayment>
                for (item in monthlyPayments.distinctBy { it.year }) {
                    Log.d(TAG, "Ano: ${item.year}")
                    years.add(item.year)
                }
                setDropDownYear()
            }
        }

        viewModel.expensePerMonth.observe(viewLifecycleOwner) { expensesMonths ->
            if (expensesMonths.isNotEmpty()) {
                binding.recyclerViewListHistoric.visibility = View.VISIBLE
                _list = expensesMonths as ArrayList<ExpensePerMonth>
                adapter.updateList(_list)

                _valueTotal = 0.0
                var paidOut = 0.0
                var pending = 0.0
                for (item in expensesMonths) {
                    _valueTotal += item.value
                    if (item.situation) {
                        paidOut += item.value
                    } else {
                        pending += item.value
                    }
                }
                binding.tvTotalMonth.visibility = View.VISIBLE
                binding.tvTotalMonth.text = "Total: ${_valueTotal.toCurrency()}"
                Log.d(TAG, "Total de despesas do mês: ${_valueTotal.toCurrency()}")
                Log.d(TAG, "Total pagas: ${paidOut.toCurrency()}")
                Log.d(TAG, "Total pendentes: ${pending.toCurrency()}")
            }

        }
    }

    private fun getAllMonthExpenses() {
        viewModel.getAllExpensePerMonth(_month, _year)
    }

    override fun onClick(position: Int, aView: View) {
        Log.d(TAG, "Nome: ${_list[position].name}")
    }
}