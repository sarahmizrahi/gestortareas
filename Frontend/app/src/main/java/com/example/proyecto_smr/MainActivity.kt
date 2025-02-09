package com.example.proyecto_smr

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberDrawerState
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.proyecto_smr.ui.theme.Proyecto_SMRTheme
import com.example.proyecto_smr.ui.theme.fondoApp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//@AndroidEntryPoint  //para que Hilt pueda inyectar dependencias en cualquier Composable asociado
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Proyecto_SMRTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TareasApp(modifier = Modifier.padding(innerPadding))}
            }
        }
    }
}
@Composable
fun TareasApp(modifier: Modifier) {
    // para gestior la neavegacion entre pantallas
    val navController = rememberNavController()
    // Manejo de las rutas
    NavHost(navController, startDestination = Rutas.Progress.ruta) {
        composable(Rutas.Progress.ruta) { MiProgressBar(navController) }
        composable(Rutas.TareasLista.ruta) { TareasLista(navController) }
        composable(Rutas.CrearTareas.ruta) { CrearTareas(navController) }
        composable(Rutas.TareaDetalle.ruta) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("tareaId")?.toLong()
            EditarTarea(navController, taskId)
        }
    }
}

//pantalla carga
@Composable
fun MiProgressBar(navController: NavController){

    // para controlar el progreso de la barra de carga
    var currentProgress by remember { mutableStateOf(0f) }

    // indica si aún está en proceso de carga
    var loading by remember { mutableStateOf(true) }

    // para gestionar la visibilidad de la imagen de "check"
    var visible by remember { mutableStateOf(true) }

    val imageSlideOut = slideOutHorizontally(
        targetOffsetX = { fullWidth -> -fullWidth }, // Desplazamiento completo hacia la izquierda
        animationSpec = tween(durationMillis = 1500) // Duración más lenta
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(top = 100.dp)
    ) {
        // Animación de la imagen de "check"
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(1500)),
            exit = imageSlideOut
        ) {
            AsyncImage(
                model = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fb/Yes_check.svg/600px-Yes_check.svg.png",
                contentDescription = "check",
                modifier = Modifier.width(300.dp)
            )
        }

        // Efecto para iniciar la carga y navegar a la siguiente pantalla después de completar
        LaunchedEffect(Unit) {
            visible = false
            loadProgress { progress ->
                currentProgress = progress
            }
            loading = false // Terminar carga cuando termine
            navController.navigate("tareasLista") // Redirigir a la siguiente pantalla
        }
        Spacer(Modifier.size(50.dp))

        // Mostrar la barra de progreso mientras carga
        if (loading) {
            LinearProgressIndicator(
                progress = currentProgress,
                modifier = Modifier.fillMaxWidth(0.8f),
            )
        }
    }
}

// Función asíncrona para simular carga con un retraso
suspend fun loadProgress(updateProgress: (Float) -> Unit) {
    for (i in 1..10) {
        updateProgress(i.toFloat() / 10)// Actualiza el progreso
        delay(100) // Retraso por cada actualización
    }
}





// Lista de Tareas
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TareasLista(navController: NavController, viewModel: TareaViewModel = viewModel()) {

    // Recoge las tareas desde el ViewModel usando un flujo
    val tareas by viewModel.tareas.collectAsState()

    // para controlar el estado del menú lateral (drawer)
    val drawerEstado = rememberDrawerState(initialValue = DrawerValue.Closed)

    // para abrir/cerrar el drawer con animación
    val coroutineScope = rememberCoroutineScope()

    // Se recarga la lista de tareas cada vez que se accede a esta pantalla
    LaunchedEffect(Unit) {
        viewModel.cargarTareas()    // Cargar tareas desde viewModel
    }

    Menu_Lateral(drawerEstado) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Tareas", color = Color.White, fontSize = 30.sp) },
                    navigationIcon = {
                        IconButton(onClick = { coroutineScope.launch {
                            if (drawerEstado.isClosed) drawerEstado.open() else drawerEstado.close()
                        } }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = fondoApp)
                )
            },
            bottomBar = {
                BottomBarFabAdd(navController)
            }
        ) { innerPadding ->
            Column(modifier = Modifier.background(fondoApp).fillMaxSize()) {

                //Muestra la lista de tareas
                LazyColumn(contentPadding = innerPadding) {
                    items(tareas) { tarea ->
                        TareaItem(
                            tarea,
                            viewModel,
                            onClick = { navController.navigate("tareaDetalle/${tarea.id}") }
                        )
                    }
                }
            }
        }
    }
}

