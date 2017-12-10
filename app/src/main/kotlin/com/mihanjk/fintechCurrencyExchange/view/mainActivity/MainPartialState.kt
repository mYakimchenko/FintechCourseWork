package com.mihanjk.fintechCurrencyExchange.view.mainActivity


sealed class MainPartialState {
    data class saveCurrencyExchangeFragment(val first: String, val second: String) : MainPartialState()
    object removeCurrencyExchangeFragment : MainPartialState()
    object openLastExchange : MainPartialState()
    object openAnalysisFragment : MainPartialState()
    object openLastHistoryFragment : MainPartialState()
    object removeHistoryFilterFragment : MainPartialState()
    data class removeFragmentFromStack(val tag: String) : MainPartialState()

}