package com.example.covertervk.domain.use_case

import com.example.covertervk.data.remote.dto.toValue
import com.example.covertervk.domain.model.Value
import com.example.covertervk.domain.repository.ValueRepository
import com.example.covertervk.domain.util.Resource
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import java.util.concurrent.Flow
import javax.inject.Inject

class getExchangeUseCase @Inject constructor(
    private val repository: ValueRepository
){
 operator fun invoke(fromValue: String, toValue: String, amount: String): kotlinx.coroutines.flow.Flow<Resource<Value>> = flow {
     try {
         emit(Resource.Loading<Value>())
         val value = repository.getExchange(fromValue, toValue, amount ).toValue()
         emit(Resource.Success(value))
     } catch(e: HttpException) {
         emit(Resource.Error<Value>(e.localizedMessage ?: "An unexpected error occured"))
     }
     catch(e: IOException) {
         emit(Resource.Error<Value>(e.localizedMessage ?: "An unexpected error occured"))
     }

 }
}
