package com.vulcan_labs_test.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vulcan_labs_test.R
import com.vulcan_labs_test.ui.theme.Typography

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun DecodeScreen(
    uiState: DecodeUS = DecodeUS(),
    onEvent: (DecodeEvent) -> Unit = {}
) {
    val scrollState = rememberScrollState(0)
    val focusManager = LocalFocusManager.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {
        Scaffold(
            bottomBar = {
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    CustomTextField(
                        value = uiState.inputText,
                        onValueChange = {
                            onEvent(
                                DecodeEvent.InputChange(it)
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Text
                        ),
                        placeholder = {
                            Text(
                                text = "Enter text to decode",
                                style = Typography.bodyMedium,
                                color = Color.Gray
                            )
                        },
                        modifier = Modifier.fillMaxWidth()

                    )
                    Row {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable {
                                        onEvent(
                                            DecodeEvent.CaesarDecode
                                        )
                                        focusManager.clearFocus()
                                    }
                                    .padding(vertical = 10.dp),
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                Text(
                                    text = "Caesar-Decode",
                                    style = Typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = Color.Black
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .weight(1f)
                                    .background(
                                        color = Color(0xFF1573FF),
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .shadow(
                                        elevation = 10.dp,
                                        shape = RoundedCornerShape(10.dp),
                                        ambientColor = Color.Transparent,
                                        spotColor = Color.Transparent
                                    )
                                    .clickable {
                                        onEvent(
                                            DecodeEvent.Base64Decode
                                        )
                                        focusManager.clearFocus()
                                    }
                                    .padding(vertical = 10.dp),
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                Text(
                                    text = "Base64-Decode",
                                    style = Typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            },
            modifier = Modifier.systemBarsPadding()
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(20.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                uiState.messages.forEach {
                    DecodedValueLine(
                        message = it.message,
                        type = it.type,
                    )
                }

            }
        }
        if (uiState.key.isNotEmpty()) {
            Dialog(
                uiState.key
            ) {
                onEvent(
                    DecodeEvent.ResetKey
                )
            }
        }

    }
}

enum class DecodedInfoType {
    Success,
    Error
}

@Preview
@Composable
fun DecodedValueLine(
    message: String = "ábasjkbas", type: DecodedInfoType = DecodedInfoType.Success,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(10.dp)
    val isSuccess = type == DecodedInfoType.Success
    Row(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 5.dp,
                shape = shape,
                ambientColor = Color.Gray.copy(
                    0.2f
                ),
                spotColor = Color.Gray.copy(
                    0.2f
                )
            )
            .background(
                color = if (isSuccess) Color.White else Color.Red.copy(0.2f),
                shape = shape
            )
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Icon(
            painter = if (isSuccess) painterResource(R.drawable.success) else painterResource(R.drawable.info),
            contentDescription = null,
            modifier = Modifier.size(25.dp),
            tint = if (isSuccess) Color.Green else Color.Red
        )
        Text(
            text = message,
            style = Typography.bodyMedium,
            modifier = Modifier.weight(1f),
            color = if (isSuccess) Color.Black else Color.Red
        )
    }

}
