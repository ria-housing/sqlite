package com.example.assignment5

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.assignment5.ui.theme.Assignment5Theme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Assignment5Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    Greeting("Android")
                    MainScreen(applicationContext)
                }
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(applicationContext: Context) {
    val db = UsersDatabase.getInstance(applicationContext).UsersDao()
    var mobile by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var qmob by remember {
        mutableStateOf("")
    }
    var updmob by remember {
        mutableStateOf("")
    }
    var updpass by remember {
        mutableStateOf("")
    }
    var delmob by remember {
        mutableStateOf("")
    }
    val mContext = LocalContext.current
    val scope = rememberCoroutineScope()
    var savebool by remember { mutableStateOf(false) }
    var getpassbool by remember {
        mutableStateOf(false)
    }
    var updpassbool by remember {
        mutableStateOf(false)
    }
    var delrowbool by remember {
        mutableStateOf(false)
    }
    var delbool by remember {
        mutableStateOf(false)
    }
    Column(
        Modifier.padding(all = 5.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = mobile,
            onValueChange = {
                mobile = it
            },
            modifier = Modifier.padding(vertical = 10.dp),
            placeholder = { Text(text = "Mobile No.") },
        )
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            modifier = Modifier.padding(vertical = 10.dp),
            placeholder = { Text(text = "Password") },
        )
        if (savebool) {
            scope.launch {
                try {
                    db.insert(Users(MobileNo = mobile, Password = password))

                } catch (ex: Exception) {
                    println("cancelled")
                }
                mobile = ""
                password = ""
                savebool = !savebool
            }

        }
        Button(onClick = {
            savebool = !savebool

        }) {
            Text(text = "SAVE DETAILS")
        }
        OutlinedTextField(
            value = qmob,
            onValueChange = {
                qmob = it
            },
            modifier = Modifier.padding(vertical = 10.dp),
            placeholder = { Text(text = "Query mobile") },
        )
        if (getpassbool) {
            val showcustomrows by remember {
                derivedStateOf {
                    runBlocking {
                        withContext(Dispatchers.IO) {
                            db.getCustomUser(qmob)
                        }
                    }
                }
            }
            showcustomrows.forEach {
                Text("${it.MobileNo} - ${it.Password}")
            }
        }
        Button(onClick = {
            getpassbool = !getpassbool
        }) {
            Text(text = "GET PASSWORD")
        }
        Row() {
            OutlinedTextField(
                value = updmob,
                onValueChange = {
                    updmob = it
                },
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .weight(1f),
                placeholder = { Text(text = "Query mobile") },
            )
            OutlinedTextField(
                value = updpass,
                onValueChange = {
                    updpass = it
                },
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 10.dp)
                    .weight(1f),
                placeholder = { Text(text = "Query mobile") },
            )
        }
        if (updpassbool) {
            scope.launch {
                try {
                    val id = db.getId(updmob)
                    db.update(Users(id, MobileNo = updmob, Password = updpass))
                } catch (ex: Exception) {
                    println("cancelled")
                }
            }
        }
        Button(onClick = {
            updpassbool = !updpassbool
        }) {
            Text(text = "UPDATE PASS")
        }
        Row() {
            OutlinedTextField(
                value = delmob,
                onValueChange = {
                    delmob = it
                },
                modifier = Modifier.padding(vertical = 10.dp),
                placeholder = { Text(text = "Query mobile") },
            )
            if (delrowbool) {
                scope.launch {
                    try {
                        val id = db.getId(delmob)
                        db.delete(Users(id, MobileNo = delmob, Password = ""))
                        Toast.makeText(mContext, "deleted", Toast.LENGTH_SHORT).show()
                    } catch (ex: Exception) {
                        println("cancelled")
                    }
                }
            }
            Button(
                onClick = {
                    delrowbool = !delrowbool
                }, Modifier.padding(all = 10.dp)
            ) {
                Text(text = "DROP")
            }


        }
        if (delbool) {
            scope.launch {
                try {
                    db.deleteAll()
                    Toast.makeText(mContext, "deleted", Toast.LENGTH_SHORT).show()
                } catch (ex: Exception) {
                    println("cancelled")
                }
            }
        }
        Button(onClick = {
            delbool = !delbool
        }) {
            Text(text = "DELETE ALL")
        }
    }
}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Assignment5Theme {
        Greeting("Android")
    }
}