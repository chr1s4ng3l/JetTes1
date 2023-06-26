package com.tamayo.jettes1.data

import com.tamayo.jettes1.domain.DomainData
import com.tamayo.jettes1.domain.mapToDomain
import com.tamayo.jettes1.utils.UIState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface MyRepository {

    fun getData(tag: String): Flow<UIState<List<DomainData>>>

}

class MyRepositoryImpl @Inject constructor(
    private val serviceApi: ServiceApi,
    private val ioDispatcher: CoroutineDispatcher
) : MyRepository {
    override fun getData(tag: String): Flow<UIState<List<DomainData>>> = flow {

        emit(UIState.LOADING)

        try {

            val response = serviceApi.getData(tag)

            if (response.isSuccessful) {
                response.body()?.let {
                    emit(UIState.SUCCESS(it.data.mapToDomain()))

                } ?: throw Exception(response.errorBody()?.string())
            } else {
                throw Exception("Response was null")
            }

        } catch (e: Exception) {
            emit(UIState.ERROR(e))
        }


    }.flowOn(ioDispatcher)

}