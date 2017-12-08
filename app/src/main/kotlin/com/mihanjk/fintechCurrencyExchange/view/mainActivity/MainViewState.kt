package com.mihanjk.fintechCurrencyExchange.view.mainActivity

import android.support.v4.app.Fragment
import com.mihanjk.fintechCurrencyExchange.view.analysis.AnalysisFragment
import com.mihanjk.fintechCurrencyExchange.view.currencyList.CurrencyListFragment
import com.mihanjk.fintechCurrencyExchange.view.transactionHistory.HistoryFragment
import java.util.*

data class MainViewState(
        val exchangeFragments: Deque<Fragment> = ArrayDeque(arrayListOf(CurrencyListFragment())),
        val exchange: Boolean = true,
        val historyFragments: Deque<Fragment> = ArrayDeque(arrayListOf(HistoryFragment())),
        val history: Boolean = false,
        val analysisFragment: AnalysisFragment? = null,
        val analysis: Boolean = false
)