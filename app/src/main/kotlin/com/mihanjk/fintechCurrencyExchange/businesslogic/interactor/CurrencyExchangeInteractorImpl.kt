package com.mihanjk.fintechCurrencyExchange.businesslogic.interactor

import com.mihanjk.fintechCurrencyExchange.businesslogic.http.FixerService
import com.mihanjk.fintechCurrencyExchange.model.database.CurrencyDatabase
import javax.inject.Inject

interface CurrencyExchangeInteractor {

}

class CurrencyExchangeInteractorImpl @Inject constructor(
        val mDatabase: CurrencyDatabase,
        val mApiService: FixerService
) : CurrencyExchangeInteractor {

}