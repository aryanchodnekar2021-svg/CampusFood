
package com.aryan.campusfood

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Orange = Color(0xFFFF7043)
private val Cream = Color(0xFFFFF8F1)
private val Dark = Color(0xFF292321)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                CampusFoodApp()
            }
        }
    }
}

data class Food(
    val name: String,
    val price: Int,
    val emoji: String,
    val category: String
)

@Composable
fun CampusFoodApp() {

    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        containerColor = Cream,
        bottomBar = {
            NavigationBar(
                containerColor = Color.White
            ) {
                val tabs = listOf(
                    "Home" to "🏠",
                    "Restaurants" to "🍽️",
                    "Cart" to "🛒",
                    "Profile" to "👤"
                )

                tabs.forEachIndexed { index, tab ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        icon = {
                            Text(tab.second, fontSize = 20.sp)
                        },
                        label = {
                            Text(tab.first)
                        }
                    )
                }
            }
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (selectedTab) {
                0 -> HomeScreen()
                1 -> SimpleScreen("Restaurants", "Discover food around your campus.")
                2 -> SimpleScreen("Your Cart", "Your selected food items will appear here.")
                3 -> SimpleScreen("My Profile", "Your account details will appear here.")
            }
        }
    }
}

@Composable
fun HomeScreen() {

    val foods = listOf(
        Food("Veg Burger", 79, "🍔", "Fast Food"),
        Food("Cheese Pizza", 129, "🍕", "Fast Food"),
        Food("Masala Dosa", 65, "🥞", "South Indian"),
        Food("Veg Sandwich", 59, "🥪", "Snacks"),
        Food("Cold Coffee", 49, "🥤", "Drinks")
    )

    var search by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        contentPadding = PaddingValues(vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {
            Text(
                text = "CAMPUS FOOD",
                color = Orange,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Hungry, Aryan? 😋",
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold,
                color = Dark
            )

            Text(
                text = "Order your favourite campus food!",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }

        item {
            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search food...") },
                leadingIcon = { Text("🔍") },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Orange,
                    unfocusedBorderColor = Color.LightGray
                )
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Orange
                ),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        "SPECIAL CAMPUS OFFER",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        "Get 20% OFF",
                        color = Color.White,
                        fontSize = 27.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        "On your first food order!",
                        color = Color.White
                    )
                }
            }
        }

        item {
            Text(
                "Popular Food 🍽️",
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                color = Dark
            )
        }

        items(foods.filter {
            it.name.contains(search, ignoreCase = true)
        }) { food ->

            FoodCard(food)
        }

        item {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                "More delicious food coming soon!",
                color = Color.Gray,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun FoodCard(food: Food) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(65.dp)
                    .background(
                        Cream,
                        RoundedCornerShape(15.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(food.emoji, fontSize = 35.sp)
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    food.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = Dark
                )

                Text(
                    food.category,
                    color = Color.Gray,
                    fontSize = 13.sp
                )

                Text(
                    "₹${food.price}",
                    color = Orange,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )
            }

            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Orange
                ),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(
                    horizontal = 15.dp,
                    vertical = 8.dp
                )
            ) {
                Text("ADD")
            }
        }
    }
}

@Composable
fun SimpleScreen(title: String, description: String) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            title,
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold,
            color = Dark
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            description,
            color = Color.Gray
        )
    }
}
