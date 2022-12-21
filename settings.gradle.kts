rootProject.name = "bouncer"

include("core")
include("playground")

// Feature Previews
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

run {
  rootProject.children.forEach { it.name = "${rootProject.name}-${it.name}" }
}

// Plugin Repositories
pluginManagement {
  repositories {
    gradlePluginPortal()
    mavenLocal()
  }
}
