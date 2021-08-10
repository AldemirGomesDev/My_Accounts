package br.com.aldemir.myaccounts.ui.expensedetail

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.data.database.ConfigDataBase
import br.com.aldemir.myaccounts.data.repository.MonthlyPaymentRepositoryImpl
import br.com.aldemir.myaccounts.domain.model.MonthlyPayment
import br.com.aldemir.myaccounts.databinding.FragmentExpenseDetailBinding
import br.com.aldemir.myaccounts.util.getNavOptions
import br.com.aldemir.myaccounts.util.navigateWithAnimations


class ExpenseDetailFragment : Fragment(), ExpenseDetailAdapter.ClickListener {

    private lateinit var adapter: ExpenseDetailAdapter
    private var _binding: FragmentExpenseDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var mContext: Context
    private lateinit var viewModel: ExpenseDetailViewModel
    private var list = listOf<MonthlyPayment>()
    private var idExpense = 0
    private lateinit var nameExpense: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            idExpense = it.getInt("idExpense")
            nameExpense = it.getString("nameExpense")!!
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

        binding.tvTitleExpense.text = nameExpense

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
        adapter.setOnItemClickListener(this)
    }


    private fun setupViewModel() {
        val database = ConfigDataBase.getDataBase(mContext)
        val monthlyPaymentRepository = MonthlyPaymentRepositoryImpl(database.monthlyPaymentDao())
        viewModel  = ViewModelProvider(this,
            ExpenseDetailViewModelFactory(
                monthlyPaymentRepository
            )
        ).get(ExpenseDetailViewModel::class.java)
    }

    private fun listenersViewModel() {
        viewModel.monthlyPayment.observe(viewLifecycleOwner, { monthlyPayments ->
            val pattern = """\d+""".toRegex()
            if (monthlyPayments.isNotEmpty()) {
                list = monthlyPayments.sortedWith(compareBy {
                    pattern.find(it.month)?.value?.toInt() ?: 0
                })
                adapter.updateList(list)
            }
        })

        viewModel.id.observe(viewLifecycleOwner, {id ->
            if (id > 0) {
                adapter.notifyDataSetChanged()
            }
        })
    }
    companion object {
        private const val TAG = "ExpenseDetailFragment"
    }

    override fun onClick(position: Int, aView: View) {
        val monthlyPayment = list[position]
        monthlyPayment.situation = true
        dialogUpdateExpense(monthlyPayment)
    }

    override fun onLongClick(position: Int, aView: View) {
        Log.d(TAG, "clicou em clique longo: $position")
        val bundle = Bundle()
        bundle.putInt("idMonthlyPayment", list[position].id)
        bundle.putString("nameExpense", nameExpense)
        val navOptions = findNavController().getNavOptions(R.id.expenseChange)
        findNavController().navigateWithAnimations(
            R.id.action_mainFragment_to_expenseChange, animation = navOptions, bundle = bundle)
    }

    private fun dialogUpdateExpense(monthlyPayment: MonthlyPayment){
        val builder: AlertDialog.Builder = AlertDialog.Builder(mContext,
            R.style.AlertDialogCustom)
        builder.setTitle("Aviso")
        builder.setMessage("Deseja marcar essa despesa como paga?")
        builder.setPositiveButton("Sim") { dialog, _ ->
            viewModel.updateMonthlyPayment(monthlyPayment)
            dialog.dismiss()
        }
        builder.setNegativeButton("Não") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
        val buttonYes = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
        val buttonNo = dialog.getButton(DialogInterface.BUTTON_NEGATIVE)
        with(buttonYes) {
            setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_200))
        }
        with(buttonNo) {
            setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_200))
        }

        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)

    }
}