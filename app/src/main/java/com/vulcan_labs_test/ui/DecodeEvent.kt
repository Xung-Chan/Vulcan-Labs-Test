package com.vulcan_labs_test.ui

sealed class DecodeEvent {
    data class InputChange(val input: String) : DecodeEvent()
    object Base64Decode : DecodeEvent()
    object CaesarDecode : DecodeEvent()
    object ResetKey : DecodeEvent()
}