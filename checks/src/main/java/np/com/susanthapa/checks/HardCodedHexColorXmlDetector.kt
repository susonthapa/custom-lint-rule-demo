package np.com.susanthapa.checks

import com.android.resources.ResourceFolderType
import com.android.tools.lint.detector.api.*
import org.w3c.dom.Attr

/**
 * Created by suson on 11/5/20
 */
@Suppress("UnstableApiUsage")
class HardCodedHexColorXmlDetector : ResourceXmlDetector() {

    companion object {
        val ISSUE = Issue.create(
            id = "HardCodedHexColorXml",
            briefDescription = """
                Prohibits hardcoded hex colors in layout XML
            """.trimIndent(),
            explanation = """
                Hardcoded hex colors should be declared in a `color` resource or better
                defined in theme and referenced through `?attr/` indirection
            """.trimIndent(),
            category = Category.CORRECTNESS,
            severity = Severity.ERROR,
            implementation = Implementation(
                HardCodedHexColorXmlDetector::class.java,
                Scope.RESOURCE_FILE_SCOPE
            )
        )
    }

    override fun appliesTo(folderType: ResourceFolderType): Boolean {
        return folderType == ResourceFolderType.LAYOUT
    }

    override fun getApplicableAttributes(): Collection<String>? {
        return XmlScannerConstants.ALL
    }

    override fun visitAttribute(context: XmlContext, attribute: Attr) {
        // get the value of the xml attribute
        val attributeValue = attribute.value

        // check for the hex color
        if (!attributeValue.startsWith("#")) {
            return
        }

        context.report(
            issue = ISSUE,
            scope = attribute,
            location = context.getValueLocation(attribute),
            message = "Hardcoded hex colors should be declared in a `color` resource."
        )
    }

}