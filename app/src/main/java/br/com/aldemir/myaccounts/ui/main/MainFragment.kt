package br.com.aldemir.myaccounts.ui.main

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.data.database.Account
import br.com.aldemir.myaccounts.data.database.ConfigDataBase
import br.com.aldemir.myaccounts.databinding.MainFragmentBinding
import br.com.aldemir.myaccounts.ui.main.adapter.MainAdapter
import br.com.aldemir.myaccounts.util.getNavOptions
import br.com.aldemir.myaccounts.util.navigateWithAnimations

class MainFragment : Fragment(), MainAdapter.ClickListener {

    companion object {
        private const val TAG = "mainFragment"
    }
    private lateinit var adapter: MainAdapter
    private var list = ArrayList<Account>()
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var mContext: Context

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading()

        setupViewModel()

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        setListeners()
        list = arrayListOf()
        setupRecyclerView(list)

        getAllAccounts()

        listenersViewModel()
    }

    private fun listenersViewModel() {
        viewModel.accounts.observe(viewLifecycleOwner, Observer {accounts ->
            hideLoading()
            if(accounts.isNotEmpty()) {
                Log.d(TAG, "Account: ${accounts.size}")
                adapter.updateList(accounts.toMutableList())
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    private fun getAllAccounts() {
        viewModel.getAll()
    }

    private fun setListeners() {
        binding.floatButtonAdd.setOnClickListener {
            val navOptions = findNavController().getNavOptions(R.id.addAccount)
            findNavController().navigateWithAnimations(R.id.action_mainFragment_to_addFragment, animation = navOptions)
        }
    }

    private fun setupRecyclerView(list: MutableList<Account>) {
        adapter = MainAdapter(list)
        binding.recyclerViewListAccounts.adapter = adapter
        val layoutManager = LinearLayoutManager(mContext)
        binding.recyclerViewListAccounts.layoutManager = layoutManager
        adapter.setOnItemClickListener(this)
    }

    override fun onClick(position: Int, aView: View) {
        Log.d(TAG, "clicado: $position")
    }

    private fun setupViewModel() {
        val database = ConfigDataBase.getDataBase(mContext)
        viewModel  = ViewModelProvider(this,
            MainViewModelFactory(database.accountDao())
        ).get(MainViewModel::class.java)
    }

    private fun showLoading() {
        binding.loadingMain.visibility = View.VISIBLE
        binding.recyclerViewListAccounts.visibility = View.GONE
    }

    private fun hideLoading() {
        binding.loadingMain.visibility = View.GONE
        binding.recyclerViewListAccounts.visibility = View.VISIBLE
    }

}