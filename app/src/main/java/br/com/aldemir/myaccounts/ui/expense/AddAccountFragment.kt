package br.com.aldemir.myaccounts.ui.expense

import android.content.Context
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.data.database.ConfigDataBase
import br.com.aldemir.myaccounts.data.repository.ExpenseRepository
import br.com.aldemir.myaccounts.data.repository.ExpenseRepositoryImpl
import br.com.aldemir.myaccounts.data.repository.MonthlyPaymentRepositoryImpl
import br.com.aldemir.myaccounts.databinding.AddAccountFragmentBinding
import br.com.aldemir.myaccounts.domain.usecase.AddExpenseUseCase
import br.com.aldemir.myaccounts.util.CurrencyTextWatcher
import br.com.aldemir.myaccounts.util.fromCurrency
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class AddAccountFragment : Fragment() {

    companion object {
        private const val TAG = "AddAccountFragment"
    }
    private var _binding: AddAccountFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AddAccountViewModel
    private lateinit var mContext: Context
    private var months: MutableList<String> = mutableListOf()
    private var isEntry = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =  AddAccountFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()

        viewModel = ViewModelProvider(this).get(AddAccountViewModel::class.java)

        setupListeners(view)

        collectFlow()

        listenersViewModel()

        val items = listOf("2021", "2022", "2023", "2024" , "2025")
        val adapter = ArrayAdapter(requireContext(), R.layout.item_dropmenu, items)
        binding.dropMenuYear.setAdapter(adapter)
        binding.dropMenuYear.setDropDownBackgroundDrawable(
            ColorDrawable(ContextCompat.getColor(mContext, R.color.white)))

    }

    private fun setupListeners(view: View) {
        binding.edtValue.run {
            addTextChangedListener(CurrencyTextWatcher(this))
        }

        binding.edtName.addTextChangedListener {
            viewModel.setName(it.toString())
        }
        binding.edtValue.addTextChangedListener {
            if (it.toString() != "") {
                viewModel.setValue(it.toString().fromCurrency())
            }
        }
        binding.edtDescription.addTextChangedListener {
            viewModel.setDescription(it.toString())
        }
        binding.dropMenuYear.addTextChangedListener {
            viewModel.setYear(it.toString())
        }
        binding.checkboxJan.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                months.add("1 - Janeiro")
            } else {
                months.remove("1 - Janeiro")
            }
            updateMonths()
        }
        binding.checkboxFeb.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                months.add("2 - Fevereiro")
            } else {
                months.remove("2 - Fevereiro")
            }
            updateMonths()
        }
        binding.checkboxMar.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                months.add("3 - Março")
            } else {
                months.remove("3 - Março")
            }
            updateMonths()
        }
        binding.checkboxApr.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                months.add("4 - Abril")
            } else {
                months.remove("4 - Abril")
            }
            updateMonths()
        }
        binding.checkboxMai.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                months.add("5 - Maio")
            } else {
                months.remove("5 - Maio")
            }
            updateMonths()
        }
        binding.checkboxJun.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                months.add("6 - Junho")
            } else {
                months.remove("6 - Junho")
            }
            updateMonths()
        }
        binding.checkboxJul.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                months.add("7 - Julho")
            } else {
                months.remove("7 - Julho")
            }
            updateMonths()
        }
        binding.checkboxAgo.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                months.add("8 - Agosto")
            } else {
                months.remove("8 - Agosto")
            }
            updateMonths()
        }
        binding.checkboxSep.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                months.add("9 - Setembro")
            } else {
                months.remove("9 - Setembro")
            }
            updateMonths()
        }
        binding.checkboxOut.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                months.add("10 - Outubro")
            } else {
                months.remove("10 - Outubro")
            }
            updateMonths()
        }
        binding.checkboxNov.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                months.add("11 - Novembro")
            } else {
                months.remove("11 - Novembro")
            }
            updateMonths()
        }
        binding.checkboxDez.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                months.add("12 - Dezembro")
            } else {
                months.remove("12 - Dezembro")
            }
            updateMonths()
        }


        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val radio: RadioButton = view.findViewById(checkedId)
            when (radio) {
                binding.radioButton1 -> {
                    isEntry = true
                }
                binding.radioButton2 -> {
                    isEntry = false
                }
            }
        }

        binding.btnAddAccount.setOnClickListener {
            showLoading()
            viewModel.insertExpense(
                binding.edtName.text.toString(),
                binding.edtDescription.text.toString(),
                binding.dropMenuYear.text.toString(),
                months.sortedBy { "s(\\d+)".toRegex().matchEntire(it)?.groups?.get(1)?.value?.toInt()  },
                binding.edtValue.text.toString().fromCurrency(),
                false
            )
        }
    }

    private fun updateMonths() {
        viewModel.setMonths(months)
    }

    private fun collectFlow() {
        lifecycleScope.launch {
            viewModel.isSubmitEnabled.collect { value ->
                isEnabled(value)
            }
        }
    }

    private fun isEnabled(enabled: Boolean) {
        binding.btnAddAccount.isEnabled = enabled
//        if (enabled) {
//            mButtonCreateAccount.setBackgroundResource(R.drawable.bg_bottom_blue)
//        } else {
//            mButtonCreateAccount.setBackgroundResource(R.drawable.bg_bottom_disabled)
//        }
    }

    private fun getAllAccounts() {
//        viewModel.getAll()
    }

    override fun onResume() {
        super.onResume()
        getAllAccounts()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    private fun listenersViewModel() {
        viewModel.id.observe(viewLifecycleOwner, { id ->
            hideLoading()
            if(id > 0) {
                Log.d(TAG, "Expense id: $id")
                findNavController().popBackStack()
            }
        })
        viewModel.addAccountFormState.observe(viewLifecycleOwner, { formState ->

            if (formState.nameError != null) {
                binding.edtName.error = getString(formState.nameError)
            }else {
                binding.edtName.error = null
            }
            if (formState.valueError != null) {
                binding.edtValue.error = getString(formState.valueError)
            }else {
                binding.edtValue.error = null
            }
            if (formState.descriptionError != null) {
                binding.edtDescription.error = getString(formState.descriptionError)
            }else {
                binding.edtDescription.error = null
            }
        })
    }

    private fun setupViewModel() {
        val database = ConfigDataBase.getDataBase(mContext)
        val expenseRepository = ExpenseRepositoryImpl(database.expenseDao())
        val monthlyPaymentRepository = MonthlyPaymentRepositoryImpl(database.monthlyPaymentDao())
        viewModel  = ViewModelProvider(this,
            AddAccountViewModelFactory(
                expenseRepository,
                monthlyPaymentRepository
            )
        ).get(AddAccountViewModel::class.java)
    }

    private fun showLoading() {
        binding.loadingAdd.visibility = View.VISIBLE
        binding.btnAddAccount.isEnabled = false
    }

    private fun hideLoading() {
        binding.loadingAdd.visibility = View.GONE
        binding.btnAddAccount.isEnabled = true
    }

}