package com.kostas.redone.data.network.api

import com.kostas.redone.data.network.dto.ResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("XML_dynamic.asp")
    suspend fun getUsdRate(
        @Query(TITLE_DATE_REQ1) date1:String =  "07/05/2022",
        @Query(TITLE_DATE_REQ2) date2:String =  "07/06/2022",
        @Query(TITLE_VAL_NM_RQ) valNmRq:String =  "R01235"
    ): Response<ResponseDto>

    companion object{
        private const val TITLE_DATE_REQ1 = "date_req1"
        private const val TITLE_DATE_REQ2 = "date_req2"
        private const val TITLE_VAL_NM_RQ = "VAL_NM_RQ"
    }
}