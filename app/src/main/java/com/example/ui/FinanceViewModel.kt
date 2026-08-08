package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.FinanceRepository
import com.example.data.entity.SavingsGoalEntity
import com.example.data.entity.TransactionEntity
import com.example.data.entity.UserProfileEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class Screen {
    object Welcome : Screen()
    object Onboarding : Screen()
    object Home : Screen()
    object Transactions : Screen()
    object AddTransaction : Screen()
    object Goals : Screen()
    object CreateGoal : Screen()
    object Learn : Screen()
    object Summary : Screen()
    object Profile : Screen()
}

data class FinancialCalculations(
    val monthIncome: Double = 0.0,
    val monthSpent: Double = 0.0,
    val availableToSpend: Double = 0.0,
    val totalGoalContribution: Double = 0.0,
    val safeToSpend: Double = 0.0,
    val totalSavings: Double = 0.0,
    val savingsRate: Int = 0
)

class FinanceViewModel(private val repository: FinanceRepository) : ViewModel() {

    val userProfile: StateFlow<UserProfileEntity?> = repository.userProfile
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    val transactions: StateFlow<List<TransactionEntity>> = repository.allTransactions
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val goals: StateFlow<List<SavingsGoalEntity>> = repository.allGoals
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val financialCalculations: StateFlow<FinancialCalculations> = combine(
        userProfile,
        transactions,
        goals
    ) { profile, txList, goalList ->
        val user = profile ?: UserProfileEntity()
        val incomeFromTx = txList.filter { it.type == "income" }.sumOf { it.amount }
        val monthIncome = if (incomeFromTx > 0) incomeFromTx else user.salary
        val monthSpent = txList.filter { it.type == "expense" }.sumOf { it.amount }

        val available = user.salary - user.fixedExpenses - monthSpent
        val totalGoalContrib = goalList.sumOf { it.monthlyContribution }
        val safe = (user.salary - user.fixedExpenses - totalGoalContrib - monthSpent).coerceAtLeast(0.0)

        val totalSavings = user.currentSavings + (monthIncome - monthSpent)
        val savingsRate = if (monthIncome > 0) {
            (((monthIncome - monthSpent) / monthIncome) * 100).toInt().coerceIn(0, 100)
        } else {
            39
        }

        FinancialCalculations(
            monthIncome = monthIncome,
            monthSpent = monthSpent,
            availableToSpend = available,
            totalGoalContribution = totalGoalContrib,
            safeToSpend = safe,
            totalSavings = totalSavings,
            savingsRate = savingsRate
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = FinancialCalculations()
    )

    fun saveProfile(profile: UserProfileEntity) {
        viewModelScope.launch {
            repository.saveUserProfile(profile)
        }
    }

    fun addTransaction(type: String, amount: Double, category: String, description: String) {
        viewModelScope.launch {
            repository.addTransaction(
                TransactionEntity(
                    type = type,
                    amount = amount,
                    category = category,
                    description = description,
                    timestamp = System.currentTimeMillis()
                )
            )
        }
    }

    fun deleteTransaction(transaction: TransactionEntity) {
        viewModelScope.launch {
            repository.deleteTransaction(transaction)
        }
    }

    fun createGoal(name: String, targetAmount: Double, currentAmount: Double, monthlyContribution: Double, icon: String) {
        viewModelScope.launch {
            val estimateMonth = "Dec 2026"
            repository.addGoal(
                SavingsGoalEntity(
                    name = name,
                    targetAmount = targetAmount,
                    currentAmount = currentAmount,
                    monthlyContribution = monthlyContribution,
                    estimateDate = estimateMonth,
                    icon = icon
                )
            )
        }
    }

    fun deleteGoal(goal: SavingsGoalEntity) {
        viewModelScope.launch {
            repository.deleteGoal(goal)
        }
    }

    fun resetData() {
        viewModelScope.launch {
            repository.resetAllData()
        }
    }
}

class FinanceViewModelFactory(private val repository: FinanceRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FinanceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FinanceViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
