import com.android.build.api.dsl.ApplicationExtension
import com.example.convention.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationComposeConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            //this means this convention plugin will contain the application plugin
            //and it's configurations
            pluginManager.run{
                apply("runique.android.application")
                apply("org.jetbrains.kotlin.plugin.compose")
            }
            val extension = extensions.getByType<ApplicationExtension>()
            configureAndroidCompose(
                commonExtension = extension
            )
        }
    }
}