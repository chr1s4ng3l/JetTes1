package com.tamayo.jettes1.domain

import com.tamayo.jettes1.data.MyRepository
import com.tamayo.jettes1.utils.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDataUseCase @Inject constructor(private val myRepository: MyRepository) {

    operator fun invoke(tag: String): Flow<UIState<List<DomainData>>> = flow {
        myRepository.getData(tag).collect{
            println("Usecases data -> $it <-")

            emit(it)
        }
    }
}