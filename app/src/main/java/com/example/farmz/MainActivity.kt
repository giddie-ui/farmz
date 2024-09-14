package com.example.farmz

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.farmz.R
import com.example.farmz.ui.theme.FarmzTheme
import kotlinx.coroutines.launch
import viewmodel.AuthViewModel


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
fun SignUpScreen(navController: NavHostController, authViewModel: AuthViewModel = viewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    // Observing the state from AuthViewModel
    val isLoading by authViewModel.isLoading.collectAsState()
    val authMessage by authViewModel.authMessage.collectAsState()

    // Showing a toast message when authMessage is updated
    LaunchedEffect(authMessage) {
        authMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            authViewModel.clearAuthMessage()
            if (it == "Account created successfully!") {
                navController.navigate("home")
            }
        }
    }

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
                    authViewModel.signUp(email, password)
                } else {
                    Toast.makeText(context, "Please enter both email and password", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
            } else {
                Text("Sign Up")
            }
        }
    }
}

@Composable
fun LoginScreen(navController: NavHostController, authViewModel: AuthViewModel = viewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    // Observing the state from AuthViewModel
    val isLoading by authViewModel.isLoading.collectAsState()
    val authMessage by authViewModel.authMessage.collectAsState()

    // Showing a toast message when authMessage is updated
    LaunchedEffect(authMessage) {
        authMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            authViewModel.clearAuthMessage()
            if (it == "Login successful!") {
                navController.navigate("home")
            }
        }
    }

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
                    authViewModel.login(email, password)
                } else {
                    Toast.makeText(context, "Please enter both email and password", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
            } else {
                Text("Login")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

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
                text = "Welcome",
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
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(), // Ensure cards fill maximum height
                imageResId = R.drawable.breed
            )
            CardItem(
                title = "Calving",
                onClick = { navController.navigate("calving") },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(), // Ensure cards fill maximum height
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
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(), // Ensure cards fill maximum height
                imageResId = R.drawable.gestation
            )
            CardItem(
                title = "Health",
                onClick = { navController.navigate("health") },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(), // Ensure cards fill maximum height
                imageResId = R.drawable.health
            )
        }
    }
}
@Composable
fun CardItem(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    imageResId: Int
) {
    Card(
        modifier = modifier
            .clickable(onClick = onClick)
            .fillMaxWidth()
            .height(150.dp), // Set a fixed height for the card to help with centering
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        // Use a vertical arrangement with Spacers
        Spacer(modifier = Modifier.weight(1f)) // Pushes content to the center

        Image(
            painter = painterResource(id = imageResId),
            contentDescription = title,
            modifier = Modifier
                .size(60.dp) // Set size for the image
                .align(Alignment.CenterHorizontally) // Centers the image horizontally
        )

        Spacer(modifier = Modifier.weight(1f)) // Pushes image upwards to center it

        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .align(Alignment.CenterHorizontally) // Centers the text horizontally
                .padding(bottom = 16.dp) // Adds padding to keep text at the bottom
        )
    }
}

