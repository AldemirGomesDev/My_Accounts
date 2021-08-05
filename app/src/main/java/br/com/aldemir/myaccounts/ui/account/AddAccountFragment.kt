package br.com.aldemir.myaccounts.ui.account

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.data.database.Account
import br.com.aldemir.myaccounts.data.database.AccountDao
import br.com.aldemir.myaccounts.data.database.ConfigDataBase
import br.com.aldemir.myaccounts.databinding.AddAccountFragmentBinding
import br.com.aldemir.myaccounts.databinding.MainFragmentBinding
import br.com.aldemir.myaccounts.ui.main.MainFragment
import br.com.aldemir.myaccounts.util.CurrencyTextWatcher

class AddAccountFragment : Fragment() {

    companion object {
        private const val TAG = "AddAccountFragment"
    }
    private var _binding: AddAccountFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AddAccountViewModel
    private lateinit var mContext: Context
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

        listenersViewModel()

    }

    private fun setupListeners(view: View) {
        binding.edtValue.run {
            addTextChangedListener(CurrencyTextWatcher(this))
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
            viewModel.insertAccount(Account(
                id = 0,
                name = binding.edtName.text.toString(),
                description = binding.edtDescription.text.toString(),
                value = binding.edtValue.text.toString(),
                type = isEntry
            ))
        }
    }

    private fun getAllAccounts() {
        viewModel.getAll()
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
        viewModel.id.observe(viewLifecycleOwner, Observer {id ->
            hideLoading()
            if(id > 0) {
                Log.d(TAG, "Account id: $id")
                findNavController().popBackStack()
            }
        })
    }

    private fun setupViewModel() {
        val database = ConfigDataBase.getDataBase(mContext)
        viewModel  = ViewModelProvider(this,
            AddAccountViewModelFactory(database.accountDao())
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