package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Laptop
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AppBackground
import com.example.ui.theme.LightPurpleBg
import com.example.ui.theme.PrimaryPurple
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary

@Composable
fun CreateGoalScreen(
    onBack: () -> Unit,
    onCreateGoal: (name: String, targetAmount: Double, currentAmount: Double, monthlyContribution: Double, icon: String) -> Unit
) {
    var nameInput by remember { mutableStateOf("") }
    var targetInput by remember { mutableStateOf("") }
    var currentInput by remember { mutableStateOf("0") }
    var monthlyInput by remember { mutableStateOf("") }
    var selectedIcon by remember { mutableStateOf("Shield") }
    var errorMsg by remember { mutableStateOf<String?>(null) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(24.dp)
            .verticalScroll(scrollState)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = PrimaryPurple)
            }
            Text(
                text = "Create Savings Goal",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryPurple
            )
            Spacer(modifier = Modifier.width(48.dp))
        }

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = nameInput,
            onValueChange = { nameInput = it },
            label = { Text("Goal Name (e.g., Emergency Fund, Laptop, Travel)") },
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryPurple,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            ),
            modifier = Modifier.testTag("create_goal_name_input").fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(14.dp))

        OutlinedTextField(
            value = targetInput,
            onValueChange = { targetInput = it },
            label = { Text("Target Amount") },
            leadingIcon = { Text("₹", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryPurple,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            ),
            modifier = Modifier.testTag("create_goal_target_input").fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(14.dp))

        OutlinedTextField(
            value = currentInput,
            onValueChange = { currentInput = it },
            label = { Text("Initial Saved Amount") },
            leadingIcon = { Text("₹", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryPurple,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            ),
            modifier = Modifier.testTag("create_goal_current_input").fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(14.dp))

        OutlinedTextField(
            value = monthlyInput,
            onValueChange = { monthlyInput = it },
            label = { Text("Monthly Contribution") },
            leadingIcon = { Text("₹", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryPurple,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            ),
            modifier = Modifier.testTag("create_goal_monthly_input").fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Choose Icon", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextPrimary)

        Spacer(modifier = Modifier.height(10.dp))

        val iconOptions = listOf(
            Pair("Shield", Icons.Default.Shield),
            Pair("Laptop", Icons.Default.Laptop),
            Pair("Flight", Icons.Default.Flight),
            Pair("ShoppingBag", Icons.Default.ShoppingBag),
            Pair("Wallet", Icons.Default.Wallet)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            iconOptions.forEach { (iconKey, iconVector) ->
                val isSelected = selectedIcon == iconKey
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) PrimaryPurple else LightPurpleBg)
                        .clickable { selectedIcon = iconKey },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = iconVector,
                        contentDescription = iconKey,
                        tint = if (isSelected) androidx.compose.ui.graphics.Color.White else PrimaryPurple,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        if (errorMsg != null) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = errorMsg!!, color = androidx.compose.ui.graphics.Color.Red, fontSize = 13.sp)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (nameInput.isBlank()) {
                    errorMsg = "Please enter a goal name"
                    return@Button
                }
                val target = targetInput.toDoubleOrNull()
                if (target == null || target <= 0) {
                    errorMsg = "Please enter a target amount greater than 0"
                    return@Button
                }
                val current = currentInput.toDoubleOrNull() ?: 0.0
                val monthly = monthlyInput.toDoubleOrNull() ?: (target / 10)

                onCreateGoal(nameInput, target, current, monthly, selectedIcon)
            },
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .testTag("create_goal_submit")
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(text = "Save Goal", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}
