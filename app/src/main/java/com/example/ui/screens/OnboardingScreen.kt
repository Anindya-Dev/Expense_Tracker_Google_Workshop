package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.data.entity.UserProfileEntity
import com.example.ui.theme.AppBackground
import com.example.ui.theme.LightPurpleBg
import com.example.ui.theme.PrimaryPurple
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary

@Composable
fun OnboardingScreen(
    initialProfile: UserProfileEntity?,
    onBack: () -> Unit,
    onComplete: (UserProfileEntity) -> Unit
) {
    var salaryInput by remember { mutableStateOf(initialProfile?.salary?.toLong()?.toString() ?: "45000") }
    var fixedExpensesInput by remember { mutableStateOf(initialProfile?.fixedExpenses?.toLong()?.toString() ?: "15000") }
    var savingsInput by remember { mutableStateOf(initialProfile?.currentSavings?.toLong()?.toString() ?: "10000") }
    var selectedGoal by remember { mutableStateOf(initialProfile?.primaryGoal ?: "Emergency Fund") }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(24.dp)
            .verticalScroll(scrollState)
    ) {
        // Top Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = PrimaryPurple
                )
            }
            Text(
                text = "FirstSalary",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryPurple
            )
            Spacer(modifier = Modifier.width(48.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Let's understand your money",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            lineHeight = 32.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Tell us a bit about your salary to build your custom Financial Zen plan.",
            fontSize = 14.sp,
            color = TextMuted,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Input Card
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "Your Numbers",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = salaryInput,
                    onValueChange = { salaryInput = it },
                    label = { Text("Monthly Take-Home Salary") },
                    leadingIcon = { Text("₹", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryPurple,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    ),
                    modifier = Modifier.testTag("onboarding_salary_input").fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = fixedExpensesInput,
                    onValueChange = { fixedExpensesInput = it },
                    label = { Text("Monthly Fixed Expenses (Rent, EMI, Bills)") },
                    leadingIcon = { Text("₹", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryPurple,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    ),
                    modifier = Modifier.testTag("onboarding_expenses_input").fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = savingsInput,
                    onValueChange = { savingsInput = it },
                    label = { Text("Current Savings") },
                    leadingIcon = { Text("₹", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryPurple,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    ),
                    modifier = Modifier.testTag("onboarding_savings_input").fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "What's your primary money goal?",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )

        Spacer(modifier = Modifier.height(12.dp))

        GoalOptionGrid(
            selectedGoal = selectedGoal,
            onGoalSelected = { selectedGoal = it }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                val salary = salaryInput.toDoubleOrNull() ?: 45000.0
                val fixedExpenses = fixedExpensesInput.toDoubleOrNull() ?: 15000.0
                val currentSavings = savingsInput.toDoubleOrNull() ?: 10000.0

                val profile = UserProfileEntity(
                    id = 1,
                    name = initialProfile?.name ?: "Alex Mercer",
                    email = initialProfile?.email ?: "alex.mercer@example.com",
                    salary = salary,
                    fixedExpenses = fixedExpenses,
                    currentSavings = currentSavings,
                    primaryGoal = selectedGoal,
                    onboarded = true
                )
                onComplete(profile)
            },
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .testTag("onboarding_submit")
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = "Create My Financial Zen Plan",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun GoalOptionGrid(
    selectedGoal: String,
    onGoalSelected: (String) -> Unit
) {
    val goals = listOf(
        Pair("Build Savings", Icons.Default.Wallet),
        Pair("Emergency Fund", Icons.Default.Shield),
        Pair("Major Purchase", Icons.Default.ShoppingBag),
        Pair("Travel", Icons.Default.Flight)
    )

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            GoalTile(
                title = goals[0].first,
                icon = goals[0].second,
                isSelected = selectedGoal == goals[0].first,
                onClick = { onGoalSelected(goals[0].first) },
                modifier = Modifier.weight(1f)
            )
            GoalTile(
                title = goals[1].first,
                icon = goals[1].second,
                isSelected = selectedGoal == goals[1].first,
                onClick = { onGoalSelected(goals[1].first) },
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            GoalTile(
                title = goals[2].first,
                icon = goals[2].second,
                isSelected = selectedGoal == goals[2].first,
                onClick = { onGoalSelected(goals[2].first) },
                modifier = Modifier.weight(1f)
            )
            GoalTile(
                title = goals[3].first,
                icon = goals[3].second,
                isSelected = selectedGoal == goals[3].first,
                onClick = { onGoalSelected(goals[3].first) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun GoalTile(
    title: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val borderModifier = if (isSelected) {
        Modifier.border(2.dp, PrimaryPurple, RoundedCornerShape(20.dp))
    } else {
        Modifier.border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(20.dp))
    }
    val bg = if (isSelected) LightPurpleBg else MaterialTheme.colorScheme.surface

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(bg)
            .then(borderModifier)
            .clickable { onClick() }
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = if (isSelected) PrimaryPurple else TextMuted,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) PrimaryPurple else TextPrimary
            )
        }
    }
}
