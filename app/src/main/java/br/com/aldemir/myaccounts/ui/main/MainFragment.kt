package br.com.aldemir.myaccounts.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.domain.model.Expense
import br.com.aldemir.myaccounts.databinding.MainFragmentBinding
import br.com.aldemir.myaccounts.domain.model.MonthlyPayment
import br.com.aldemir.myaccounts.ui.main.adapter.MainAdapter
import br.com.aldemir.myaccounts.util.Constants
import br.com.aldemir.myaccounts.util.getNavOptions
import br.com.aldemir.myaccounts.util.navigateWithAnimations
import br.com.aldemir.myaccounts.util.toCurrency
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialFadeThrough
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Double.NaN
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

@AndroidEntryPoint
class MainFragment : Fragment(), MainAdapter.ClickListener {

    companion object {
        private const val TAG = "mainFragment"
    }

    private lateinit var adapter: MainAdapter
    private var _list = ArrayList<Expense>()
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var mContext: Context
    private lateinit var _month: String
    private lateinit var _year: String
    private var _valueTotal: Double = 0.0

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        exitTransition = MaterialFadeThrough()
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        return if (enter) {
            AnimationUtils.loadAnimation(context, R.anim.slide_in_left)
        } else {
            AnimationUtils.loadAnimation(context, R.anim.slide_out_left)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getMonthAndYear()

        showLoading()

        setupRecyclerView(_list)

        getAllExpenses()

        getAllMonthExpenses()

        listenersViewModel()

        val trashBinIcon = ContextCompat.getDrawable(mContext, R.drawable.ic_delete)

        val myCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                dialogDeleteExpense(viewHolder.bindingAdapterPosition)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                c.clipRect(
                    0f, viewHolder.itemView.top.toFloat(),
                    dX, viewHolder.itemView.bottom.toFloat()
                )

                if (dX < 100 / 3)
                    c.drawColor(Color.GRAY)
                else
                    c.drawColor(ContextCompat.getColor(mContext, R.color.red))

                val textMargin = resources.getDimension(R.dimen.text_margin)
                    .roundToInt()

                trashBinIcon!!.bounds = Rect(
                    textMargin,
                    viewHolder.itemView.top + textMargin,
                    textMargin + trashBinIcon.intrinsicWidth,
                    viewHolder.itemView.top + trashBinIcon.intrinsicHeight
                            + textMargin
                )
                trashBinIcon.draw(c)

                super.onChildDraw(
                    c, recyclerView, viewHolder,
                    dX, dY, actionState, isCurrentlyActive
                )
            }
        }
        val myHelper = ItemTouchHelper(myCallback)
        myHelper.attachToRecyclerView(binding.recyclerViewListAccounts)
    }

    @SuppressLint("SetTextI18n")
    private fun listenersViewModel() {
        viewModel.accounts.observe(viewLifecycleOwner, { accounts ->
            hideLoading()
            if (accounts.isNotEmpty()) {
                _list = accounts as ArrayList<Expense>
                adapter.updateList(_list)
            }
        })
        viewModel.idAccount.observe(viewLifecycleOwner, { id ->
            if (id > 0) {
                Toast.makeText(mContext, "Item excluído com sucesso!", Toast.LENGTH_SHORT).show()
                viewModel.setId(0)
                getAllMonthExpenses()
            }
        })

        viewModel.monthExpenses.observe(viewLifecycleOwner, { expensesMonths ->
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
            val percentage = (paidOut / _valueTotal) * 100
            Log.d(TAG, "Porcentagem: $percentage %")
            if(!percentage.isNaN()){
                binding.progressValue.progress = percentage.roundToInt()
                binding.tvProgressText.text = "${percentage.roundToInt()} %"
            }
            binding.tvTotalMonth.text = _valueTotal.toCurrency()
            binding.tvPaidOut.text = paidOut.toCurrency()
            binding.tvPayable.text = pending.toCurrency()
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    private fun getAllExpenses() {
        viewModel.getAll()
    }

    private fun getAllMonthExpenses() {
        viewModel.getAllExpensesMonth(_month, _year)
    }

    private fun setupRecyclerView(list: MutableList<Expense>) {
        adapter = MainAdapter(list)
        binding.recyclerViewListAccounts.adapter = adapter
        val layoutManager = LinearLayoutManager(mContext)
        binding.recyclerViewListAccounts.layoutManager = layoutManager
        adapter.setOnItemClickListener(this)
    }

    override fun onClick(position: Int, aView: View) {
        val bundle = Bundle()
        bundle.putInt(Constants.ID_EXPENSE.value, _list[position].id)
        bundle.putString(Constants.NAME_EXPENSE.value, _list[position].name)
        val navOptions = findNavController().getNavOptions(R.id.expenseDetail)
        findNavController().navigateWithAnimations(
            R.id.action_mainFragment_to_expenseDetailFragment,
            animation = navOptions,
            bundle = bundle
        )
    }


    private fun showLoading() {
        binding.loadingMain.visibility = View.VISIBLE
        binding.recyclerViewListAccounts.visibility = View.GONE
    }

    private fun hideLoading() {
        binding.loadingMain.visibility = View.GONE
        binding.recyclerViewListAccounts.visibility = View.VISIBLE
    }

    private fun getMonthAndYear() {
        val date = Calendar.getInstance()
        _year = date.get(Calendar.YEAR).toString()
        val sdf = SimpleDateFormat("MMMM", Locale.getDefault())
        val month = sdf.format(date.time)
        Log.d(TAG, "Mês -> $month")
        when (date.get(Calendar.MONTH)) {
            0 -> _month = "1 - Janeiro"
            1 -> _month = "2 - Fevereiro"
            2 -> _month = "3 - Março"
            3 -> _month = "4 - Abril"
            4 -> _month = "5 - Maio"
            5 -> _month = "6 - Junho"
            6 -> _month = "7 - Julho"
            7 -> _month = "8 - Agosto"
            8 -> _month = "9 - Setembro"
            9 -> _month = "10 - Outubro"
            10 -> _month = "11 - Novembro"
            11 -> _month = "12 - Dezembro"
        }
    }

    private fun dialogDeleteExpense(position: Int){
        val builder: AlertDialog.Builder = AlertDialog.Builder(mContext,
            R.style.AlertDialogCustom)
        builder.setTitle("Aviso")
        builder.setMessage("Deseja excluir esta despesa?")
        builder.setPositiveButton("Sim") { dialog, _ ->
            viewModel.delete(_list[position])
            _list.removeAt(position)
            adapter.notifyItemRemoved(position)
            dialog.dismiss()
        }
        builder.setNegativeButton("Não") { dialog, _ ->
            adapter.notifyItemChanged(position)
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