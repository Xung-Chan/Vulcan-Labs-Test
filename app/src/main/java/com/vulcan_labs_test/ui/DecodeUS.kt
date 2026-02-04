package com.vulcan_labs_test.ui

data class DecodeUS(
    val inputText: String = "",
    val messages: List<DecodeValue> = emptyList(),
    val key: String = ""
)

data class DecodeValue(
    val message: String = "",
    val type: DecodedInfoType=DecodedInfoType.Success
)