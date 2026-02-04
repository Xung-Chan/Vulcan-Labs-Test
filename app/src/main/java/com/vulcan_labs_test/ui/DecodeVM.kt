package com.vulcan_labs_test.ui

import android.util.Base64
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class DecodeVM @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(DecodeUS())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update {
            DecodeUS(
                inputText = "QmFzZTY0VVJMLWRlY29kZSAiZVhodlptUngiIHRvIGdldCBjaXBoZXJUZXh0OyB0aGVuIENhZXNhci1kZWNvZGUgKHNoaWZ0PTMpIHRvIGdldCBLRVk"
            )
        }
    }

    fun onEvent(event: DecodeEvent) {
       when(event){
           DecodeEvent.Base64Decode -> onBase64Decode()
           DecodeEvent.CaesarDecode -> onCaesarDecode()
           is DecodeEvent.InputChange -> onInputChange(event.input)
           DecodeEvent.ResetKey -> onResetKey()
       }
    }

    private fun onResetKey() {
        _uiState.update {
            it.copy(
                key = ""
            )
        }
    }
    private fun onInputChange(input: String) {
        _uiState.update {
            it.copy(
                inputText = input
            )
        }
    }

    private fun onBase64Decode() {
        try {
            val input = _uiState.value.inputText
            if (input.isEmpty()) {
                _uiState.update {
                    it.copy(
                        messages = it.messages + DecodeValue(
                            message = "Input is empty",
                            type = DecodedInfoType.Error
                        )
                    )
                }
                return
            }


            val decoded = Base64.decode(
                input.replace('-', '+').replace('_', '/'),
                Base64.URL_SAFE or Base64.NO_WRAP
            )
            val decodedText = String(decoded, Charsets.UTF_8)

            _uiState.update {
                it.copy(
                    inputText = "",
                    messages = it.messages + DecodeValue(
                        message = decodedText,
                        type = DecodedInfoType.Success
                    )
                )
            }
        } catch (e: Exception) {
            _uiState.update {
                it.copy(
                    messages = it.messages + DecodeValue(
                        message = "Base64 decode error: ${e.message}",
                        type = DecodedInfoType.Error
                    )
                )
            }
        }
    }

    private fun onCaesarDecode() {
        try {
            val input = _uiState.value.inputText
            if (input.isEmpty()) {
                _uiState.update {
                    it.copy(
                        messages = it.messages + DecodeValue(
                            message = "Input is empty",
                            type = DecodedInfoType.Error
                        )
                    )
                }
                return
            }

            val shift = 3
            val decoded = input.map { char ->
                when {
                    char.isUpperCase() -> {
                        ((char - 'A' - shift + 26) % 26 + 'A'.code).toChar()
                    }
                    char.isLowerCase() -> {
                        ((char - 'a' - shift + 26) % 26 + 'a'.code).toChar()
                    }
                    else -> char
                }
            }.joinToString("")

            _uiState.update {
                it.copy(
                    inputText = "",
                    key = decoded,
                    messages = it.messages + DecodeValue(
                        message = decoded,
                        type = DecodedInfoType.Success
                    )
                )
            }
        } catch (e: Exception) {
            _uiState.update {
                it.copy(
                    messages = it.messages + DecodeValue(
                        message = "Caesar decode error: ${e.message}",
                        type = DecodedInfoType.Error
                    )
                )
            }
        }
    }
}