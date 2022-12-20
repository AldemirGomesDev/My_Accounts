package br.com.aldemir.myaccounts.presentation.main

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.core.view.doOnLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.domain.model.Expense
import br.com.aldemir.myaccounts.databinding.MainFragmentBinding
import br.com.aldemir.myaccounts.presentation.main.component.HandleListContent
import br.com.aldemir.myaccounts.presentation.theme.Purple700
import br.com.aldemir.myaccounts.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.util.*

private const val TAG = "mainFragment"

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var mContext: Context
    private var _valueTotal: Double = 0.0

    private val viewModel: MainViewModel by viewModels()

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

        view.doOnLayout {
            binding.greeting.layoutParams.height = view.height
            binding.greeting.requestLayout()
        }

        setContentCompose { CircularProgress(condition = true) }

        getAllMonthExpenses()

        getAllExpensesPerMonth()

        listenersViewModel()

    }

    @SuppressLint("SetTextI18n")
    private fun listenersViewModel() {
//        viewModel.idExpense.observe( viewLifecycleOwner) { id ->
//            if (id > 0) {
//                Toast.makeText(mContext, "Item excluído com sucesso!", Toast.LENGTH_SHORT).show()
//                viewModel.setId(0)
//                getAllMonthExpenses()
//                getAllExpensesPerMonth()
//            }
//        }

//        viewModel.monthExpenses.observe(viewLifecycleOwner) { expensesMonths ->
//            _valueTotal = 0.0
//            var paidOut = 0.0
//            var pending = 0.0
//            for (item in expensesMonths) {
//                _valueTotal += item.value
//                if (item.situation) {
//                    paidOut += item.value
//                } else {
//                    pending += item.value
//                }
//            }
//            val percentage = (paidOut / _valueTotal) * 100
//            if (!percentage.isNaN()) {
//                binding.progressValue.progress = percentage.roundToInt()
//                binding.tvProgressText.text = "${percentage.roundToInt()} %"
//            }
//            binding.tvTotalMonth.text = _valueTotal.toCurrency()
//            binding.tvPaidOut.text = paidOut.toCurrency()
//            binding.tvPayable.text = pending.toCurrency()
//        }

//        viewModel.expenses.observe(viewLifecycleOwner) { accounts ->
//            setContentCompose { CircularProgress(condition = false) }
//            setUpListContent(accounts as MutableList<Expense>)
//            Log.i(TAG, "listenersViewModel: ${accounts.size}")
//        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    private fun getAllMonthExpenses() {
        viewModel.getAllExpensesMonth(DateUtils.getMonth(), DateUtils.getYear())
    }

    private fun getAllExpensesPerMonth() {
        viewModel.getAllExpensePerMonth(DateUtils.getMonth(), DateUtils.getYear())
    }

    @Composable
    private fun handleHomeFragment() {

    }

    private fun setUpListContent(list: MutableList<Expense>) {
        setContentCompose { setListContent(accounts = list) }
    }

    @Composable
    fun setListContent(accounts: MutableList<Expense>) {

        val scaffoldState = rememberScaffoldState()

        HandleListContent(
            accounts = accounts,
            onSwipeToDelete = { task ->
                deleteExpense(task)
                scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
            },
            navigateToTaskScreen = { id, name ->
                navigateToDetail(id, name)
            }
        )
    }

    private fun setContentCompose(content: @Composable () -> Unit) {
        binding.greeting.setContent(content)
    }

    @Composable
    private fun CircularProgress(condition: Boolean) {
        val commentsAlpha = if (condition) 1f else 0f

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier.alpha(commentsAlpha),
                color = Purple700
            )
        }
    }

    private fun navigateToDetail(id: Int, name: String) {
        val bundle = Bundle()
        bundle.putInt(Constants.ID_EXPENSE.value, id)
        bundle.putString(Constants.NAME_EXPENSE.value, name)
        val navOptions = findNavController().getNavOptions(R.id.expenseDetail)
        findNavController().navigateWithAnimations(
            R.id.action_mainFragment_to_expenseDetailFragment,
            animation = navOptions,
            bundle = bundle
        )
    }

    private fun deleteExpense(expense: Expense) {
        CoroutineScope(Dispatchers.Default).launch {
            delay(300)
            viewModel.delete(expense)
        }
    }

}