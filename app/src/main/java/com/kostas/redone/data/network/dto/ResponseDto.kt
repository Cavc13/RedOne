package com.kostas.redone.data.network.dto

import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml

@Xml
data class ResponseDto(
    @Attribute(name = "ID")
    val id: String? = "",
    @Attribute(name = "DateRange1")
    val dateRange1: String? = "",
    @Attribute(name = "DateRange2")
    val dateRange2: String? = "",
    @Attribute(name = "name")
    val name: String? = "",
    @Element
    val arrayDollar: List<DollarDto>? = emptyList()

)