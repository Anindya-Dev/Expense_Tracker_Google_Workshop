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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.Screen
import com.example.ui.components.BottomNavBar
import com.example.ui.theme.AppBackground
import com.example.ui.theme.LightPurpleBg
import com.example.ui.theme.PrimaryPurple
import com.example.ui.theme.SafeGreen
import com.example.ui.theme.SafeGreenBg
import com.example.ui.theme.SafeGreenTrack
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary

data class LessonArticle(
    val title: String,
    val readTime: String,
    val emoji: String,
    val summary: String,
    val body: String
)

@Composable
fun LearnScreen(
    onNavigate: (Screen) -> Unit
) {
    var selectedArticle by remember { mutableStateOf<LessonArticle?>(null) }
    var lessonsCompleted by remember { mutableStateOf(1) }

    val scrollState = rememberScrollState()

    if (selectedArticle != null) {
        LessonDetailDialog(
            article = selectedArticle!!,
            onDismiss = { selectedArticle = null },
            onComplete = {
                lessonsCompleted += 1
                selectedArticle = null
            }
        )
    }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                currentScreen = Screen.Learn,
                onNavigate = onNavigate
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppBackground)
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(20.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Learn Money Basics",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextPrimary
            )

            Text(
                text = "Master your finances with simple, bite-sized lessons.",
                fontSize = 13.sp,
                color = TextMuted
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Featured Lesson Card: 50/30/20 Rule
            val featured = LessonArticle(
                title = "The 50/30/20 Rule",
                readTime = "5 min read",
                emoji = "📈",
                summary = "A simple framework to balance needs, wants, and savings goals effortlessly.",
                body = "The 50/30/20 rule is an intuitive budget allocation guide for first-time salary earners:\n\n" +
                        "1. 50% for Needs: Essential living costs like rent, groceries, utility bills, and transportation.\n\n" +
                        "2. 30% for Wants: Personal choices like eating out, shopping, hobbies, and weekend trips.\n\n" +
                        "3. 20% for Savings & Goals: Building an emergency fund, investing, or saving for major life milestones.\n\n" +
                        "Pro Tip: Don't worry if your exact numbers vary at first. The key is establishing a consistent savings habit from your very first paycheck!"
            )

            Card(
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(LightPurpleBg),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.TrendingUp,
                                contentDescription = null,
                                tint = PrimaryPurple
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(LightPurpleBg)
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "⏱ " + featured.readTime,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryPurple
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = featured.title,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = featured.summary,
                        fontSize = 13.sp,
                        color = TextMuted,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = { selectedArticle = featured },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .testTag("learn_start_featured")
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text(text = "Start Lesson", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Other Bite-Sized Lessons
            val articles = listOf(
                LessonArticle(
                    title = "How much should I save?",
                    readTime = "3 min read",
                    emoji = "💰",
                    summary = "Aim to save at least 15-20% of your net income every month.",
                    body = "When starting your job, even saving ₹2,000 to ₹5,000 every month compounds into significant security over time. Automate your savings on salary day before spending on leisure!"
                ),
                LessonArticle(
                    title = "What is an emergency fund?",
                    readTime = "4 min read",
                    emoji = "🛟",
                    summary = "3 to 6 months of living expenses saved in a high-yield liquid account.",
                    body = "An emergency fund protects you against unexpected medical expenses, job changes, or urgent home repairs so you never need to rely on high-interest loans."
                ),
                LessonArticle(
                    title = "Managing your first salary",
                    readTime = "4 min read",
                    emoji = "🎉",
                    summary = "Smart steps to take in month 1 of your new professional journey.",
                    body = "1. Treat yourself modestly to celebrate your hard work.\n2. Set aside fixed rent & bill expenses.\n3. Transfer your planned savings first.\n4. Spend what's left peacefully."
                )
            )

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                articles.forEach { art ->
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedArticle = art }
                    ) {
                        Row(
                            modifier = Modifier.padding(18.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = art.emoji, fontSize = 28.sp)
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = art.title, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(text = art.readTime, fontSize = 11.sp, color = TextMuted)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Learning Progress Level Card
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = LightPurpleBg),
                modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Learning Progress", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TextPrimary)
                        Text(text = "Level ${(lessonsCompleted / 3) + 1}", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = PrimaryPurple)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    val progressRatio = ((lessonsCompleted % 3) / 3.0f).coerceIn(0.1f, 1f)
                    LinearProgressIndicator(
                        progress = { progressRatio },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(CircleShape),
                        color = SafeGreen,
                        trackColor = SafeGreenTrack
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Complete 3 more lessons to level up your financial literacy!",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextMuted
                    )
                }
            }
        }
    }
}

@Composable
private fun LessonDetailDialog(
    article: LessonArticle,
    onDismiss: () -> Unit,
    onComplete: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier.fillMaxWidth().padding(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = article.emoji + " " + article.readTime, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PrimaryPurple)
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = TextMuted)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = article.title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = article.body,
                    fontSize = 14.sp,
                    color = TextPrimary,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(28.dp))

                Button(
                    onClick = onComplete,
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Mark as Completed", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
