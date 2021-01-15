/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */

package org.elasticsearch.gradle.test;

import org.elasticsearch.gradle.ElasticsearchJavaPlugin;
import org.elasticsearch.gradle.ExportElasticsearchBuildResourcesTask;
import org.elasticsearch.gradle.RepositoriesSetupPlugin;
import org.elasticsearch.gradle.info.BuildParams;
import org.elasticsearch.gradle.info.GlobalBuildInfoPlugin;
import org.elasticsearch.gradle.internal.precommit.InternalPrecommitTasks;
import org.elasticsearch.gradle.precommit.PrecommitTasks;
import org.elasticsearch.gradle.testclusters.TestClustersPlugin;
import org.gradle.api.InvalidUserDataException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaBasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.testing.Test;
import org.gradle.plugins.ide.eclipse.model.EclipseModel;

import org.gradle.plugins.ide.idea.model.IdeaModel;

import java.util.Arrays;
import java.util.Map;

/**
 * Configures the build to compile tests against Elasticsearch's test framework
 * and run REST tests. Use BuildPlugin if you want to build main code as well
 * as tests.
 */
public class StandaloneRestTestPlugin implements Plugin<Project> {
    @Override
    public void apply(final Project project) {
        if (project.getPluginManager().hasPlugin("elasticsearch.build")) {
            throw new InvalidUserDataException(
                "elasticsearch.standalone-test, elasticsearch.standalone-rest-test, " + "and elasticsearch.build are mutually exclusive"
            );
        }

        project.getRootProject().getPluginManager().apply(GlobalBuildInfoPlugin.class);
        project.getPluginManager().apply(JavaBasePlugin.class);
        project.getPluginManager().apply(TestClustersPlugin.class);
        project.getPluginManager().apply(RepositoriesSetupPlugin.class);
        project.getPluginManager().apply(RestTestBasePlugin.class);

        project.getTasks().register("buildResources", ExportElasticsearchBuildResourcesTask.class);
        ElasticsearchJavaPlugin.configureInputNormalization(project);
        ElasticsearchJavaPlugin.configureCompile(project);

        project.getExtensions().getByType(JavaPluginExtension.class).setSourceCompatibility(BuildParams.getMinimumRuntimeVersion());
        project.getExtensions().getByType(JavaPluginExtension.class).setTargetCompatibility(BuildParams.getMinimumRuntimeVersion());

        // only setup tests to build
        SourceSetContainer sourceSets = project.getExtensions().getByType(SourceSetContainer.class);
        final SourceSet testSourceSet = sourceSets.create("test");

        project.getTasks().withType(Test.class).configureEach(test -> {
            test.setTestClassesDirs(testSourceSet.getOutput().getClassesDirs());
            test.setClasspath(testSourceSet.getRuntimeClasspath());
        });

        // create a compileOnly configuration as others might expect it
        project.getConfigurations().create("compileOnly");
        project.getDependencies().add("testImplementation", project.project(":test:framework"));

        EclipseModel eclipse = project.getExtensions().getByType(EclipseModel.class);
        eclipse.getClasspath().setSourceSets(Arrays.asList(testSourceSet));
        eclipse.getClasspath()
            .setPlusConfigurations(
                Arrays.asList(project.getConfigurations().getByName(JavaPlugin.TEST_RUNTIME_CLASSPATH_CONFIGURATION_NAME))
            );

        IdeaModel idea = project.getExtensions().getByType(IdeaModel.class);
        idea.getModule().getTestSourceDirs().addAll(testSourceSet.getJava().getSrcDirs());
        idea.getModule()
            .getScopes()
            .put(
                "TEST",
                Map.of("plus", Arrays.asList(project.getConfigurations().getByName(JavaPlugin.TEST_RUNTIME_CLASSPATH_CONFIGURATION_NAME)))
            );
        BuildParams.withInternalBuild(() -> InternalPrecommitTasks.create(project, false)).orElse(() -> PrecommitTasks.create(project));
    }
}
