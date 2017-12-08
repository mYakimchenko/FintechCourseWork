package com.mihanjk.fintechCurrencyExchange.view.currencyExchange

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mihanjk.fintechCurrencyExchange.R
import kotlinx.android.synthetic.main.fragment_currency_exchange.view.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CurrencyExchangeFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CurrencyExchangeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CurrencyExchangeFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private lateinit var mFirstCurrency: String
    private lateinit var mSecondCurrency: String

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mFirstCurrency = (arguments.getString(FIRST_CURRENCY))
            mSecondCurrency = (arguments.getString(SECOND_CURRENCY))
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_currency_exchange, container, false)
        view.firstCurrency.text = mFirstCurrency
        view.secondCurrency.text = mSecondCurrency
        return view
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        private val FIRST_CURRENCY = "first"
        private val SECOND_CURRENCY = "second"

        fun newInstance(firstCurrency: String, secondCurrency: String):
                CurrencyExchangeFragment {
            val fragment = CurrencyExchangeFragment()
            val args = Bundle()
            args.putString(FIRST_CURRENCY, firstCurrency)
            args.putString(SECOND_CURRENCY, secondCurrency)
            fragment.arguments = args
            return fragment
        }
    }
}
