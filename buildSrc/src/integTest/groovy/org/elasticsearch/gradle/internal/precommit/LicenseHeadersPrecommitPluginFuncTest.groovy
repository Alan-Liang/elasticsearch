/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */

package org.elasticsearch.gradle.internal.precommit

import org.elasticsearch.gradle.fixtures.AbstractGradleFuncTest
import org.gradle.testkit.runner.TaskOutcome

class LicenseHeadersPrecommitPluginFuncTest extends AbstractGradleFuncTest {

    def "detects invalid files with invalid license header"() {
        given:
        buildFile << """
        plugins {
            id 'java'
            id 'elasticsearch.internal-licenseheaders'
        }
        """
        apacheSourceFile()
        unknownSourceFile()
        unapprovedSourceFile()

        when:
        def result = gradleRunner("licenseHeaders").buildAndFail()

        then:
        result.task(":licenseHeaders").outcome == TaskOutcome.FAILED
        assertOutputContains(result.output, "> Check failed. License header problems were found. Full details: ./build/reports/licenseHeaders/rat.xml")
        assertOutputContains(result.output, "./src/main/java/org/acme/UnknownLicensed.java")
        assertOutputContains(result.output, "./src/main/java/org/acme/UnapprovedLicensed.java")
        normalized(result.output).contains("./src/main/java/org/acme/ApacheLicensed.java") == false
    }

    def "can filter source files"() {
        given:
        buildFile << """
        plugins {
            id 'java'
            id 'elasticsearch.internal-licenseheaders'
        }

        tasks.named("licenseHeaders").configure {
            excludes << 'org/acme/filtered/**/*'
        }
        """
        apacheSourceFile()
        unknownSourceFile("src/main/java/org/acme/filtered/FilteredUnknownLicensed.java")
        unapprovedSourceFile("src/main/java/org/acme/filtered/FilteredUnapprovedLicensed.java")

        when:
        def result = gradleRunner("licenseHeaders").build()

        then:
        result.task(":licenseHeaders").outcome == TaskOutcome.SUCCESS
    }

    private File unapprovedSourceFile(String filePath = "src/main/java/org/acme/UnapprovedLicensed.java") {
        File sourceFile = file(filePath);
        sourceFile << """
/*
 * Copyright (C) 2007 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

 package ${packageString(sourceFile)};

 public class ${sourceFile.getName() - ".java"} {
 }
 """
    }

    private File unknownSourceFile(String filePath = "src/main/java/org/acme/UnknownLicensed.java") {
        File sourceFile = file(filePath);
        sourceFile << """
/*
 * Blubb my custom license shrug!
 */

 package ${packageString(sourceFile)};

 public class ${sourceFile.getName() - ".java"} {
 }
 """
    }

    private File apacheSourceFile() {
        file("src/main/java/org/acme/ApacheLicensed.java") << """
/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */

 package org.acme;
 public class ApacheLicensed {
 }
 """
    }

    private String packageString(File sourceFile) {
        String normalizedPath = normalized(sourceFile.getPath())
        (normalizedPath.substring(normalizedPath.indexOf("src/main/java")) - "src/main/java/" - ("/" + sourceFile.getName())).replaceAll("/", ".")
    }
}
