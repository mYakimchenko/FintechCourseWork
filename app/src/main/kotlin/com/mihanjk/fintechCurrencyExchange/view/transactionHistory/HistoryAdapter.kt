package com.mihanjk.fintechCurrencyExchange.view.transactionHistory

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mihanjk.fintechCurrencyExchange.R
import com.mihanjk.fintechCurrencyExchange.model.database.CurrencyTransaction
import kotlinx.android.synthetic.main.fragment_history_item.view.*


class HistoryAdapter(val data: List<CurrencyTransaction>) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder =
            HistoryViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_history_item, parent, false))

    override fun onBindViewHolder(holder: HistoryViewHolder?, position: Int) {
        holder?.bind(data[position])
    }

    inner class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mFromCurrency = view.fromTextView
        val mFromAmount = view.fromAmountTextView
        val mToCurrency = view.fromTextView
        val mToAmount = view.fromAmountTextView
        val mDate = view.dateTextView

        fun bind(item: CurrencyTransaction) {
            item.apply {
                mFromCurrency.text = from
                mFromAmount.text = fromValue.toString()
            }
        }
    }
}
