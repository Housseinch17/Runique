import androidx.room.gradle.RoomExtension
import com.example.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.exclude

class AndroidRoomConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("androidx.room")
                apply("com.google.devtools.ksp")
            }

            //Apply the exclusion to all configurations in this module
            //to avoid duplicate class com.intellij:annotations with org.jetbrains:annotations
            configurations.all {
                exclude(group = "com.intellij", module = "annotations")
            }

            extensions.configure<RoomExtension> {
                schemaDirectory("$projectDir/schemas")
            }

            dependencies{
                "implementation"(libs.findLibrary("room.runtime").get())
                "implementation"(libs.findLibrary("room.ktx").get())
                "implementation"(libs.findLibrary("room.compiler").get())
            }
        }
    }

}