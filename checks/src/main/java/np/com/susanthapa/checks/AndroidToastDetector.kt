package np.com.susanthapa.checks

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.UCallExpression

/**
 * Created by suson on 11/5/20
 */
@Suppress("UnstableApiUsage")
class AndroidToastDetector : Detector(), SourceCodeScanner {

    companion object {
        val ISSUE = Issue.create(
            id = "Toast",
            briefDescription = "Prohibits usage of `android.widget.Toast`",
            explanation = """
                Should use `Snackbar` instead of `android.widget.Toast`
            """.trimIndent(),
            category = Category.CORRECTNESS,
            severity = Severity.ERROR,
            implementation = Implementation(
                AndroidToastDetector::class.java,
                Scope.JAVA_FILE_SCOPE
            )
        )
    }


    override fun getApplicableMethodNames(): List<String>? {
        return listOf("makeText")
    }

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        val evaluator = context.evaluator
        if (evaluator.isMemberInClass(method, "android.widget.Toast")) {

            val quickFixData = LintFix.create()
                .name("Change to Snackbar")
                .replace()
                .shortenNames()
                .text("Toast")
                .with("com.google.android.material.snackbar.Snackbar")
                .build()



            context.report(
                issue = ISSUE,
                scope = node,
                location = context.getCallLocation(
                    call = node,
                    includeReceiver = true,
                    includeArguments = true
                ),
                message = "Should use `Snackbar` instead of `android.widget.Toast`",
                quickfixData = quickFixData
            )
        }
    }

}