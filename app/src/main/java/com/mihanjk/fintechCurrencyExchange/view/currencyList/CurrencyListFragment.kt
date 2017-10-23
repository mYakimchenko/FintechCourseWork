package com.mihanjk.fintechCurrencyExchange.view.currencyList

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mihanjk.fintechCurrencyExchange.R
import com.mihanjk.fintechCurrencyExchange.model.data.Currency
import com.mihanjk.fintechCurrencyExchange.model.data.ForeignItem

class CurrencyListFragment : Fragment() {
    private var mListener: OnListFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        if (arguments != null) {
//            mColumnCount = arguments.getInt(ARG_COLUMN_COUNT)
//        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_currency_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            val context = view.context
            view.layoutManager = LinearLayoutManager(context)
            val values = mutableListOf<ForeignItem>()
            Currency.values().forEach { values.add(ForeignItem(it, true)) }
            view.adapter = CurrencyRecyclerViewAdapter(values,
                    mListener)
        }
        return view
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnListFragmentInteractionListener {
        fun onStarClicked(item: ForeignItem)
    }

    companion object {

        // TODO: Customize parameter initialization
        fun newInstance(): CurrencyListFragment {
            val fragment = CurrencyListFragment()
//            val args = Bundle()
//            args.putInt(ARG_COLUMN_COUNT, columnCount)
//            fragment.arguments = args
            return fragment
        }
    }
}
