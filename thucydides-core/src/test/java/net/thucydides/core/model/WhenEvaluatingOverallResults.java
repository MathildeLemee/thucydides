package net.thucydides.core.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static net.thucydides.core.model.TestResult.FAILURE;
import static net.thucydides.core.model.TestResult.IGNORED;
import static net.thucydides.core.model.TestResult.PENDING;
import static net.thucydides.core.model.TestResult.SKIPPED;
import static net.thucydides.core.model.TestResult.SUCCESS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(Parameterized.class)
public class WhenEvaluatingOverallResults {

    private List<TestResult> results;
    private TestResult expectedOverallResult;

    public WhenEvaluatingOverallResults(List<TestResult> results, TestResult expectedOverallResult) {
        this.results = results;
        this.expectedOverallResult = expectedOverallResult;
    }

    @Parameters
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][] {
                { Collections.emptyList(),                  PENDING },
                { Arrays.asList(SUCCESS),                   SUCCESS },
                { Arrays.asList(SUCCESS, SUCCESS),          SUCCESS },
                { Arrays.asList(SUCCESS, SUCCESS, SUCCESS), SUCCESS },
                { Arrays.asList(SUCCESS, PENDING),          PENDING },
                { Arrays.asList(SUCCESS, IGNORED),          SUCCESS },
                { Arrays.asList(SUCCESS, SKIPPED),          SUCCESS },
                { Arrays.asList(FAILURE),                   FAILURE },
                { Arrays.asList(FAILURE, FAILURE),          FAILURE },
                { Arrays.asList(FAILURE, SUCCESS),          FAILURE },
                { Arrays.asList(FAILURE, IGNORED),          FAILURE },
                { Arrays.asList(FAILURE, PENDING),          FAILURE },
                { Arrays.asList(IGNORED),                   IGNORED },
                { Arrays.asList(SKIPPED),                   SKIPPED },
                { Arrays.asList(IGNORED, FAILURE,SKIPPED),  FAILURE },
                { Arrays.asList(IGNORED, IGNORED),          IGNORED },
                { Arrays.asList(IGNORED, PENDING),          PENDING },
                { Arrays.asList(PENDING),                   PENDING },
                { Arrays.asList(PENDING, PENDING),          PENDING },
        });
    }

    @Test
    public void should_produce_correct_overall_result_from_a_list_of_step_results() {

        TestResultList overallResult = TestResultList.of(results);

        assertThat(overallResult.getOverallResult(), is(expectedOverallResult));
    }

}
