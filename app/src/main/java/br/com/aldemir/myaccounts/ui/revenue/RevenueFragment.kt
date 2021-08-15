package br.com.aldemir.myaccounts.ui.revenue

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.aldemir.myaccounts.R

class RevenueFragment : Fragment() {

    companion object {
        fun newInstance() = RevenueFragment()
    }

    private lateinit var viewModel: RevenueViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.revenue_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RevenueViewModel::class.java)
        // TODO: Use the ViewModel
    }

}