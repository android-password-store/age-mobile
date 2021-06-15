plugins {
  `maven-publish`
  signing
}

group = "com.github.android-password-store"
version = "0.2.0-SNAPSHOT"

tasks.register<Exec>("buildGomobile") {
  commandLine("./build.sh")
}

publishing {
  publications {
    create<MavenPublication>("ageMobile") {
      artifactId = "age-mobile"
      artifact("dist/android/ageMobile.aar")
      pom {
        name.set("age-mobile")
        description.set("Gomobile-based bindings to the age encryption tool")
        url.set("https://github.com/Android-Password-Store/age-mobile")
        licenses {
          license {
            name.set("MIT")
            url.set("https://raw.githubusercontent.com/Android-Password-Store/age-mobile/develop/LICENSE")
          }
        }
        developers {
          developer {
            id.set("SphericalKat")
            name.set("Amogh Lele")
            email.set("amolele@gmail.com")
          }
          developer {
            id.set("android-password-store")
            name.set("The Android Password Store Authors")
            email.set("aps@msfjarvis.dev")
          }
        }
        scm {
          connection.set("scm:git:https://github.com/Android-Password-Store/age-mobile.git")
          developerConnection.set("scm:git:ssh://git@github.com:Android-Password-Store/age-mobile.git")
          url.set("https://github.com/Android-Password-Store/age-mobile")
        }
      }
    }
  }
  repositories {
    maven {
      val hostUrl = "https://oss.sonatype.org/"
      val releasesRepoUrl = uri("$hostUrl/service/local/staging/deploy/maven2/")
      val snapshotsRepoUrl = uri("$hostUrl/content/repositories/snapshots/")
      url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
      credentials {
        username = findProperty("mavenUsername") as String?
        password = findProperty("mavenPassword") as String?
      }
    }
  }
}

signing {
  val signingKey: String? by project
  val signingPassword: String? by project
  useInMemoryPgpKeys(signingKey, signingPassword)
  sign(publishing.publications["ageMobile"])
}
