import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.maven
import org.gradle.language.jvm.tasks.ProcessResources

/** Extra resource directories (under src/main/overlays) and the Minecraft versions they apply to. */
val RESOURCE_OVERLAYS: Map<String, String> = linkedMapOf(
    "1.20.1" to "<1.21",
    "1.21.1" to ">=1.21 <26.1",
    "1.21+" to ">=1.21",
    "pre-26.1" to "<26.1",
    "26.x" to ">=26.1",
)

/** Converts the 1.21+ data pack layout (singular folder names) to the 1.20.1 layout. */
fun ProcessResources.legacyDataLayout() {
    exclude("data/*/recipe/**")
    for ((from, to) in listOf("block" to "blocks", "item" to "items", "entity_type" to "entity_types")) {
        filesMatching("data/*/tags/$from/**") {
            path = path.replace("/tags/$from/", "/tags/$to/")
        }
    }
}

fun RepositoryHandler.reliableGlidersRepositories() {
    mavenLocal()
    mavenCentral()
    exclusiveContent {
        forRepository { maven("https://api.modrinth.com/maven") { name = "Modrinth" } }
        filter { includeGroup("maven.modrinth") }
    }
    maven("https://maven.terraformersmc.com/releases/") {
        name = "Terraformers (Mod Menu, EMI)"
        content {
            includeGroupAndSubgroups("com.terraformersmc")
            includeGroupAndSubgroups("dev.emi")
        }
    }
    maven("https://maven.isxander.dev/releases") {
        name = "Xander Maven (YACL)"
        content {
            includeGroupAndSubgroups("dev.isxander")
            includeGroupAndSubgroups("org.quiltmc.parsers")
        }
    }
    maven("https://maven.quiltmc.org/repository/release/") {
        name = "Quilt Maven"
        content { includeGroupAndSubgroups("org.quiltmc.parsers") }
    }
    maven("https://maven.ladysnake.org/releases") {
        name = "Ladysnake (Cardinal Components)"
        content {
            includeGroupAndSubgroups("org.ladysnake")
            includeGroupAndSubgroups("dev.onyxstudios")
        }
    }
    maven("https://maven.parchmentmc.org") {
        name = "ParchmentMC"
        content { includeGroupAndSubgroups("org.parchmentmc") }
    }
}
