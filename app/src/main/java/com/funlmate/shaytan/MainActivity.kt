package com.funlmate.shaytan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Red = Color(0xFFE50920)
private val Dark = Color(0xFF171717)
private val White = Color(0xFFFFFFFF)
private val SoftGray = Color(0xFFF5F5F5)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                FunimateApp()
            }
        }
    }
}

@Composable
fun FunimateApp() {
    var selectedTab by remember { mutableIntStateOf(0) }
    var selectedVideo by remember { mutableStateOf<String?>(null) }

    val videoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        selectedVideo = uri?.toString()
    }

    Scaffold(
        containerColor = White,
        bottomBar = {
            NavigationBar(
                containerColor = White,
                contentColor = Red
            ) {
                val items = listOf(
                    Triple("الرئيسية", Icons.Default.Home, 0),
                    Triple("مشاريعي", Icons.Default.VideoLibrary, 1),
                    Triple("التأثيرات", Icons.Default.AutoAwesome, 2),
                    Triple("حسابي", Icons.Default.Person, 3)
                )

                items.forEach { item ->
                    NavigationBarItem(
                        selected = selectedTab == item.third,
                        onClick = { selectedTab = item.third },
                        icon = {
                            Icon(item.second, contentDescription = item.first)
                        },
                        label = {
                            Text(item.first, fontSize = 10.sp)
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Red,
                            selectedTextColor = Red,
                            indicatorColor = Color(0xFFFFE5E8),
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray
                        )
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            Header()

            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                Text(
                    text = when (selectedTab) {
                        1 -> "مشاريعك"
                        2 -> "اكتشف التأثيرات"
                        3 -> "حسابك"
                        else -> "ابدأ الإبداع"
                    },
                    color = Dark,
                    fontSize = 27.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Text(
                    text = when (selectedTab) {
                        1 -> "كل أعمالك في مكان واحد"
                        2 -> "أضف لمساتك الإبداعية"
                        3 -> "مرحباً بك في عالم الإبداع"
                        else -> "حوّل أفكارك إلى فيديوهات مميزة"
                    },
                    color = Color.Gray,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 5.dp)
                )

                Spacer(modifier = Modifier.height(22.dp))

                Button(
                    onClick = { videoPicker.launch("video/*") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(66.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Red
                    )
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.width(9.dp))
                    Text(
                        "إنشاء مشروع جديد",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "مشاريعك الأخيرة",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Dark
                    )
                    Text(
                        "عرض الكل",
                        color = Red,
                        fontSize = 13.sp
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(185.dp)
                        .background(SoftGray, RoundedCornerShape(20.dp))
                        .border(
                            1.dp,
                            Color(0xFFEEEEEE),
                            RoundedCornerShape(20.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(65.dp)
                                .background(Color(0xFFFFE5E8), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Movie,
                                contentDescription = null,
                                tint = Red,
                                modifier = Modifier.size(34.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            "لا توجد مشاريع حتى الآن",
                            color = Dark,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )

                        Text(
                            "اختر فيديو وابدأ مشروعك الأول",
                            color = Color.Gray,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 5.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(25.dp))

                Text(
                    "مساحة الإبداع",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Dark
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    FeatureCard(
                        title = "تأثيرات",
                        subtitle = "لمسات إبداعية",
                        modifier = Modifier.weight(1f)
                    )
                    FeatureCard(
                        title = "انتقالات",
                        subtitle = "حركة سلسة",
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(25.dp))
            }
        }
    }

    if (selectedVideo != null) {
        AlertDialog(
            onDismissRequest = { selectedVideo = null },
            title = {
                Text("تم اختيار الفيديو")
            },
            text = {
                Text("تم اختيار ملف فيديو بنجاح. الخطوة التالية ستكون بناء شاشة التحرير.")
            },
            confirmButton = {
                TextButton(onClick = { selectedVideo = null }) {
                    Text("حسناً", color = Red)
                }
            }
        )
    }
}

@Composable
fun Header() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(155.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            val cracks = listOf(
                listOf(
                    Offset(w * 0.02f, 0f),
                    Offset(w * 0.10f, h * 0.30f),
                    Offset(w * 0.06f, h * 0.48f),
                    Offset(w * 0.17f, h * 0.65f)
                ),
                listOf(
                    Offset(w * 0.10f, h * 0.30f),
                    Offset(w * 0.20f, h * 0.22f),
                    Offset(w * 0.24f, h * 0.08f)
                ),
                listOf(
                    Offset(w * 0.98f, 0f),
                    Offset(w * 0.90f, h * 0.25f),
                    Offset(w * 0.95f, h * 0.43f),
                    Offset(w * 0.84f, h * 0.62f)
                ),
                listOf(
                    Offset(w * 0.90f, h * 0.25f),
                    Offset(w * 0.78f, h * 0.17f),
                    Offset(w * 0.75f, h * 0.04f)
                )
            )

            cracks.forEach { points ->
                val path = Path().apply {
                    moveTo(points.first().x, points.first().y)
                    points.drop(1).forEach {
                        lineTo(it.x, it.y)
                    }
                }
                drawPath(
                    path = path,
                    color = Red.copy(alpha = 0.65f),
                    style = Stroke(width = 2.5f)
                )
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "♛  Funimateشيطان",
                color = Dark,
                fontSize = 27.sp,
                fontWeight = FontWeight.Black
            )
            Text(
                text = "VIDEO EDITOR",
                color = Red,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 4.sp
            )
        }
    }
}

@Composable
fun FeatureCard(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .height(105.dp)
            .background(SoftGray, RoundedCornerShape(17.dp))
            .clickable { }
            .padding(14.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(9.dp)
                .background(Red, CircleShape)
        )
        Spacer(modifier = Modifier.height(9.dp))
        Text(
            title,
            color = Dark,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
        )
        Text(
            subtitle,
            color = Color.Gray,
            fontSize = 11.sp
        )
    }
}
