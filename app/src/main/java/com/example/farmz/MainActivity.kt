package com.example.farmz

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.farmz.ui.theme.FarmzTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FarmzTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "login") {
                    composable("login") { LoginScreen(navController) }
                    composable("signup") { SignUpScreen(navController) }
                    composable("home") { HomeScreen(navController) }
                    composable("breedSelection") { BreedSelectionScreen() }
                    composable("calving") { CalvingScreen() }
                    composable("gestation") { GestationScreen() }
                    composable("health") { HealthScreen() }
                }
            }
        }
    }
}
@Composable
fun SignUpScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sign Up",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    Toast.makeText(context, "Account created successfully!", Toast.LENGTH_SHORT).show()
                    navController.navigate("home")
                } else {
                    Toast.makeText(context, "Please enter both email and password", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text("Sign Up")
        }
    }
}

@Composable
fun LoginScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    navController.navigate("home")
                } else {
                    Toast.makeText(context, "Please enter both email and password", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Create New Account Text
        Text(
            text = "Create New Account",
            style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .padding(top = 16.dp)
                .clickable {
                    navController.navigate("signup")
                }
        )
    }
}
@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomNavigationBar(navController) },
        content = { paddingValues ->
            FourCardsScreen(navController, Modifier.padding(paddingValues))
        }
    )
}

@Composable
fun TopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.primaryContainer
                    )
                )
            )
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Good morning,\nUser!",
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 20.sp,
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_user),
                contentDescription = "User Icon",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .size(48.dp)
                    .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f), CircleShape)
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun FourCardsScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CardItem(
                title = "Breed Selection",
                onClick = { navController.navigate("breedSelection") },
                modifier = Modifier.weight(1f),
                imageResId = R.drawable.breed
            )
            CardItem(
                title = "Calving",
                onClick = { navController.navigate("calving") },
                modifier = Modifier.weight(1f),
                imageResId = R.drawable.calving
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CardItem(
                title = "Gestation",
                onClick = { navController.navigate("gestation") },
                modifier = Modifier.weight(1f),
                imageResId = R.drawable.gestation
            )
            CardItem(
                title = "Health",
                onClick = { navController.navigate("health") },
                modifier = Modifier.weight(1f),
                imageResId = R.drawable.health
            )
        }
    }
}

@Composable
fun CardItem(title: String, onClick: (() -> Unit)? = null, modifier: Modifier = Modifier, imageResId: Int) {
    Card(
        modifier = modifier
            .fillMaxHeight()
            .padding(4.dp)
            .clickable { onClick?.invoke() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = "$title Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(8.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(onClick = { navController.navigate("home") }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_home),
                contentDescription = "Home",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
fun BreedSelectionScreen() {
    var breedName by remember { mutableStateOf("") }
    var healthCondition by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var suggestedBreed by remember { mutableStateOf<String?>(null) }

    val breeds = listOf("Angus", "Hereford", "Holstein", "Jersey", "Simmental", "Charolais", "Limousin", "Brahman")
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = breedName,
            onValueChange = { breedName = it },
            label = { Text("Breed Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = healthCondition,
            onValueChange = { healthCondition = it },
            label = { Text("Health Condition") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Age") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                suggestedBreed = breeds.random()
                coroutineScope.launch {
                    Toast.makeText(context, "Suggested breed: $suggestedBreed", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Suggest Breed")
        }

        suggestedBreed?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Suggested breed to inseminate: $it", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun CalvingScreen() {
    val feedsAndSupplements = listOf(
        "Month" to "Feed/Supplement",
        "0-1" to "Milk replacer",
        "1-2" to "Starter feed",
        "2-3" to "Calf grower",
        "4-6" to "Concentrates",
        "6-12" to "Forage and concentrates",
        "12-24" to "Transition feed and forages"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Feeds and Supplements for Cow Calves",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Table(
            columnHeaders = listOf("Month", "Feed/Supplement"),
            rows = feedsAndSupplements
        )
    }
}

@Composable
fun GestationScreen() {
    val feedsAndSupplements = listOf(
        "Month" to "Feed/Supplement",
        "1" to "High-energy concentrate",
        "2" to "Balanced forage and grain",
        "3" to "Quality hay and silage",
        "4-6" to "Forage with protein supplement",
        "7-9" to "Forage, grain mix, and minerals"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Feeds and Supplements for Gestation",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Table(
            columnHeaders = listOf("Month", "Feed/Supplement"),
            rows = feedsAndSupplements
        )
    }
}

@Composable
fun HealthScreen() {
    var healthRecord by remember { mutableStateOf("") }
    var tipsExpanded by remember { mutableStateOf(false) }
    var symptoms by remember { mutableStateOf("") }
    var suggestions by remember { mutableStateOf<String?>(null) }


    val symptomSuggestions = mapOf(
        "cough" to "Possible cold or respiratory infection. Monitor closely and consult a vet if symptoms persist.",
        "fever" to "Possible infection or disease. Ensure hydration and contact a vet.",
        "diarrhea" to "Possible digestive issue or dietary problem. Review diet and consult a vet.",
        "loss of appetite" to "Possible health issue. Check for other symptoms and consult a vet."
    )


    fun checkSymptoms() {
        suggestions = symptomSuggestions.filterKeys { it in symptoms.split(", ") }
            .values
            .takeIf { it.isNotEmpty() }
            ?.joinToString("\n") ?: "No suggestions available for the given symptoms."
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Cow Health Management",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Health Tips Section
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Health Tips",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                if (tipsExpanded) {
                    Text(
                        text = "1. Ensure regular vaccinations.\n2. Monitor weight and growth.\n3. Maintain proper hygiene and clean living areas.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    Text(
                        text = "Tap to expand tips",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { tipsExpanded = !tipsExpanded }) {
                    Text(text = if (tipsExpanded) "Collapse" else "Expand")
                }
            }
        }

        // Health Record Form
        Text(
            text = "Record Health Check",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        TextField(
            value = healthRecord,
            onValueChange = { healthRecord = it },
            label = { Text("Health Record Details") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* Save health record */ },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Save Record")
        }

        // Symptom Checker Section
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Symptom Checker",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        TextField(
            value = symptoms,
            onValueChange = { symptoms = it },
            label = { Text("Enter symptoms (comma separated)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { checkSymptoms() },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Check Symptoms")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = suggestions ?: "",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}


@Composable
fun Table(columnHeaders: List<String>, rows: List<Pair<String, String>>) {
    Column {
        Row(modifier = Modifier.padding(vertical = 4.dp)) {
            for (header in columnHeaders) {
                Text(
                    text = header,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        for (row in rows) {
            Row(modifier = Modifier.padding(vertical = 4.dp)) {
                Text(
                    text = row.first,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = row.second,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FarmzTheme {
        val navController = rememberNavController()
        HomeScreen(navController)
    }
}
