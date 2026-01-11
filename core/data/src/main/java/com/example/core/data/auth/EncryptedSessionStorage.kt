package com.example.core.data.auth

import androidx.datastore.core.DataStore
import com.example.core.domain.AuthInfo
import com.example.core.domain.SessionStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import timber.log.Timber

class EncryptedSessionStorage(
    private val syncDataStore: DataStore<AuthInfoSerializable?>,
) : SessionStorage {
    override suspend fun get(): AuthInfo? = withContext(Dispatchers.IO) {
        val data = syncDataStore.data.firstOrNull()
        return@withContext if (data == null || data == AuthInfoSerializable()) null else data.toAuthInfo()
    }

    override suspend fun set(info: AuthInfo?) {
        try {
            withContext(Dispatchers.IO) {
                syncDataStore.updateData {
                    //if authInfo is set to null just use AuthInfoSerializable()
                    //later check if the data is null or AuthInfoSerializable() means it's null
                    //used like that to clear the data inside AuthInfo instead of just removing/deleting it
                    //which will remain the authInfo variables(accessToken,refreshToken,...) saved inside memory
                    info?.toAuthInfoSerializable() ?: AuthInfoSerializable()
                }
            }
        } catch (e: Exception) {
            Timber.tag("MyTag").e("setAuthInfo: ${e.localizedMessage}")
        }
    }
}
