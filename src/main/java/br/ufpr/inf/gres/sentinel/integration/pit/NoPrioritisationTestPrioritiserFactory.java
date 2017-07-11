/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpr.inf.gres.sentinel.integration.pit;

import java.util.Collection;
import java.util.List;
import java.util.Properties;
import org.pitest.classinfo.ClassName;
import org.pitest.classpath.CodeSource;
import org.pitest.coverage.CoverageDatabase;
import org.pitest.coverage.TestInfo;
import org.pitest.functional.FCollection;
import org.pitest.functional.prelude.Prelude;
import org.pitest.mutationtest.build.TestPrioritiser;
import org.pitest.mutationtest.build.TestPrioritiserFactory;
import org.pitest.mutationtest.engine.MutationDetails;

/**
 *
 * @author Giovani
 */
public class NoPrioritisationTestPrioritiserFactory implements TestPrioritiserFactory {

    @Override
    public TestPrioritiser makeTestPrioritiser(Properties props, CodeSource code, CoverageDatabase coverage) {
        return new NoPrioritisationTestPrioritiser(coverage);
    }

    @Override
    public String description() {
        return "No Test Prioritisation";
    }

    public class NoPrioritisationTestPrioritiser implements TestPrioritiser {

        private final CoverageDatabase coverage;

        public NoPrioritisationTestPrioritiser(CoverageDatabase coverage) {
            this.coverage = coverage;
        }

        @Override
        public List<TestInfo> assignTests(MutationDetails mutation) {
            return prioritizeTests(mutation.getClassName(), pickTests(mutation));
        }

        private Collection<TestInfo> pickTests(MutationDetails mutation) {
            if (!mutation.isInStaticInitializer()) {
                return this.coverage.getTestsForClassLine(mutation.getClassLine());
            } else {
                return this.coverage.getTestsForClass(mutation.getClassName());
            }
        }

        private List<TestInfo> prioritizeTests(final ClassName clazz,
                Collection<TestInfo> testsForMutant) {
            final List<TestInfo> unsortedTests = FCollection.map(testsForMutant,
                    Prelude.id(TestInfo.class));
            return unsortedTests;
        }
    }

}
