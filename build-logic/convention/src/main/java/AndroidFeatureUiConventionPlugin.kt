import com.example.convention.addUiLayerDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureUiConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            //this means this convention plugin will contain the library compose plugin
            //and it's configurations
            pluginManager.apply("runique.android.library.compose")

            dependencies{
                addUiLayerDependencies(target)
            }
        }
    }
}