@Composable
fun BreedSelectionScreen() {
    var breedName by remember { mutableStateOf("") }
    var healthCondition by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var suggestedBreed by remember { mutableStateOf<String?>(null) }

    val breeds = listOf(
        "Angus", "Hereford", "Holstein", "Jersey", "Simmental", "Charolais", "Limousin", "Brahman",
        "Gelbvieh", "Shorthorn", "Red Poll", "Beefmaster", "Brangus", "Santa Gertrudis", "Chianina", "Maine-Anjou",
        "Salers", "Blonde d'Aquitaine", "Bazadaise", "Devon", "Dutch Belted", "Dexter", "Murray Grey", "Piedmontese",
        "Romagnola", "Texas Longhorn", "Wagyu", "Welsh Black", "Guernsey", "Brown Swiss", "Ayrshire", "Norwegian Red",
        "Tarentaise", "South Devon", "Belgian Blue", "Galloway", "Highland", "Corriente", "Senepol", "Tuli",
        "Africander", "Ankole-Watusi", "Boran", "Nguni", "Mashona", "N'Dama", "Nelore", "Ongole", "Rathi", "Sahiwal",
        "Tharparkar", "Gyr", "Kankrej", "Khillari", "Deoni", "Hariana", "Vechur", "Kherigarh", "Malvi", "Gir", "Rathi"
    )

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Function to determine the best breed based on the provided inputs
    fun suggestBreed(breedName: String, healthCondition: String, age: String): String? {
        val suitableBreeds = mutableListOf<String>()

        // Apply some basic logic to filter or suggest breeds
        breeds.forEach { breed ->
            when {
                breedName.isNotEmpty() && breed.contains(breedName, ignoreCase = true) -> suitableBreeds.add(breed)
                healthCondition.equals("Good", ignoreCase = true) && (breed == "Angus" || breed == "Hereford") -> suitableBreeds.add(breed)
                healthCondition.equals("Poor", ignoreCase = true) && (breed == "Holstein" || breed == "Jersey") -> suitableBreeds.add(breed)
                age.toIntOrNull() != null && age.toInt() in 1..3 && (breed == "Simmental" || breed == "Charolais") -> suitableBreeds.add(breed)
                age.toIntOrNull() != null && age.toInt() > 3 && (breed == "Limousin" || breed == "Brahman") -> suitableBreeds.add(breed)
            }
        }

        // If there are suitable breeds based on the rules, return one randomly
        return if (suitableBreeds.isNotEmpty()) suitableBreeds.random() else null
    }

    // Check if all fields are filled
    fun areAllFieldsFilled(): Boolean {
        return breedName.isNotBlank() && healthCondition.isNotBlank() && age.isNotBlank()
    }

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 64.dp)  // Space at the top for the title
                    .padding(bottom = 64.dp), // Ensure there is space at the bottom
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // TextField styling without custom colors
                TextField(
                    value = breedName,
                    onValueChange = { breedName = it },
                    label = { Text("Breed Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = healthCondition,
                    onValueChange = { healthCondition = it },
                    label = { Text("Health Condition") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = age,
                    onValueChange = { age = it },
                    label = { Text("Age") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (areAllFieldsFilled()) {
                            suggestedBreed = suggestBreed(breedName, healthCondition, age)
                            coroutineScope.launch {
                                if (suggestedBreed != null) {
                                    Toast.makeText(context, "Suggested breed: $suggestedBreed", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context, "No suitable breed found", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else {
                            coroutineScope.launch {
                                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                            }
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
            Text(
                text = "Breed Selection",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 16.dp)
            )
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background) // Set background color
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize(),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()), // Handle vertical scrolling
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
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

    // Full-screen background
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top, // Align content to the top
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Feeds and Supplements for Gestation",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Table(
                    columnHeaders = listOf("Month", "Feed/Supplement"),
                    rows = feedsAndSupplements
                )
            }
        }
    }
}


@Composable
fun HealthScreen() {
    var tipsExpanded by remember { mutableStateOf(false) }
    var symptoms by remember { mutableStateOf("") }
    var suggestions by remember { mutableStateOf<String?>(null) }

    // Expanded list of symptoms and their suggestions
    val symptomSuggestions = mapOf(
        "cough" to "Possible cold or respiratory infection. Monitor closely and consult a vet if symptoms persist.",
        "fever" to "Possible infection or disease. Ensure hydration and contact a vet.",
        "diarrhea" to "Possible digestive issue or dietary problem. Review diet and consult a vet.",
        "loss of appetite" to "Possible health issue. Check for other symptoms and consult a vet.",
        "lumps" to "Possible tumors or abscesses. Consult a vet for further examination.",
        "swelling" to "Possible injury or infection. Monitor closely and consult a vet if it persists.",
        "sneezing" to "Possible respiratory issue. Check for other symptoms and consult a vet if necessary.",
        "lethargy" to "Possible illness or discomfort. Check for other symptoms and consult a vet.",
        "weight loss" to "Possible nutritional deficiency or health issue. Review diet and consult a vet.",
        "bad breath" to "Possible dental issues or digestive problems. Consult a vet for examination.",
        "runny nose" to "Possible respiratory infection. Ensure the environment is clean and consult a vet if needed.",
        "vomiting" to "Possible digestive issue or poisoning. Monitor closely and consult a vet.",
        "ear shaking" to "Possible ear infection or parasite. Check ears and consult a vet if needed.",
        "skin rash" to "Possible allergic reaction or skin infection. Review any recent changes and consult a vet."
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.health),
                    contentDescription = "Health Image",
                    modifier = Modifier
                        .size(80.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(8.dp))
                if (tipsExpanded) {
                    Text(
                        text = """
                            1. Ensure regular vaccinations.
                            2. Monitor weight and growth.
                            3. Maintain proper hygiene and clean living areas.
                            4. Provide balanced nutrition and clean water.
                            5. Regularly check for signs of illness or injury.
                            6. Ensure adequate shelter and comfort.
                            7. Practice good parasite control and deworming.
                            8. Keep detailed health records for each cow.
                            9. Regularly inspect hooves and manage foot health.
                            10. Ensure proper breeding practices and check for reproductive health.
                            11. Monitor for any unusual behavior or symptoms.
                            12. Ensure regular dental check-ups and hoof trimming.
                            13. Provide adequate space and clean bedding.
                            14. Regularly review and update health management practices.
                        """.trimIndent(),
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
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Health Tips",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
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
