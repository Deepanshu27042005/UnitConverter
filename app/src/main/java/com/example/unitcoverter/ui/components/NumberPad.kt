package com.example.unitcoverter.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unitcoverter.ui.theme.MintGreen
import com.example.unitcoverter.ui.theme.White
import com.example.unitcoverter.ui.theme.DarkGreen

@Composable
fun NumberPad(
    onNumberClick: (String) -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val buttons = listOf(
        listOf("1", "2", "3"),
        listOf("4", "5", "6"),
        listOf("7", "8", "9"),
        listOf(".", "0", "delete")
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        buttons.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { char ->
                    NumberButton(
                        text = char,
                        onClick = {
                            if (char == "delete") onDeleteClick()
                            else onNumberClick(char)
                        },
                        isDelete = char == "delete"
                    )
                }
            }
        }
    }
}

@Composable
fun NumberButton(
    text: String,
    onClick: () -> Unit,
    isDelete: Boolean = false
) {
    Surface(
        onClick = onClick,
        shape = CircleShape,
        color = if (isDelete) MintGreen else White,
        modifier = Modifier.size(74.dp),
        shadowElevation = if (isDelete) 0.dp else 2.dp
    ) {
        Box(contentAlignment = Alignment.Center) {
            if (isDelete) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Backspace,
                    contentDescription = "Delete",
                    tint = DarkGreen,
                    modifier = Modifier.size(28.dp)
                )
            } else {
                Text(
                    text = text,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray
                )
            }
        }
    }
}
