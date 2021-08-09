package br.com.aldemir.myaccounts.ui.expensechange

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.data.database.ConfigDataBase
import br.com.aldemir.myaccounts.data.domain.model.MonthlyPayment
import br.com.aldemir.myaccounts.databinding.ChangeExpenseFragmentBinding
import br.com.aldemir.myaccounts.databinding.FragmentExpenseDetailBinding
import br.com.aldemir.myaccounts.ui.expense.AddAccountFragment
import br.com.aldemir.myaccounts.util.CurrencyTextWatcher
import br.com.aldemir.myaccounts.util.fromCurrency
import br.com.aldemir.myaccounts.util.toCurrency

class ChangeExpenseFragment : Fragment() {

    companion object {
        private const val TAG = "ChangeDetailFragment"
    }
    private var idMonthlyPayment = 0
    private lateinit var nameExpense: String
    private lateinit var mContext: Context
    private lateinit var viewModel: ChangeExpenseViewModel
    private var _binding: ChangeExpenseFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var mMonthlyPayment: MonthlyPayment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            idMonthlyPayment = it.getInt("idMonthlyPayment")
            nameExpense = it.getString("nameExpense")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =  ChangeExpenseFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvTitleChangeExpense.text = nameExpense

        setupViewModel()

        viewModel = ViewModelProvider(this).get(ChangeExpenseViewModel::class.java)

        setupListeners()

        viewModel.getAllByIdMonthlyPayment(idMonthlyPayment)

        listenersViewModel()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    private fun setupListeners() {
        binding.edtValueChange.run {
            addTextChangedListener(CurrencyTextWatcher(this))
        }

        binding.edtValueChange.addTextChangedListener {
            if (it.toString() != "") {
                viewModel.setValue(it.toString().fromCurrency())
            }
        }

        binding.btnChangeMonthlyPayment.setOnClickListener {
            showLoading()
            mMonthlyPayment.value = binding.edtValueChange.text.toString().fromCurrency()
            viewModel.updateMonthlyPayment(mMonthlyPayment)
        }
    }

    private fun setupViewModel() {
        val database = ConfigDataBase.getDataBase(mContext)
        viewModel  = ViewModelProvider(this,
            ChangeExpenseViewModelFactory(
                database.monthlyPaymentDao()
            )
        ).get(ChangeExpenseViewModel::class.java)
    }

    @SuppressLint("SetTextI18n")
    private fun listenersViewModel() {
        viewModel.monthlyPayment.observe(viewLifecycleOwner, { monthlyPayment ->
            if (monthlyPayment != null) {
                mMonthlyPayment = monthlyPayment
                Log.d(TAG, "result -> $monthlyPayment")
                binding.tvDatesChange.text = "${monthlyPayment.month} de ${monthlyPayment.year}"
                binding.edtValueChange.setText("${monthlyPayment.value.toCurrency()}")
            }
        })

        viewModel.id.observe(viewLifecycleOwner, { id ->
            hideLoading()
            if(id > 0) {
                findNavController().popBackStack()
            }
        })

        viewModel.changeFormState.observe(viewLifecycleOwner, { formState ->
            if (formState.valueError != null) {
                binding.edtValueChange.error = getString(formState.valueError)
            }else {
                binding.edtValueChange.error = null
            }
        })

        viewModel.isValidForm.observe(viewLifecycleOwner, { isValid ->
            binding.btnChangeMonthlyPayment.isEnabled = isValid
        })
    }

    private fun showLoading() {
        binding.loadingChange.visibility = View.VISIBLE
        binding.btnChangeMonthlyPayment.isEnabled = false
    }

    private fun hideLoading() {
        binding.loadingChange.visibility = View.GONE
        binding.btnChangeMonthlyPayment.isEnabled = true
    }

}