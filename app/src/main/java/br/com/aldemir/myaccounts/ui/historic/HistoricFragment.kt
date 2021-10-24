package br.com.aldemir.myaccounts.ui.historic

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.databinding.HistoricFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoricFragment : Fragment() {

    companion object {
        fun newInstance() = HistoricFragment()
        private const val TAG = "HistoricFragment"
    }

    private var _binding: HistoricFragmentBinding? = null
    private val binding get() = _binding!!
    private val years = mutableListOf<String>()

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

    }

    private fun setDropDownYear() {
        val adapter = ArrayAdapter(requireContext(), R.layout.item_dropmenu, years.sorted())
        binding.dropMenuYearHistoric.setAdapter(adapter)
        binding.dropMenuYearHistoric.setDropDownBackgroundDrawable(
            ColorDrawable(ContextCompat.getColor(requireContext(), R.color.white))
        )
    }

    private fun listenersViewModel() {
        viewModel.monthlyPayment.observe(viewLifecycleOwner, {monthlyPayments ->
            if (monthlyPayments.isNotEmpty()) {
                for (item in monthlyPayments.distinctBy { it.year }) {
                    Log.d(TAG, "Ano: ${item.year}")
                    years.add(item.year)
                    setDropDownYear()
                }
            }
        })
    }


}