//Tareas
@Composable
fun TareaItem(tarea: Tarea, viewModel: TareaViewModel, onClick: () -> Unit) {

    // para controlar si la estrella esta seleccionada
    var estrella by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable(onClick = onClick),  // Al hacer click se navegue al detalle de la tarea
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // RadioButton para eliminar la tarea al seleccionalo
            RadioButton(
                selected = false,
                onClick = {viewModel.eliminarTarea(tarea)}
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = tarea.titulo,
                    //fontWeight = FontWeight.Bold,
                )
            }
            // Icono estrella que al hacer click cambia de color
            IconButton(onClick = { estrella = !estrella }) {
                Icon(
                    imageVector = if (estrella) Icons.Outlined.Star else Icons.Default.Star,
                    contentDescription = "importante",
                    tint = if (estrella) Color.Yellow else Color.Gray   // Cambia el color según la selección
                )
            }

        }
    }
}

//Crear tarea
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearTareas(navController: NavController, viewModel: TareaViewModel = viewModel()) {

    // para manejar el estado de cada una
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var nota by remember { mutableStateOf("") }
    var selectedUser by remember { mutableStateOf<User?>(null) }
    var expandedDesplegable by remember { mutableStateOf(false) }

    // Estados para manejar los errores de los campos
    var tituloError by remember { mutableStateOf(false) }
    var usuarioError by remember { mutableStateOf(false) }

    // Lista de usuarios
    val opcionesDesplegable = listOf(
        User("Sarah"),
        User("Hector")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crear Tarea", color = Color.White, fontSize = 30.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = fondoApp)
            )
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.White)
                    }
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Info, contentDescription = "Info", tint = Color.White)
                    }
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.LocationOn, contentDescription = "LocationOn", tint = Color.White)
                    }
                },containerColor = fondoApp,
                floatingActionButton = {
                    FloatingActionButton(onClick = {
                        // Validaciones para los campos
                        tituloError = titulo.isEmpty()
                        usuarioError = selectedUser == null

                        // Si no hay errores, crear la tarea y navegar hacia atrás
                        if (!tituloError && !usuarioError) {
                            viewModel.crearTarea(Tarea(0, titulo, descripcion, false))
                            navController.popBackStack()
                        }
                    }) {
                        Icon(Icons.Default.Check, contentDescription = "Guardar tarea")
                    }
                },
            )
        },
        containerColor = fondoApp
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(innerPadding)
                .background(fondoApp)
        ) {
            // Campo de texto para el título con validación de error
            TextField(
                value = titulo,
                onValueChange = {
                    titulo = it
                    if (it.isNotEmpty()) tituloError = false
                },
                label = { Text("Título*") },
                modifier = Modifier
                    .fillMaxWidth(),
                isError = tituloError, // Muestra borde rojo si hay error
                colors = TextFieldDefaults.colors()
            )
            if (tituloError) {
                Text(
                    text = "El título no puede estar vacío",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.background(Color.White)
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            // Campo de texto para la descripción
            TextField(
                value = descripcion,
                onValueChange = {
                    descripcion = it
                },
                label = { Text("Descripción") },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = TextFieldDefaults.colors()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // ExposedDropdownMenuBox para el menú desplegable que permite seleccionar usuario
            ExposedDropdownMenuBox(
                expanded = expandedDesplegable,
                onExpandedChange = { expandedDesplegable = !expandedDesplegable }
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    TextField(
                        value = selectedUser?.nombre ?: "",
                        onValueChange = {},
                        label = { Text("Asignar a*") },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Desplegar menú"
                            )
                        },
                        readOnly = true,
                        isError = usuarioError,
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                }

                ExposedDropdownMenu(
                    expanded = expandedDesplegable,
                    onDismissRequest = { expandedDesplegable = false }
                ) {
                    opcionesDesplegable.forEach { user ->
                        DropdownMenuItem(
                            text = { Text(text = user.nombre) },
                            onClick = {
                                selectedUser = user
                                usuarioError = false
                                expandedDesplegable = false
                            }
                        )
                    }
                }
            }
            if (usuarioError) {
                Text(
                    text = "Debe seleccionar un usuario",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.background(Color.White)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Campo de texto para las notas
            TextField(
                value = nota,
                onValueChange = {
                    nota = it
                },
                label = { Text("Agregar nota") },
                modifier = Modifier
                    .fillMaxWidth().height(250.dp),
                colors = TextFieldDefaults.colors(),
                maxLines = 10
            )
            Spacer(modifier = Modifier.height(8.dp))

            Image(
                painter = painterResource(id = R.drawable.tareas),
                contentDescription = "Imagen tareas",
                modifier = Modifier
                    .size(180.dp)
                    .clip(RoundedCornerShape(90.dp))
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}



//Editar tareas
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarTarea(navController: NavController, taskId: Long?, viewModel: TareaViewModel = viewModel()) {

    val tarea by viewModel.listarTareaId(taskId).collectAsState(initial = null)

    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var selectedUser by remember { mutableStateOf<User?>(null) }
    var expandedDesplegable by remember { mutableStateOf(false) }

    // Estados para manejar los errores de los campos
    var tituloError by remember { mutableStateOf(false) }
    var usuarioError by remember { mutableStateOf(false) }


    LaunchedEffect(tarea) {
        tarea?.let {
            titulo = it.titulo
            descripcion = it.descripcion
        }
    }

    val opcionesDesplegable = listOf(
        User("Sarah"),
        User("Hector")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Tarea", color = Color.White, fontSize = 25.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = fondoApp)
            )
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.White)
                    }
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Info, contentDescription = "Info", tint = Color.White)
                    }
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.LocationOn, contentDescription = "LocationOn", tint = Color.White)
                    }
                },containerColor = fondoApp,
                        floatingActionButton = {
                            FloatingActionButton(onClick = {
                                // Validaciones
                                tituloError = titulo.isEmpty()
                                usuarioError = selectedUser == null

                                if (!tituloError && !usuarioError) {
                                    tarea?.let {
                                        val tareaEditada = it.copy(
                                            titulo = titulo,
                                            descripcion = descripcion
                                        )
                                        viewModel.actualizarTarea(tareaEditada)
                                        navController.popBackStack()
                                    }
                                }
                    }) {
                        Icon(Icons.Default.Check, contentDescription = "Guardar tarea")
                    }
                },
            )
        },
        containerColor = fondoApp
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .background(fondoApp)
        ) {
            // Campo de texto para el título
                TextField(
                    value = titulo,
                    onValueChange = {
                        titulo = it
                        if (it.isNotEmpty()) tituloError = false
                    },
                    label = { Text("Título*") },
                    modifier = Modifier
                        .fillMaxWidth(),
                    isError = tituloError, // Muestra borde rojo si hay error
                    colors = TextFieldDefaults.colors()
                )
            if (tituloError) {
                Text(
                    text = "El título no puede estar vacío",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.background(Color.White)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 5,
                colors = TextFieldDefaults.colors()
            )
            Spacer(modifier = Modifier.height(8.dp))

            ExposedDropdownMenuBox(
                expanded = expandedDesplegable,
                onExpandedChange = { expandedDesplegable = !expandedDesplegable }
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    TextField(
                        value = selectedUser?.nombre ?: "",
                        onValueChange = {},
                        label = { Text("Asignar a*") },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Desplegar menú"
                            )
                        },
                        readOnly = true,
                        isError = usuarioError,
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                }

                ExposedDropdownMenu(
                    expanded = expandedDesplegable,
                    onDismissRequest = { expandedDesplegable = false }
                ) {
                    opcionesDesplegable.forEach { user ->
                        DropdownMenuItem(
                            text = { Text(text = user.nombre) },
                            onClick = {
                                selectedUser = user
                                usuarioError = false
                                expandedDesplegable = false
                            }
                        )
                    }
                }
            }
            if (usuarioError) {
                Text(
                    text = "Debe seleccionar un usuario",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.background(Color.White)
                )
            }
        }
    }
}


