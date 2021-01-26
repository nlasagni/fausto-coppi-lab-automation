package faustoCoppiLabAutomation

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.gradle.internal.impldep.org.junit.rules.TemporaryFolder
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import java.io.File
import java.io.InputStream

class MainTest : FreeSpec({
    fun projectSetup(content: String) = TemporaryFolder().apply {
        create()
        newFile("build.gradle.kts").writeText(content)
    }

    fun projectSetup(content: () -> String) = projectSetup(content())

    fun InputStream.toFile(file: File) {
        use { input ->
            file.outputStream().use { input.copyTo(it) }
        }
    }

    "Nothing to print" - {
        assert(true)
    }
})