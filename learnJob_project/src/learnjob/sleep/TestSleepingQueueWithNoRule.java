package learnjob.sleep;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.internal.jobs.JobManager;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;

import job.LightOnJobWithNonRull;
import learnJob.DebugOptionImp;
import learnJob.JobManagerUtil;

public class TestSleepingQueueWithNoRule {
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		((JobManager)(Job.getJobManager())).optionsChanged(new DebugOptionImp());
		testDifferentInstance();
		testTheSameInstance();
	}

	private static void testDifferentInstance() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		System.out.println("Start ==> testDifferentInstance.");
		
		LightOnJobWithNonRull lightOn1 = new LightOnJobWithNonRull();
		LightOnJobWithNonRull lightOn2 = new LightOnJobWithNonRull();
		LightOnJobWithNonRull lightOn3 = new LightOnJobWithNonRull();
		
		lightOn1.schedule(1000000000);
		JobManagerUtil.printAllJobs();
		
		lightOn2.schedule(1000000000);
		JobManagerUtil.printAllJobs();

		lightOn3.schedule(1000000000);
		JobManagerUtil.printAllJobs();
		
		((JobManager)(Job.getJobManager())).printState(lightOn1);
		((JobManager)(Job.getJobManager())).printState(lightOn2);
		((JobManager)(Job.getJobManager())).printState(lightOn3);
		
		Job[] selectedJobs = Job.getJobManager().find(LightOnJobWithNonRull.JOB_FAMILY);
		for (Job job: selectedJobs) {
			((JobManager)(Job.getJobManager())).printState(job);
		}
		
		Job currentJob = Job.getJobManager().currentJob();

		ISchedulingRule currenctRule = Job.getJobManager().currentRule();
		System.out.println("Current rule is " + currenctRule);
		
		System.out.println("End ==> testDifferentInstance.");
	}
	
	private static void testTheSameInstance() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		System.out.println("Start ==> testTheSameInstance.");
		
		LightOnJobWithNonRull lightOn1 = new LightOnJobWithNonRull();
		
		lightOn1.schedule(1000000000);
		JobManagerUtil.printAllJobs();
		
		lightOn1.schedule(1000000000);
		JobManagerUtil.printAllJobs();
		
		lightOn1.schedule(1000000000);
		JobManagerUtil.printAllJobs();
		
		System.out.println("End ==> testTheSameInstance.");

	}
}
