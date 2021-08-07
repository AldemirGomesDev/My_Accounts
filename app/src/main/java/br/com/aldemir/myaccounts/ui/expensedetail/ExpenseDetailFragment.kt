package br.com.aldemir.myaccounts.ui.expensedetail

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.aldemir.myaccounts.data.database.ConfigDataBase
import br.com.aldemir.myaccounts.data.domain.model.MonthlyPayment
import br.com.aldemir.myaccounts.databinding.FragmentExpenseDetailBinding


class ExpenseDetailFragment : Fragment() {

    private lateinit var adapter: ExpenseDetailAdapter
    private var _binding: FragmentExpenseDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var mContext: Context
    private lateinit var viewModel: ExpenseDetailViewModel
    private var list = listOf<MonthlyPayment>()
    private var idExpense = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            idExpense = it.getInt("idExpense")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =  FragmentExpenseDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()

        viewModel = ViewModelProvider(this).get(ExpenseDetailViewModel::class.java)

        viewModel.getAllByIdExpense(idExpense)

        setupRecyclerView(list)

        listenersViewModel()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onResume() {
        super.onResume()
    }

    private fun setupRecyclerView(list: List<MonthlyPayment>) {
        adapter = ExpenseDetailAdapter(list)
        binding.detailRecyclerview.adapter = adapter
        val layoutManager = LinearLayoutManager(mContext)
        binding.detailRecyclerview.layoutManager = layoutManager
        binding.detailRecyclerview.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )
    }


    private fun setupViewModel() {
        val database = ConfigDataBase.getDataBase(mContext)
        viewModel  = ViewModelProvider(this,
            ExpenseDetailViewModelFactory(
                database.monthlyPaymentDao()
            )
        ).get(ExpenseDetailViewModel::class.java)
    }

    private fun listenersViewModel() {
        viewModel.monthlyPayment.observe(viewLifecycleOwner, { monthlyPayments ->
            Log.d(TAG, "Expense id: ${monthlyPayments[0]}")
            adapter.updateList(monthlyPayments)
            if (monthlyPayments.isNotEmpty()) {

            }
        })
    }
    companion object {
        private const val TAG = "ExpenseDetailFragment"
        const val ARG_COLUMN_COUNT = "column-count"

    }
}