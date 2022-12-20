rootProject.name = "bouncer"

include("core")

// Feature Previews
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

// Plugin Repositories
pluginManagement {
  repositories {
    gradlePluginPortal()
    mavenLocal()
  }
}
