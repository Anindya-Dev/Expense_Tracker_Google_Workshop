package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.AppDatabase
import com.example.data.FinanceRepository
import com.example.data.entity.UserProfileEntity
import com.example.ui.FinanceViewModel
import com.example.ui.FinanceViewModelFactory
import com.example.ui.Screen
import com.example.ui.screens.AddTransactionScreen
import com.example.ui.screens.CreateGoalScreen
import com.example.ui.screens.GoalsScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LearnScreen
import com.example.ui.screens.MonthlySummaryScreen
import com.example.ui.screens.OnboardingScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.TransactionsScreen
import com.example.ui.screens.WelcomeScreen
import com.example.ui.theme.FirstSalaryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(applicationContext)
        val repository = FinanceRepository(
            userDao = database.userDao(),
            transactionDao = database.transactionDao(),
            savingsGoalDao = database.savingsGoalDao()
        )
        val factory = FinanceViewModelFactory(repository)

        setContent {
            FirstSalaryTheme {
                FirstSalaryApp(factory = factory)
            }
        }
    }
}

@Composable
fun FirstSalaryApp(factory: FinanceViewModelFactory) {
    val viewModel: FinanceViewModel = viewModel(factory = factory)

    val userProfile by viewModel.userProfile.collectAsState()
    val transactions by viewModel.transactions.collectAsState()
    val goals by viewModel.goals.collectAsState()
    val calculations by viewModel.financialCalculations.collectAsState()

    var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }

    val user = userProfile ?: UserProfileEntity()

    when (currentScreen) {
        is Screen.Welcome -> {
            WelcomeScreen(
                onGetStarted = { currentScreen = Screen.Onboarding },
                onQuickDemo = { currentScreen = Screen.Home }
            )
        }
        is Screen.Onboarding -> {
            OnboardingScreen(
                initialProfile = user,
                onBack = { currentScreen = Screen.Welcome },
                onComplete = { newProfile ->
                    viewModel.saveProfile(newProfile)
                    currentScreen = Screen.Home
                }
            )
        }
        is Screen.Home -> {
            HomeScreen(
                userProfile = user,
                calculations = calculations,
                onNavigate = { screen -> currentScreen = screen }
            )
        }
        is Screen.Transactions -> {
            TransactionsScreen(
                transactions = transactions,
                onDeleteTransaction = { tx -> viewModel.deleteTransaction(tx) },
                onNavigate = { screen -> currentScreen = screen }
            )
        }
        is Screen.AddTransaction -> {
            AddTransactionScreen(
                onClose = { currentScreen = Screen.Transactions },
                onSave = { type, amount, category, description ->
                    viewModel.addTransaction(type, amount, category, description)
                    currentScreen = Screen.Transactions
                }
            )
        }
        is Screen.Goals -> {
            GoalsScreen(
                goals = goals,
                onDeleteGoal = { goal -> viewModel.deleteGoal(goal) },
                onNavigate = { screen -> currentScreen = screen }
            )
        }
        is Screen.CreateGoal -> {
            CreateGoalScreen(
                onBack = { currentScreen = Screen.Goals },
                onCreateGoal = { name, target, current, monthly, icon ->
                    viewModel.createGoal(name, target, current, monthly, icon)
                    currentScreen = Screen.Goals
                }
            )
        }
        is Screen.Learn -> {
            LearnScreen(
                onNavigate = { screen -> currentScreen = screen }
            )
        }
        is Screen.Summary -> {
            MonthlySummaryScreen(
                calculations = calculations,
                onNavigate = { screen -> currentScreen = screen }
            )
        }
        is Screen.Profile -> {
            ProfileScreen(
                userProfile = user,
                onResetData = {
                    viewModel.resetData()
                    currentScreen = Screen.Welcome
                },
                onNavigate = { screen -> currentScreen = screen }
            )
        }
    }
}

