package com.mihanjk.fintechCurrencyExchange.view.transactionHistory

import android.os.Bundle
import android.view.*
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.mihanjk.fintechCurrencyExchange.CurrencyApplication
import com.mihanjk.fintechCurrencyExchange.R
import io.reactivex.Observable


class HistoryFragment : HistoryView, MviFragment<HistoryView, HistoryPresenter>() {

    override fun loadTransactions(): Observable<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun render(state: HistoryViewState) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createPresenter(): HistoryPresenter =
            (activity.application as CurrencyApplication).component.getHistoryPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.history_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        TODO()
        when (item?.itemId) {
//            R.id.filter_history -> (activity as MainActivity).openHistoryFilterScreen()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_history, container, false)
    }
}
