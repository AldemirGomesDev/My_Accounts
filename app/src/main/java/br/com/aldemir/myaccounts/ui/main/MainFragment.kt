package br.com.aldemir.myaccounts.ui.main

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.data.database.Account
import br.com.aldemir.myaccounts.data.database.ConfigDataBase
import br.com.aldemir.myaccounts.databinding.MainFragmentBinding
import br.com.aldemir.myaccounts.ui.main.adapter.MainAdapter
import br.com.aldemir.myaccounts.util.getNavOptions
import br.com.aldemir.myaccounts.util.navigateWithAnimations
import com.google.android.material.snackbar.Snackbar
import kotlin.math.roundToInt

class MainFragment : Fragment(), MainAdapter.ClickListener {

    companion object {
        private const val TAG = "mainFragment"
    }
    private lateinit var adapter: MainAdapter
    private var _list = ArrayList<Account>()
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

        setupRecyclerView(_list)

        getAllAccounts()

        listenersViewModel()

        val trashBinIcon = ContextCompat.getDrawable(mContext,  R.drawable.ic_delete)

        val myCallback = object: ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.delete(_list[viewHolder.adapterPosition])
                _list.removeAt (viewHolder.adapterPosition)
                adapter.notifyItemRemoved (viewHolder.adapterPosition)
            }

            override fun onChildDraw (
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                c.clipRect (0f, viewHolder.itemView.top.toFloat (),
                    dX, viewHolder.itemView.bottom.toFloat ())

                if(dX < 800 / 3)
                    c.drawColor(Color.GRAY)
                else
                    c.drawColor(Color.RED)

                val textMargin = resources.getDimension(R.dimen.text_margin)
                    .roundToInt()

                trashBinIcon!!.bounds = Rect(
                    textMargin,
                    viewHolder.itemView.top + textMargin,
                    textMargin + trashBinIcon.intrinsicWidth,
                    viewHolder.itemView.top + trashBinIcon.intrinsicHeight
                            + textMargin)
                trashBinIcon.draw(c)

                super.onChildDraw (c, recyclerView, viewHolder,
                    dX, dY, actionState, isCurrentlyActive)
            }
        }
        val myHelper = ItemTouchHelper (myCallback)
        myHelper.attachToRecyclerView (binding.recyclerViewListAccounts)
    }

    private fun listenersViewModel() {
        viewModel.accounts.observe(viewLifecycleOwner, { accounts ->
            hideLoading()
            if(accounts.isNotEmpty()) {
                _list = accounts as ArrayList<Account>
                adapter.updateList(_list)
            }
        })
        viewModel.idAccount.observe(viewLifecycleOwner, {id ->
            if(id > 0) {
                val snack = view?.let { Snackbar.make(it,"Item excluído com sucesso!",Snackbar.LENGTH_LONG) }
                snack!!.show()
                viewModel.setId(0)
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