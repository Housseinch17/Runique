import com.android.build.api.dsl.LibraryExtension
import com.example.convention.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            //this means this convention plugin will contain the library plugin
            //and it's configurations
            pluginManager.run {
                apply("runique.android.library")
                apply("org.jetbrains.kotlin.plugin.compose")
            }
            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidCompose(
                commonExtension = extension
            )
        }
    }
}