@Composable
fun Menu_Lateral(
    drawerState: DrawerState,
    contenido:  @Composable () -> Unit   //Parametro que acepta una funcion como contenido
){
    //Elemetos menu con iconos
    val secciones = listOf(
        "Settings" to Icons.Filled.Settings,
        "Back" to Icons.Default.ArrowBack)

    val corutina = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = false,
        drawerContent = {
            ModalDrawerSheet(drawerTonalElevation = 100.dp) {
                secciones.forEach { (label, icon) ->
                    NavigationDrawerItem(
                        icon = { Icon(imageVector = icon, contentDescription = label) },
                        label = { Text(label) },
                        selected = false,
                        //Se cerrerá el menu al seleccionar "back"
                        onClick = {
                            if (label == "Back") corutina.launch {
                                drawerState.close() //Solo cierra si el label es "Back"
                            }
                        }
                    )
                }
            }
        }
    ) {
        contenido()
    }
}

@Composable
fun BottomBarFabAdd(navController: NavController){
    val isExpandedSearch = remember { mutableStateOf(false) }
    val isExpandedInfo = remember { mutableStateOf(false) }
    val isExpandedLocation = remember { mutableStateOf(false) }

    val iconSizeSearch by animateDpAsState(targetValue = if (isExpandedSearch.value) 36.dp else 24.dp)
    val iconSizeInfo by animateDpAsState(targetValue = if (isExpandedInfo.value) 36.dp else 24.dp)
    val iconSizeLocation by animateDpAsState(targetValue = if (isExpandedLocation.value) 36.dp else 24.dp)
    BottomAppBar(
        actions = {
            IconButton(onClick = { isExpandedSearch.value = !isExpandedSearch.value }) {
                Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.White, modifier = Modifier.size(iconSizeSearch))
            }
            IconButton(onClick = { isExpandedInfo.value = !isExpandedInfo.value }) {
                Icon(Icons.Default.Info, contentDescription = "Info", tint = Color.White, modifier = Modifier.size(iconSizeInfo))
            }
            IconButton(onClick = { isExpandedLocation.value = !isExpandedLocation.value }) {
                Icon(Icons.Default.LocationOn, contentDescription = "LocationOn", tint = Color.White, modifier = Modifier.size(iconSizeLocation))
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("crearTareas")
                },
                containerColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Añadir tarea")
            }
        },
        containerColor = fondoApp
    )
}
