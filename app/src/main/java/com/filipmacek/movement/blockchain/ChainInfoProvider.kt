package com.filipmacek.movement.blockchain

import android.content.res.AssetManager
import androidx.lifecycle.MutableLiveData
import com.filipmacek.movement.data.AppDatabase
import com.filipmacek.movement.data.chainInfo.ChainInfo
import com.filipmacek.movement.data.config.Settings
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types.newParameterizedType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun AssetManager.loadInitChains(moshi: Moshi): List<ChainInfo> {
    val chainsJSON = open("init-chains.json").reader().readText()
    return moshi.deSerialize(chainsJSON)
}


fun Moshi.deSerialize(chainsJSON: String): List<ChainInfo> {
    val adapter: JsonAdapter<List<ChainInfo>> = adapter(newParameterizedType(List::class.java,ChainInfo::class.java))
    val list = adapter.fromJson(chainsJSON) ?: emptyList()
    return list
}

class ChainInfoProvider(val settings: Settings,
                        val appDatabase: AppDatabase,
                        private val moshi: Moshi,
                        private val assetManager: AssetManager):MutableLiveData<ChainInfo>() {

    init {
        GlobalScope.launch(Dispatchers.Default) {

            postValue(getInitial())
        }
    }

    private suspend fun getInitial():ChainInfo = appDatabase.chainInfoDAO().getChainById(settings.chain)
        ?: appDatabase.chainInfoDAO().getAll().firstOrNull()
        ?: appDatabase.chainInfoDAO().insert(assetManager.loadInitChains(moshi)).let {
            getInitial()
        }

    fun setCurrent(value: ChainInfo) {
        settings.chain= value.chainId
        setValue(value)

    }

    fun getCurrent() = value

    fun getCurrentChainId() = value!!.chainId

}