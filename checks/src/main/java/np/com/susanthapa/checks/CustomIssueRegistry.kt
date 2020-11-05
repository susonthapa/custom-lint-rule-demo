package np.com.susanthapa.checks

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue

/**
 * Created by suson on 11/5/20
 */

@Suppress("UnstableApiUsage")
class CustomIssueRegistry : IssueRegistry() {

    override val api: Int
        get() = CURRENT_API

    /**
     * The list of issues that can be found by all known detectors (including those that may be
     * disabled!)
     */
    override val issues: List<Issue>
        get() = listOf(
            AndroidLogDetector.ISSUE,
            HardCodedHexColorXmlDetector.ISSUE,
            AndroidToastDetector.ISSUE
        )

}