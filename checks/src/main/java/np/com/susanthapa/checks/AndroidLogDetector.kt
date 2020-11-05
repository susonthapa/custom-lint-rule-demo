package np.com.susanthapa.checks

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.UCallExpression

/**
 * Created by suson on 11/5/20
 */
@Suppress("UnstableApiUsage")
class AndroidLogDetector : Detector(), SourceCodeScanner {

    override fun getApplicableMethodNames(): List<String>? {
        return listOf("tag", "format", "v", "d", "i", "w", "e", "wtf")
    }

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        super.visitMethodCall(context, node, method)
        val evaluator = context.evaluator
        if (evaluator.isMemberInClass(method, "android.util.Log")) {
            reportUsage(context, node)
        }
    }

    private fun reportUsage(context: JavaContext, node: UCallExpression) {
        context.report(
            issue = ISSUE,
            scope = node,
            location = context.getCallLocation(
                call = node,
                includeReceiver = true,
                includeArguments = true
            ),
            message = "android.Util.Log usage is forbidden"
        )
    }

    companion object {
        private val IMPLEMENTATION = Implementation(
            AndroidLogDetector::class.java,
            Scope.JAVA_FILE_SCOPE
        )

        val ISSUE = Issue
            .create(
                id = "AndroidLogDetector",
                briefDescription = "The Android Log should not be used",
                explanation = """
                    You should use some logging library like Timber instead of the default
                    Android Logging API
                """.trimIndent(),
                category = Category.CORRECTNESS,
                priority = 9,
                severity = Severity.ERROR,
                androidSpecific = true,
                implementation = IMPLEMENTATION
            )
    }

}