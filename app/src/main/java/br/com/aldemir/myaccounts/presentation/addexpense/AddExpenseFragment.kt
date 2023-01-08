package br.com.aldemir.myaccounts.presentation.addexpense

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.databinding.AddAccountFragmentBinding
import br.com.aldemir.myaccounts.util.CurrencyTextWatcher
import br.com.aldemir.myaccounts.util.fromCurrency
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils



@AndroidEntryPoint
class AddExpenseFragment : Fragment() {

    companion object {
        private const val TAG = "AddExpenseFragment"
    }
    private var _binding: AddAccountFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var mContext: Context
    private val days = mutableListOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =  AddAccountFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}