package ru.kode.tools.opengate.story

import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.auto.service.AutoService
import com.google.devtools.ksp.processing.KSPLogger
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class Story()

@AutoService(SymbolProcessorProvider::class)
class StoryProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return StoryProcessor(environment)
    }
}

class StoryProcessor(private val environment: SymbolProcessorEnvironment) : SymbolProcessor {
    private val logger: KSPLogger = environment.logger
    private val storyComponents = mutableListOf<Pair<String, KSFunctionDeclaration>>()
    private var wasActivityCreated = false
    private var wasManifestCreated = false
    private var wasListOfStoriesCreated = false

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver.getSymbolsWithAnnotation(Story::class.qualifiedName!!)
        symbols.filterIsInstance<KSFunctionDeclaration>().forEach { function ->
            generateStoryFile(function)
        }

        generateActivity()

        generateManifest()

        generateStories()

        return emptyList()
    }

    private fun generateStoryFile(function: KSFunctionDeclaration) {
        val packageName = function.packageName.asString()
        val functionName = function.simpleName.asString()
        val fileName = "${function.simpleName.asString()}Story"

        storyComponents.add(packageName to function)

        val parameters = function.parameters.map { param ->
            val paramName = param.name?.asString() ?: "param"
            val paramType = param.type.resolve().declaration.qualifiedName?.asString()?.split(".")?.last() ?: "UnknownType"
            "$paramName = deeplink?.getQueryParameter(\"$paramName\").to$paramType()"
        }

        val parameterString = parameters.joinToString(", ")

        val content = """
            package $packageName

            import android.net.Uri
            import androidx.compose.runtime.Composable

            @Composable
            fun ${functionName}Story(deeplink: Uri?) {
               ${functionName}($parameterString)
            }
        """.trimIndent()
        val file = environment.codeGenerator.createNewFile(
            Dependencies(false, function.containingFile!!),
            packageName,
            fileName
        )
        file.write(content.toByteArray())
    }

    private fun generateActivity() {
        logger.info("GENERATE - $ACTIVITY_NAME")
        if (wasActivityCreated) {
            return
        }

        wasActivityCreated = true

        val file = environment.codeGenerator.createNewFile(
            dependencies = Dependencies(false),
            packageName = PACKAGE_NAME,
            fileName = ACTIVITY_NAME
        )

        val storyImports = storyComponents.joinToString("\n") { (pkg, function) ->
            val name = function.simpleName.asString()
            "import $pkg.${name}Story"
        }

        val storyList = storyComponents.joinToString("\n") { (_, function) ->
            val name = function.simpleName.asString()

            """
               "${name}Story" -> ${name}Story(deeplink)
            """.trimIndent()
        }

        val content = """
                package $PACKAGE_NAME

                import android.content.Intent
                import android.os.Bundle
                import android.net.Uri
                import androidx.activity.ComponentActivity
                import androidx.activity.compose.setContent
                import androidx.compose.foundation.clickable
                import androidx.compose.foundation.layout.Arrangement
                import androidx.compose.foundation.layout.Column
                import androidx.compose.foundation.layout.fillMaxSize
                import androidx.compose.foundation.layout.padding
                import androidx.compose.material3.Text
                import androidx.compose.runtime.Composable
                import androidx.compose.ui.Modifier
                import androidx.compose.ui.unit.dp
                import androidx.compose.ui.unit.sp
                import androidx.compose.ui.tooling.preview.Preview
                import androidx.compose.ui.text.font.FontWeight
                import androidx.compose.material3.MaterialTheme
                import androidx.compose.material3.Surface
                import androidx.compose.material3.Text
                import androidx.compose.ui.graphics.Color
                import androidx.compose.runtime.getValue
                import androidx.compose.runtime.livedata.observeAsState
                import androidx.compose.runtime.mutableStateOf
                import androidx.compose.runtime.remember
                import androidx.lifecycle.LiveData
                import androidx.lifecycle.MutableLiveData
                import androidx.lifecycle.ViewModel
                import androidx.lifecycle.ViewModelProvider

                $storyImports

                class $ACTIVITY_NAME : ComponentActivity() {
                    private lateinit var viewModel: StorybookViewModel
                     
                    override fun onCreate(savedInstanceState: Bundle?) {
                        super.onCreate(savedInstanceState)
                        
                        viewModel = ViewModelProvider(this).get(StorybookViewModel::class.java)
                        
                        val intent = getIntent();
                        handleIntent(intent)
                        
                        setContent {
                            val component by viewModel.componentName.observeAsState()
                            val deeplink by viewModel.deeplink.observeAsState()
                            
                            StoryListScreen(
                                component = component,
                                deeplink = deeplink
                            )
                        }
                    }
                    
                    override fun onNewIntent(intent: Intent?) {
                        super.onNewIntent(intent)
                        setIntent(intent)
                        intent?.let {
                            handleIntent(it)
                        }
                    }
                    
                    private fun handleIntent(intent: Intent) {
                        val uri = intent.getData();
                        println(uri?.toString() ?: "uri is null")
                        val component = uri?.getQueryParameter("component")
                        println("component - " + component)
                        if (uri != null) {
                            viewModel.onChangedDeeplink(uri)
                        }
                    }
                }

                @Composable
                fun StoryListScreen(component: String?, deeplink: Uri?) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Text(
                            text = component ?: "",
                            fontSize = 20.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Bold
                        )
                    
                         Column(
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                       
                            when(component) {
                                $storyList
                            }
                            
                        }
                        
                    }
                }

                class StorybookViewModel : ViewModel() {
                    private val _componentName = MutableLiveData("${storyComponents.first().second.simpleName.asString()}")
                    val componentName: LiveData<String> = _componentName
                    
                    private val _deeplink = MutableLiveData<Uri?>(null)
                    val deeplink: LiveData<Uri?> = _deeplink

                    fun onChangedDeeplink(uri: Uri) {
                        _componentName.value = uri.getQueryParameter("component")
                        _deeplink.value = uri
                    }
                }
                """.trimIndent()

        file.write(content.toByteArray())
    }

    private fun generateManifest() {
        logger.info("GENERATE - ${ACTIVITY_NAME}Manifest")
        if (wasManifestCreated) {
            return
        }

        wasManifestCreated = true

        val file = environment.codeGenerator.createNewFile(
            dependencies = Dependencies(false),
            packageName = "",
            fileName = "${ACTIVITY_NAME}Manifest",
            extensionName = "xml"
        )

        val content = """
                <manifest xmlns:android="http://schemas.android.com/apk/res/android"
                    package="$PACKAGE_NAME">
                    <application>
                        <activity 
                            android:name=".StorybookActivity" 
                            android:exported="true"
                            android:windowSoftInputMode="adjustResize"
                            android:launchMode="singleTask">
                            <intent-filter>
                                <action android:name="android.intent.action.VIEW" />
                                <category android:name="android.intent.category.DEFAULT" />
                                <category android:name="android.intent.category.BROWSABLE" />
                                
                                <data android:scheme="sb-native" android:host="deep.link" />
                            </intent-filter>
                        </activity>
                    </application>
                </manifest>
                """.trimIndent()

        file.write(content.toByteArray())
    }

    private fun generateStories() {
        logger.info("GENERATE - stories.json")
        if (wasListOfStoriesCreated) {
            return
        }

        wasListOfStoriesCreated = true

        val file = environment.codeGenerator.createNewFile(
            dependencies = Dependencies(false),
            packageName = "",
            fileName = "stories",
            extensionName = "json"
        )

        val storyList = storyComponents.map { (_, function) ->
            val name = function.simpleName.asString()



            val parameters = buildJsonObject {
                function.parameters.forEach { param ->
                    val paramName = param.name?.asString() ?: "param"

                    put(paramName, paramName)
                }
            }

            buildJsonObject {
                put("name",  "${name}Story")
                put("control",  parameters)
            }
        }

        val content = Json.encodeToString(storyList)

        file.write(content.toByteArray())
    }

    companion object {
        private const val PACKAGE_NAME = "ru.kode.tools.opengate.android"
        private const val ACTIVITY_NAME = "StorybookActivity"
    }
}
